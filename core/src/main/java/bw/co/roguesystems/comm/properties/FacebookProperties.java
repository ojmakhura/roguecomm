package bw.co.roguesystems.comm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "app.facebook")
public class FacebookProperties {

    private Map<String, FacebookConfig> configs = new HashMap<>();
    
    // Format: JSON array of Facebook Page configurations
    private String configString;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, FacebookConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, FacebookConfig> configs) {
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
                List<FacebookConfig> configList = objectMapper.readValue(
                    configString, 
                    new TypeReference<List<FacebookConfig>>() {}
                );
                
                for (FacebookConfig config : configList) {
                    // Set defaults if not provided
                    if (config.getGraphApiVersion() == null || config.getGraphApiVersion().isBlank()) {
                        config.setGraphApiVersion("v18.0");
                    }

                    if (config.getMaxRetries() == 0) {
                        config.setMaxRetries(3);
                    }
                    
                    if (config.getTimeoutSeconds() == 0) {
                        config.setTimeoutSeconds(30);
                    }
                    
                    configs.put(config.getPageId(), config);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse Facebook configuration JSON", e);
            }
        }
    }

    public FacebookConfig getConfigByPageId(String pageId) {
        return configs.get(pageId);
    }

    public static class FacebookConfig {
        private String pageId;
        private String pageAccessToken;
        private String appId;
        private String appSecret;
        private String graphApiVersion;
        private String webhookUrl;
        private boolean enabled;
        private int maxRetries = 3;
        private int timeoutSeconds = 30;

        public String getPageId() {
            return pageId;
        }

        public void setPageId(String pageId) {
            this.pageId = pageId;
        }

        public String getPageAccessToken() {
            return pageAccessToken;
        }

        public void setPageAccessToken(String pageAccessToken) {
            this.pageAccessToken = pageAccessToken;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getGraphApiVersion() {
            return graphApiVersion;
        }

        public void setGraphApiVersion(String graphApiVersion) {
            this.graphApiVersion = graphApiVersion;
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
            return "FacebookConfig{" +
                    "pageId='" + pageId + '\'' +
                    ", pageAccessToken='" + (pageAccessToken != null ? "***" : "null") + '\'' +
                    ", appId='" + appId + '\'' +
                    ", appSecret='" + (appSecret != null ? "***" : "null") + '\'' +
                    ", graphApiVersion='" + graphApiVersion + '\'' +
                    ", webhookUrl='" + webhookUrl + '\'' +
                    ", enabled=" + enabled +
                    ", maxRetries=" + maxRetries +
                    ", timeoutSeconds=" + timeoutSeconds +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FacebookProperties{" +
                "configs=" + configs +
                '}';
    }
}
