package bw.co.roguesystems.comm.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import bw.co.roguesystems.comm.properties.FacebookProperties;

@Configuration
@EnableConfigurationProperties(FacebookProperties.class)
public class FacebookConfiguration {
    
    private final FacebookProperties facebookProperties;

    public FacebookConfiguration(FacebookProperties facebookProperties) {
        this.facebookProperties = facebookProperties;
    }
    
}
