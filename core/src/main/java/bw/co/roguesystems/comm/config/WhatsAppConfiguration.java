package bw.co.roguesystems.comm.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import bw.co.roguesystems.comm.properties.WhatsAppProperties;

@Configuration
@EnableConfigurationProperties(WhatsAppProperties.class)
public class WhatsAppConfiguration {

    private final WhatsAppProperties whatsAppProperties;

    public WhatsAppConfiguration(WhatsAppProperties whatsAppProperties) {
        this.whatsAppProperties = whatsAppProperties;
    }

    // You can add additional beans here as needed for WhatsApp integration
    // For example: RestTemplate, WebClient, message converters, etc.
}
