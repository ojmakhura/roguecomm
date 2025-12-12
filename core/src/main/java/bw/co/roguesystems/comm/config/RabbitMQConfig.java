package bw.co.roguesystems.comm.config;

// import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import bw.co.roguesystems.comm.properties.RabbitProperties;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitMQConfig {
    private final CachingConnectionFactory cachingConnectionFactory;
    private final RabbitProperties rabbitProperties;
    private final ObjectMapper objectMapper;

    public RabbitMQConfig(CachingConnectionFactory cachingConnectionFactory, RabbitProperties rabbitProperties,
            ObjectMapper objectMapper) {
        this.cachingConnectionFactory = cachingConnectionFactory;
        this.rabbitProperties = rabbitProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Queue createEmailExchangeQueue() {

        return QueueBuilder.durable(rabbitProperties.getEmailHandler())
                .withArgument("x-dead-letter-exchange", "x.email-dispatch-failure")
                .withArgument("x-dead-letter-routing-key", "fall-back")
                .build();
    }

    @Bean
    public Queue createWhatsappExchangeQueue() {

        return QueueBuilder.durable(rabbitProperties.getWhatsappHandler())
                .withArgument("x-dead-letter-exchange", "x.whatsapp-dispatch-failure")
                .withArgument("x-dead-letter-routing-key", "fall-back")
                .build();
    }

    @Bean
    public Queue createSmsExchangeQueue() {

        return QueueBuilder.durable(rabbitProperties.getSmsHandler())
                .withArgument("x-dead-letter-exchange", "x.sms-dispatch-failure")
                .withArgument("x-dead-letter-routing-key", "fall-back")
                .build();
    }

    @Bean
    public Declarables createPostDispatchSchema() {
        return new Declarables(
                new FanoutExchange("x.post-email-dispatch"),
                new Queue(rabbitProperties.getEmailDispatchQueue(), true),
                new Binding(rabbitProperties.getEmailDispatchQueue(), Binding.DestinationType.QUEUE,
                        "x.post-email-dispatch", rabbitProperties.getEmailDispatchRoutingKey(), null),

                new FanoutExchange("x.post-whatsapp-dispatch"),
                new Queue(rabbitProperties.getWhatsappDispatchQueue(), true),
                new Binding(rabbitProperties.getWhatsappDispatchQueue(), Binding.DestinationType.QUEUE,
                        "x.post-whatsapp-dispatch", rabbitProperties.getWhatsappDispatchRoutingKey(), null),

                new FanoutExchange("x.post-sms-dispatch"),
                new Queue(rabbitProperties.getSmsDispatchQueue(), true),
                new Binding(rabbitProperties.getSmsDispatchQueue(), Binding.DestinationType.QUEUE,
                        "x.post-sms-dispatch", rabbitProperties.getSmsDispatchRoutingKey(), null));
    }

    @Bean
    public Declarables createDeadLetterSchema() {
        return new Declarables(
                new DirectExchange("x.email-dispatch-failure"),
                new Queue("q.fall-back-email-dispatch"),
                new Binding("q.fall-back-email-dispatch", Binding.DestinationType.QUEUE, "x.email-dispatch-failure",
                        "email-fall-back", null),

                new DirectExchange("x.whatsapp-dispatch-failure"),
                new Queue("q.fall-back-whatsapp-dispatch"),
                new Binding("q.fall-back-whatsapp-dispatch", Binding.DestinationType.QUEUE,
                        "x.whatsapp-dispatch-failure", "whatsapp-fall-back", null),

                new DirectExchange("x.sms-dispatch-failure"),
                new Queue("q.fall-back-sms-dispatch"),
                new Binding("q.fall-back-sms-dispatch", Binding.DestinationType.QUEUE, "x.sms-dispatch-failure",
                        "sms-fall-back", null));
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            JacksonJsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(converter); // important!
        // factory.setBatchListener(true);
        return factory;
    }

    /// Queue creation
    @Bean
    public Queue createEmailQueue() {

        return QueueBuilder.durable(rabbitProperties.getEmailQueue())
                .build();
    }

    @Bean
    public Queue createWhatappQueue() {
        return QueueBuilder.durable(rabbitProperties.getWhatsappQueue())
                .build();
    }

    @Bean
    public Queue createSmsQueue() {
        return QueueBuilder.durable(rabbitProperties.getSmsQueue())
                .build();
    }

    /// Queue schema
    @Bean
    public Declarables createEmailQueueSchema() {

        return new Declarables(
                new DirectExchange(rabbitProperties.getEmailQueueExchange()),
                emailQueue(),
                emailQueueBinding());
    }

    @Bean
    public Declarables createWhatsappQueueSchema() {

        return new Declarables(
                new DirectExchange(rabbitProperties.getWhatsappQueueExchange()),
                whatsappQueue(),
                whatsappQueueBinding());
    }

    @Bean
    public Declarables createSmsQueueSchema() {

        return new Declarables(
                new DirectExchange(rabbitProperties.getSmsQueueExchange()),
                smsQueue(),
                smsQueueBinding());
    }

    /// Queue definitions
    @Bean
    Queue emailQueue() {
        return new Queue(rabbitProperties.getEmailQueue(), true);
    }

    @Bean
    Queue whatsappQueue() {
        return new Queue(rabbitProperties.getWhatsappQueue(), true);
    }

    @Bean
    Queue smsQueue() {
        return new Queue(rabbitProperties.getSmsQueue(), true);
    }

    /// Queue exchanges
    @Bean
    DirectExchange emailQueueExchange() {
        return new DirectExchange(rabbitProperties.getEmailQueueExchange());
    }

    @Bean
    DirectExchange whatsappQueueExchange() {
        return new DirectExchange(rabbitProperties.getWhatsappQueueExchange());
    }

    @Bean
    DirectExchange smsQueueExchange() {
        return new DirectExchange(rabbitProperties.getSmsQueueExchange());
    }

    /// Queue bindings
    @Bean
    Binding emailQueueBinding() {
        return BindingBuilder.bind(emailQueue()).to(emailQueueExchange())
                .with(rabbitProperties.getEmailQueueRoutingKey());
    }

    @Bean
    Binding whatsappQueueBinding() {
        return BindingBuilder.bind(whatsappQueue()).to(whatsappQueueExchange())
                .with(rabbitProperties.getWhatsappQueueRoutingKey());
    }

    @Bean
    Binding smsQueueBinding() {
        return BindingBuilder.bind(smsQueue()).to(smsQueueExchange()).with(rabbitProperties.getSmsQueueRoutingKey());
    }

    @Bean
    public JacksonJsonMessageConverter converter(JsonMapper mapper) {
        return new JacksonJsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(JacksonJsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);

        // mapper.setDate;
        template.setMessageConverter(converter);
        return template;
    }
}
