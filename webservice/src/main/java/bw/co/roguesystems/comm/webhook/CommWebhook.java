package bw.co.roguesystems.comm.webhook;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.HexFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.proc.SecurityContext;

import bw.co.roguesystems.comm.message.CommMessageDTO;
import bw.co.roguesystems.comm.message.CommMessageService;
import bw.co.roguesystems.comm.whatsapp.WhatsAppWebhookPayload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class CommWebhook {

    private static final String SIGNATURE_HEADER = "X-Hub-Signature-256";

    @Value("${app.whatsapp.verificationToken}")
    private String verificationToken;

    @Value("${app.whatsapp.app-secret}")
    private String appSecret;

    private final JsonMapper jsonMapper = new JsonMapper();

    private final CommMessageService commMessageService;

    @GetMapping("/whatsapp")
    public ResponseEntity<String> verifyToken(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String verifyToken) {
        if (!"subscribe".equals(mode) || !verificationToken.equals(verifyToken)) {
            return ResponseEntity.status(403).body("Forbidden");
        }
        return ResponseEntity.ok(challenge);
    }

    @PostMapping("/whatsapp")
    public ResponseEntity<Void> receiveMessage(HttpServletRequest request,
            @RequestHeader(SIGNATURE_HEADER) String signature, @RequestParam String phoneNumber,
            @RequestParam String message) throws IOException {
        // Handle incoming WhatsApp messages here

        byte[] rawBody = request.getInputStream().readAllBytes();

        // 2️⃣ Verify HMAC SHA256 signature
        if (!verifySignature(rawBody, signature, appSecret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3️⃣ Parse JSON AFTER verification
        WhatsAppWebhookPayload payload = jsonMapper.readValue(rawBody, WhatsAppWebhookPayload.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymousUser";
        if (authentication != null) {

            username = authentication.getName();
        }

        Collection<CommMessageDTO> messages = commMessageService.processWhatsappWebhookPayload(username, payload);

        return ResponseEntity.ok().build();
    }

    private boolean verifySignature(byte[] payload, String headerSignature, String secret) {

        if (headerSignature == null || !headerSignature.startsWith("sha256=")) {
            return false;
        }
        String expectedHash = headerSignature.substring(7); // remove "sha256="

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(keySpec);
            byte[] computedHash = mac.doFinal(payload);
            String computedHex = HexFormat.of().formatHex(computedHash);

            return MessageDigest.isEqual(
                    computedHex.getBytes(StandardCharsets.UTF_8),
                    expectedHash.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return false;
        }
    }
}