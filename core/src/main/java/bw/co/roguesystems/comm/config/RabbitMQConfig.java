package bw.co.roguesystems.comm.config;

import java.util.concurrent.ExecutorService;

// import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bw.co.roguesystems.comm.properties.RabbitProperties;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitMQConfig {
    private final CachingConnectionFactory cachingConnectionFactory;
    private final RabbitProperties rabbitProperties;
    private final ExecutorService virtualThreadExecutor;

    public RabbitMQConfig(CachingConnectionFactory cachingConnectionFactory, RabbitProperties rabbitProperties, ExecutorService virtualThreadExecutor) {
        this.cachingConnectionFactory = cachingConnectionFactory;
        this.rabbitProperties = rabbitProperties;
        this.virtualThreadExecutor = virtualThreadExecutor;
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
    public Queue createFacebookPostExchangeQueue() {

        return QueueBuilder.durable(rabbitProperties.getFacebookPostHandler())
                .withArgument("x-dead-letter-exchange", "x.facebook-post-dispatch-failure")
                .withArgument("x-dead-letter-routing-key", "fall-back")
                .build();
    }

    @Bean
    public Queue createFacebookMessageExchangeQueue() {

        return QueueBuilder.durable(rabbitProperties.getFacebookMessageHandler())
                .withArgument("x-dead-letter-exchange", "x.facebook-message-dispatch-failure")
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
                        "x.post-sms-dispatch", rabbitProperties.getSmsDispatchRoutingKey(), null),

                new FanoutExchange("x.post-facebook-post-dispatch"),
                new Queue(rabbitProperties.getFacebookPostDispatchQueue(), true),
                new Binding(rabbitProperties.getFacebookPostDispatchQueue(), Binding.DestinationType.QUEUE,
                        "x.post-facebook-post-dispatch", rabbitProperties.getFacebookPostDispatchRoutingKey(), null),

                new FanoutExchange("x.post-facebook-message-dispatch"),
                new Queue(rabbitProperties.getFacebookMessageDispatchQueue(), true),
                new Binding(rabbitProperties.getFacebookMessageDispatchQueue(), Binding.DestinationType.QUEUE,
                        "x.post-facebook-message-dispatch", rabbitProperties.getFacebookMessageDispatchRoutingKey(), null));
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
                        "sms-fall-back", null),

                new DirectExchange("x.facebook-post-dispatch-failure"),
                new Queue("q.fall-back-facebook-post-dispatch"),
                new Binding("q.fall-back-facebook-post-dispatch", Binding.DestinationType.QUEUE,
                        "x.facebook-post-dispatch-failure", "facebook-post-fall-back", null),

                new DirectExchange("x.facebook-message-dispatch-failure"),
                new Queue("q.fall-back-facebook-message-dispatch"),
                new Binding("q.fall-back-facebook-message-dispatch", Binding.DestinationType.QUEUE,
                        "x.facebook-message-dispatch-failure", "facebook-message-fall-back", null));
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
        factory.setTaskExecutor(virtualThreadExecutor);
        factory.setPrefetchCount(1);
        
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

    @Bean
    public Queue createFacebookPostQueue() {
        return QueueBuilder.durable(rabbitProperties.getFacebookPostQueue())
                .build();
    }

    @Bean
    public Queue createFacebookMessageQueue() {
        return QueueBuilder.durable(rabbitProperties.getFacebookMessageQueue())
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

    @Bean
    public Declarables createFacebookPostQueueSchema() {

        return new Declarables(
                new DirectExchange(rabbitProperties.getFacebookPostQueueExchange()),
                facebookPostQueue(),
                facebookPostQueueBinding());
    }

    @Bean
    public Declarables createFacebookMessageQueueSchema() {

        return new Declarables(
                new DirectExchange(rabbitProperties.getFacebookMessageQueueExchange()),
                facebookMessageQueue(),
                facebookMessageQueueBinding());
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

    @Bean
    Queue facebookPostQueue() {
        return new Queue(rabbitProperties.getFacebookPostQueue(), true);
    }

    @Bean
    Queue facebookMessageQueue() {
        return new Queue(rabbitProperties.getFacebookMessageQueue(), true);
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

    @Bean
    DirectExchange facebookPostQueueExchange() {
        return new DirectExchange(rabbitProperties.getFacebookPostQueueExchange());
    }

    @Bean
    DirectExchange facebookMessageQueueExchange() {
        return new DirectExchange(rabbitProperties.getFacebookMessageQueueExchange());
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
    Binding facebookPostQueueBinding() {
        return BindingBuilder.bind(facebookPostQueue()).to(facebookPostQueueExchange())
                .with(rabbitProperties.getFacebookPostQueueRoutingKey());
    }

    @Bean
    Binding facebookMessageQueueBinding() {
        return BindingBuilder.bind(facebookMessageQueue()).to(facebookMessageQueueExchange())
                .with(rabbitProperties.getFacebookMessageQueueRoutingKey());
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
