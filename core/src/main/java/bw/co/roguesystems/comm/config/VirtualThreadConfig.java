package bw.co.roguesystems.comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
// @EnableAsync
public class VirtualThreadConfig {

    @Bean(name = "virtualThreadExecutor")
    public ExecutorService virtualThreadExecutor() {
        // Java 21+ only
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
