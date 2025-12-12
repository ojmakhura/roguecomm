package bw.co.roguesystems.comm.dispatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.message.CommMessageDTO;
import bw.co.roguesystems.comm.properties.RabbitProperties;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Slf4j
public class EmailDispatchListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public EmailDispatchListener(RabbitTemplate rabbitTemplate, RabbitProperties rabbitProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitProperties = rabbitProperties;
    }

    @RabbitListener(queues = {"${app.rabbitmq.emailDispatchQueue}"})
    public void onCommunication(CommMessageDTO emailMessage)  {
        log.info("Communication message Received: {}", emailMessage);

        rabbitTemplate.convertAndSend("x.post-email-dispatch","", emailMessage);
    }

    private void executeCommunication(CommMessageDTO emailMessage) {
        log.info("Executing Communication Event: {}", emailMessage);

        throw new RuntimeException("Communication Failed");

    }
}
