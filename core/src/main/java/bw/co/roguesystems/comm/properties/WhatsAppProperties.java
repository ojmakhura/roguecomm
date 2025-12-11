package bw.co.roguesystems.comm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "app.whatsapp")
public class WhatsAppProperties {

    private Map<String, WhatsAppConfig> configs = new HashMap<>();
    
    // Format: JSON array of WhatsApp configurations
    private String configString;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, WhatsAppConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, WhatsAppConfig> configs) {
        this.configs = configs;
    }

    public String getConfigString() {
        return configString;
    }

    public void setConfigString(String configString) {
        this.configString = configString;
    }

    @PostConstruct
    public void parseConfigString() {
        if (configString != null && !configString.isBlank()) {
            try {
                List<WhatsAppConfig> configList = objectMapper.readValue(
                    configString, 
                    new TypeReference<List<WhatsAppConfig>>() {}
                );
                
                for (WhatsAppConfig config : configList) {
                    // Set defaults if not provided
                    if (config.getApiUrl() == null || config.getApiUrl().isBlank()) {
                        config.setApiUrl("https://api.whatsapp.com/v1");
                    }
                    if (config.getMaxRetries() == 0) {
                        config.setMaxRetries(3);
                    }
                    if (config.getTimeoutSeconds() == 0) {
                        config.setTimeoutSeconds(30);
                    }
                    
                    configs.put(config.getPhoneNumber(), config);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse WhatsApp configuration JSON", e);
            }
        }
    }

    public WhatsAppConfig getConfigByPhoneNumber(String phoneNumber) {
        return configs.get(phoneNumber);
    }

    public static class WhatsAppConfig {
        private String phoneNumber;
        private String apiUrl;
        private String apiKey;
        private String apiToken;
        private String webhookUrl;
        private boolean enabled;
        private int maxRetries = 3;
        private int timeoutSeconds = 30;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getApiToken() {
            return apiToken;
        }

        public void setApiToken(String apiToken) {
            this.apiToken = apiToken;
        }

        public String getWebhookUrl() {
            return webhookUrl;
        }

        public void setWebhookUrl(String webhookUrl) {
            this.webhookUrl = webhookUrl;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public int getTimeoutSeconds() {
            return timeoutSeconds;
        }

        public void setTimeoutSeconds(int timeoutSeconds) {
            this.timeoutSeconds = timeoutSeconds;
        }

        @Override
        public String toString() {
            return "WhatsAppConfig{" +
                    "phoneNumber='" + phoneNumber + '\'' +
                    ", apiUrl='" + apiUrl + '\'' +
                    ", apiKey='" + (apiKey != null ? "***" : "null") + '\'' +
                    ", apiToken='" + (apiToken != null ? "***" : "null") + '\'' +
                    ", webhookUrl='" + webhookUrl + '\'' +
                    ", enabled=" + enabled +
                    ", maxRetries=" + maxRetries +
                    ", timeoutSeconds=" + timeoutSeconds +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WhatsAppProperties{" +
                "configs=" + configs +
                '}';
    }
}
