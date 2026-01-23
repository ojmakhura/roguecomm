package bw.co.roguesystems.comm.dispatch;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import bw.co.roguesystems.comm.MessageDispatchStatus;
import bw.co.roguesystems.comm.MessagingPlatform;
import bw.co.roguesystems.comm.message.CommMessage;
import bw.co.roguesystems.comm.message.CommMessageDTO;
import bw.co.roguesystems.comm.message.CommMessageDao;
import bw.co.roguesystems.comm.message.CommMessageRepository;
import bw.co.roguesystems.comm.properties.FacebookProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookSender {

    private final WebClient webClient;
    private final CommMessageDao messageDao;
    private final CommMessageRepository messageRepository;
    private final FacebookProperties facebookProperties;
    
    @Async("virtualThreadExecutor")
    public void sendFacebook(CommMessageDTO facebookMessage) {

        System.out.println("Running on thread: " + Thread.currentThread());

        log.info("Processing Facebook dispatch for destinations: {}", facebookMessage.getDestinations());

        // if(facebookMessage.getPlatform() != MessagingPlatform.FACEBOOK_MESSAGE) {
        //     log.error("Invalid platform for Facebook Message: {}", facebookMessage.getPlatform());
        //     throw new IllegalArgumentException("Invalid platform for Facebook Message");
        // }
        
    }
    
    @Async("virtualThreadExecutor")
    public void postFacebook(CommMessageDTO facebookPost) {

        System.out.println("Running on thread: " + Thread.currentThread());

        log.info("Processing Facebook dispatch for destinations: {}", facebookPost.getDestinations());

        // if(facebookPost.getPlatform() != MessagingPlatform.FACEBOOK_POST) {
        //     log.error("Invalid platform for Facebook Post: {}", facebookPost.getPlatform());
        //     throw new IllegalArgumentException("Invalid platform for Facebook Post");
        // }
        
        // Check if message has already been processed to prevent duplicates
        CommMessage message = messageDao.commMessageDTOToEntity(facebookPost);
        
        // Check if this message was already sent successfully
        // if (message.getId() != null && MessageDispatchStatus.SENT.equals(message.getStatus())) {
        //     log.warn("Email message with ID {} has already been sent. Skipping duplicate processing.", message.getId());
        //     return;
        // }
        
        if(CollectionUtils.isEmpty(message.getDestinations())) {

            log.error("No destinations provided for Facebook Post message");
            throw new IllegalArgumentException("No destinations provided for Facebook Post message");
        }

        String pageId = message.getDestinations().iterator().next();

        log.info("Posting to Facebook Page ID: {}", pageId);

        var config = facebookProperties.getConfigByPageId(pageId);

        if(config == null) {
            log.error("No Facebook configuration found for Page ID: {}", pageId);
            throw new IllegalArgumentException("No Facebook configuration found for Page ID: " + pageId);
        }

        try {
            Mono<String> responseMono = postToFacebookPage(pageId, message.getText(), config.getPageAccessToken());
            String response = responseMono.block();

            log.info("Facebook Post response: {}", response);

            // Update message status on successful post
            if (message.getId() == null) {
                message.setCreatedAt(java.time.LocalDateTime.now());
            }
            message.setStatus(MessageDispatchStatus.SENT);
            message.setDispatchDate(java.time.LocalDateTime.now());

            message = messageRepository.saveAndFlush(message);

        } catch (Exception e) {
            log.error("Failed to post to Facebook Page ID: {}", pageId, e);

            message.setStatus(MessageDispatchStatus.FAILED);
            message.setDispatchDate(java.time.LocalDateTime.now());

            try {
                messageRepository.saveAndFlush(message);
            } catch (Exception dbException) {
                log.error("Failed to update message status to FAILED for ID: {}", message.getId(), dbException);
            }

            throw new RuntimeException("Failed to post to Facebook Page ID: " + pageId, e);
        }

    }

    @Async
    public Mono<String> postToFacebookPage(String pageId, String message, String accessToken) {
        return webClient.post()
                .uri("/{pageId}/feed", pageId)
                .bodyValue("message=" + message + "&access_token=" + accessToken)
                .retrieve()
                .bodyToMono(String.class);
    }
}
