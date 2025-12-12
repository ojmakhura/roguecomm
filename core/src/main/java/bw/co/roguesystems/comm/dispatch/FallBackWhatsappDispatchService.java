package bw.co.roguesystems.comm.dispatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.message.CommMessageDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FallBackWhatsappDispatchService {

    @RabbitListener(queues = {"q.fall-back-whatsapp-dispatch"})
    public void onWhatsappDispatchFailure(CommMessageDTO failedWhatsappDispatch) {
        log.info("Executing fallback for failed WhatsApp dispatch: {}", failedWhatsappDispatch);
    }
}
