package bw.co.roguesystems.comm.dispatch;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.message.CommMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WhatsappSender {
        
    @Async("virtualThreadExecutor")
    public void sendWhatsapp(CommMessageDTO whatsappMessage) {

        System.out.println("Running on thread: " + Thread.currentThread());

        log.info("Processing WhatsApp dispatch for destinations: {}", whatsappMessage.getDestinations());
        
    }
}
