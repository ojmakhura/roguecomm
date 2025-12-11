package bw.co.roguesystems.comm.config;

// import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import bw.co.roguesystems.comm.properties.RabbitProperties;

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitMQConfig {
    private final CachingConnectionFactory cachingConnectionFactory;
    private final RabbitProperties rabbitProperties;

    public RabbitMQConfig(CachingConnectionFactory cachingConnectionFactory, RabbitProperties rabbitProperties) {
        this.cachingConnectionFactory = cachingConnectionFactory;
        this.rabbitProperties = rabbitProperties;
    }

    @Bean
    public Queue createEmailExchangeQueue() {

        return QueueBuilder.durable(rabbitProperties.getEmailHandler())
                .withArgument("x-dead-letter-exchange","x.email-dispatch-failure")
                .withArgument("x-dead-letter-routing-key","fall-back")
                .build();
    }

    @Bean
    public Declarables createPostDispatchSchema(){
        return new Declarables(
                new FanoutExchange("x.post-email-dispatch"),
                new Queue(rabbitProperties.getEmailDispatchQueue(), true),
                new Binding(rabbitProperties.getEmailDispatchQueue(), Binding.DestinationType.QUEUE, "x.post-email-dispatch", rabbitProperties.getEmailDispatchRoutingKey(), null)
        );
    }

    @Bean
    public Declarables createDeadLetterSchema(){
        return new Declarables(
            new DirectExchange("x.email-dispatch-failure"),
            new Queue("q.fall-back-email-dispatch"),
            new Binding("q.fall-back-email-dispatch", Binding.DestinationType.QUEUE,"x.email-dispatch-failure", "fall-back", null)
        );
    }

    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    @Bean
    public Queue createEmailQueue() {
        
        return QueueBuilder.durable(rabbitProperties.getEmailQueue())
                .build();
    }

    @Bean
    public Declarables createEmailQueueSchema() {
        
        return new Declarables(
            new DirectExchange(rabbitProperties.getEmailQueueExchange()),
            emailQueue(),
            emailQueueBinding()
        );
    }

    @Bean
	Queue emailQueue() {
		return new Queue(rabbitProperties.getEmailQueue(), true);
	}

	@Bean
	DirectExchange emailQueueExchange() {
		return new DirectExchange(rabbitProperties.getEmailQueueExchange());
	}

	@Bean
	Binding emailQueueBinding() {
		return BindingBuilder.bind(emailQueue()).to(emailQueueExchange()).with(rabbitProperties.getEmailQueueRoutingKey());
	}

    @Bean
    public Jackson2JsonMessageConverter converter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        
        // mapper.setDate;
        template.setMessageConverter(converter(objectMapper()));
        return template;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper =
                new ObjectMapper();
                        // .registerModule(new ParameterNamesModule())
                        // .registerModule(new Jdk8Module())
                        // .registerModule(new JavaTimeModule());
        
        // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
