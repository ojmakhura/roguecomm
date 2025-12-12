package bw.co.roguesystems.comm.dispatch;

import java.time.LocalDateTime;

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
public class EmailSender {

    @Value("${spring.mail.username}")
    private String fromEmailAddress;

    private final CommMessageDao messageDao;
    private final CommMessageRepository messageRepository;
    private final JavaMailSender mailSender;
    /**
     * Sends an email message. This method is called directly by other services,
     * not through RabbitMQ to avoid duplicate processing.
     */
    @Async("virtualThreadExecutor")
    public void sendEmail(CommMessageDTO emailMessage) {

        System.out.println("Running on thread: " + Thread.currentThread());

        log.info("Processing email dispatch for destinations: {}", emailMessage.getDestinations());
        
        // Check if message has already been processed to prevent duplicates
        CommMessage message = messageDao.commMessageDTOToEntity(emailMessage);
        
        // Check if this message was already sent successfully
        if (message.getId() != null && MessageDispatchStatus.SENT.equals(message.getStatus())) {
            log.warn("Email message with ID {} has already been sent. Skipping duplicate processing.", message.getId());
            return;
        }

        try {
            // Create and send the email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(emailMessage.getSubject());
            helper.setFrom(fromEmailAddress);
            helper.setReplyTo(emailMessage.getSource());
            helper.setText(emailMessage.getText(), true);
            helper.setTo(emailMessage.getDestinations().toArray(new String[0]));

            mailSender.send(mimeMessage);
            
            log.info("Email sent successfully to {}", emailMessage.getDestinations());

            // Update message status on successful send
            if (message.getId() == null) {
                message.setCreatedAt(LocalDateTime.now());
            }
            message.setStatus(MessageDispatchStatus.SENT);
            message.setDispatchDate(LocalDateTime.now());

            message = messageRepository.saveAndFlush(message);
            
            log.debug("Email message status updated to SENT for ID: {}", message.getId());

        } catch (MailException e) {
            log.error("Failed to send email to {}: {}", emailMessage.getDestinations(), e.getMessage());

            // Update message status on failure
            if (message.getId() == null) {
                message.setCreatedAt(LocalDateTime.now());
            }
            message.setStatus(MessageDispatchStatus.FAILED);
            message.setDispatchDate(LocalDateTime.now());
            
            try {
                messageRepository.saveAndFlush(message);
                log.debug("Email message status updated to FAILED for ID: {}", message.getId());
            } catch (Exception dbException) {
                log.error("Failed to update message status in database: {}", dbException.getMessage());
            }

            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while processing email to {}: {}", emailMessage.getDestinations(), e.getMessage(), e);
            
            // Update message status on unexpected failure
            if (message.getId() == null) {
                message.setCreatedAt(LocalDateTime.now());
            }
            message.setStatus(MessageDispatchStatus.FAILED);
            message.setDispatchDate(LocalDateTime.now());
            
            try {
                messageRepository.saveAndFlush(message);
            } catch (Exception dbException) {
                log.error("Failed to update message status in database: {}", dbException.getMessage());
            }
            
            throw new RuntimeException("Email processing failed", e);
        }
    }
}
