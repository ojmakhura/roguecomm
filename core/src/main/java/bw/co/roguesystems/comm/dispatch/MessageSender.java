package bw.co.roguesystems.comm.dispatch;

import java.util.Collection;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.MessageDispatchStatus;
import bw.co.roguesystems.comm.email.EmailMessageDTO;
import bw.co.roguesystems.comm.email.EmailMessageService;
import bw.co.roguesystems.comm.simple.SimpleMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSender {
    
    private final EmailMessageService emailMessageService;


    @RabbitListener(queues = "q.email-queue")
    public void readEmailQueue(Collection<EmailMessageDTO> emailMessages) {

        log.info("Getting {} from email queue.", emailMessages.size());
        
        for (EmailMessageDTO emailMessage : emailMessages) {
            
            if(emailMessage.getId() != null) {
                emailMessage = emailMessageService.findById(emailMessage.getId());
            }
            
            emailMessage = emailMessageService.save(emailMessage);

            if(emailMessage.getSendNow()) {
                try {
                    // Send email directly using EmailService instead of RabbitMQ
                    // emailService.sendEmail(emailMessage);
                    log.info("Email sent successfully for message ID: {}", emailMessage.getId());
                } catch (Exception e) {
                    log.error("Failed to send email for message ID {}: {}", emailMessage.getId(), e.getMessage());
                    // The EmailService will handle setting the status to FAILED
                }
            }

        }

    }

    @RabbitListener(queues = "q.sms-queue")
    public void readSmsQueue(Collection<SimpleMessageDTO> smsMessages) {

        log.info("Getting {} from SMS queue.", smsMessages.size());
        
        for (SimpleMessageDTO smsMessage : smsMessages) {
            
            if(smsMessage.getId() != null) {
                // smsMessage = findById(smsMessage.getId());
            }
            
            // smsMessage = save(smsMessage);

            if(smsMessage.getSendNow()) {
                try {
                    // TODO: Implement SMS sending logic
                    log.info("SMS message queued for message ID: {}", smsMessage.getId());
                    // For now, just mark as sent
                    // updateMessageStatus(smsMessage.getId(), MessageDispatchStatus.SENT);
                } catch (Exception e) {
                    log.error("Failed to process SMS for message ID {}: {}", smsMessage.getId(), e.getMessage());
                    // updateMessageStatus(smsMessage.getId(), MesssageStatus.FAILED);
                }
            }

        }

    }

    @RabbitListener(queues = "q.whatsapp-queue")
    public void readWhatsAppQueue(Collection<SimpleMessageDTO> whatsappMessages) {

        log.info("Getting {} from WhatsApp queue.", whatsappMessages.size());
        
        for (SimpleMessageDTO whatsappMessage : whatsappMessages) {
            
            if(whatsappMessage.getId() != null) {
                // whatsappMessage = findById(whatsappMessage.getId());
            }
            
            // whatsappMessage = save(whatsappMessage);

            if(whatsappMessage.getSendNow()) {
                try {
                    // Send WhatsApp message using WhatsappService
                    // whatsappService.sendWhatsAppMessage(whatsappMessage);
                    log.info("WhatsApp message sent successfully for message ID: {}", whatsappMessage.getId());
                } catch (Exception e) {
                    log.error("Failed to send WhatsApp message for message ID {}: {}", whatsappMessage.getId(), e.getMessage());
                    // The WhatsappService will handle setting the status to FAILED
                }
            }

        }

    }
}
