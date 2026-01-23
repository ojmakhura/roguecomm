package bw.co.roguesystems.comm.dispatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.message.CommMessageDTO;
import bw.co.roguesystems.comm.properties.RabbitProperties;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FacebookDispatchListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public FacebookDispatchListener(RabbitTemplate rabbitTemplate, RabbitProperties rabbitProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitProperties = rabbitProperties;
    }

    @RabbitListener(queues = {"${app.rabbitmq.facebookPostDispatchQueue}"})
    public void onFacebookPostCommunication(CommMessageDTO facebookPostMessage) {
        log.info("Facebook Post communication message received: {}", facebookPostMessage);

        rabbitTemplate.convertAndSend("x.post-facebook-post-dispatch", "", facebookPostMessage);
    }

    @RabbitListener(queues = {"${app.rabbitmq.facebookMessageDispatchQueue}"})
    public void onFacebookMessageCommunication(CommMessageDTO facebookMessage) {
        log.info("Facebook Message communication message received: {}", facebookMessage);

        rabbitTemplate.convertAndSend("x.post-facebook-message-dispatch", "", facebookMessage);
    }
}
