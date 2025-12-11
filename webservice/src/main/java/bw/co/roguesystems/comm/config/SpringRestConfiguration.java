package bw.co.roguesystems.comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class SpringRestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
        
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        
        return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
