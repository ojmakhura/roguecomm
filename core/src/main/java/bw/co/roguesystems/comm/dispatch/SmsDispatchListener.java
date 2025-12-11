package bw.co.roguesystems.comm.dispatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.properties.RabbitProperties;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsDispatchListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public SmsDispatchListener(RabbitTemplate rabbitTemplate, RabbitProperties rabbitProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitProperties = rabbitProperties;
    }

    // @RabbitListener(queues = {"q.sms-dispatch"})
    // public void onCommunication(MesssageDTO smsMessage) {
    //     log.info("SMS communication message received: {}", smsMessage);

    //     rabbitTemplate.convertAndSend("x.post-sms-dispatch", "", smsMessage);
    // }
}
