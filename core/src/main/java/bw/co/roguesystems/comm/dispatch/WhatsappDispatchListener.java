package bw.co.roguesystems.comm.dispatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.message.CommMessageDTO;
import bw.co.roguesystems.comm.properties.RabbitProperties;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WhatsappDispatchListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public WhatsappDispatchListener(RabbitTemplate rabbitTemplate, RabbitProperties rabbitProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitProperties = rabbitProperties;
    }

    @RabbitListener(queues = {"${app.rabbitmq.whatsappDispatchQueue}"})
    public void onCommunication(CommMessageDTO whatsappMessage) {
        log.info("WhatsApp communication message received: {}", whatsappMessage);

        rabbitTemplate.convertAndSend("x.post-whatsapp-dispatch", "", whatsappMessage);
    }
}
