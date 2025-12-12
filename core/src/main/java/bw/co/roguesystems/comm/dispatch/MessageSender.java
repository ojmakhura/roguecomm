package bw.co.roguesystems.comm.dispatch;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import bw.co.roguesystems.comm.MessageDispatchStatus;
import bw.co.roguesystems.comm.message.CommMessage;
import bw.co.roguesystems.comm.message.CommMessageDTO;
import bw.co.roguesystems.comm.message.CommMessageDao;
import bw.co.roguesystems.comm.message.CommMessageRepository;
import bw.co.roguesystems.comm.message.CommMessageService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSender {
    
    private final CommMessageService messageService;
    private final EmailSender emailSender;
    private final WhatsappSender whatsappSender;
    private final SmsSender smsSender;

    @RabbitListener(queues = "${app.rabbitmq.emailQueue}")
    public void readEmailQueue(Collection<CommMessageDTO> emailMessages) {

        log.info("Getting {} from email queue.", emailMessages.size());
        
        for (CommMessageDTO emailMessage : emailMessages) {
            
            if(emailMessage.getId() != null) {
                emailMessage = messageService.findById(emailMessage.getId());
            }
            
            emailMessage = messageService.save(emailMessage);

            if(emailMessage.getSendNow()) {
                try {
                    // Send email directly using EmailService instead of RabbitMQ
                    emailSender.sendEmail(emailMessage);
                    log.info("Email sent successfully for message ID: {}", emailMessage.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Failed to send email for message ID {}: {}", emailMessage.getId(), e.getMessage());
                    // The EmailService will handle setting the status to FAILED
                }
            }
        }
    }

    

    @RabbitListener(queues = "${app.rabbitmq.smsQueue}")
    public void readSmsQueue(Collection<CommMessageDTO> smsMessages) {

        log.info("Getting {} from SMS queue.", smsMessages.size());
        
        for (CommMessageDTO smsMessage : smsMessages) {
            
            if(smsMessage.getId() != null) {
                // smsMessage = findById(smsMessage.getId());
            }
            
            // smsMessage = save(smsMessage);

            if(smsMessage.getSendNow()) {
                try {
                    // TODO: Implement SMS sending logic
                    log.info("SMS message queued for message ID: {}", smsMessage.getId());
                    smsSender.sendSms(smsMessage);
                    // For now, just mark as sent
                    // updateMessageStatus(smsMessage.getId(), MessageDispatchStatus.SENT);
                } catch (Exception e) {
                    log.error("Failed to process SMS for message ID {}: {}", smsMessage.getId(), e.getMessage());
                    // updateMessageStatus(smsMessage.getId(), MessageDispatchStatus.FAILED);
                }
            }
        }
    }

    @RabbitListener(queues = "${app.rabbitmq.whatsappQueue}")
    public void readWhatsAppQueue(Collection<CommMessageDTO> whatsappMessages) {

        log.info("Getting {} from WhatsApp queue.", whatsappMessages.size());
        
        for (CommMessageDTO whatsappMessage : whatsappMessages) {
            
            if(whatsappMessage.getId() != null) {
                // whatsappMessage = findById(whatsappMessage.getId());
            }
            
            // whatsappMessage = save(whatsappMessage);

            if(whatsappMessage.getSendNow()) {
                try {
                    // Send WhatsApp message using WhatsappService
                    whatsappSender.sendWhatsapp(whatsappMessage);
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
