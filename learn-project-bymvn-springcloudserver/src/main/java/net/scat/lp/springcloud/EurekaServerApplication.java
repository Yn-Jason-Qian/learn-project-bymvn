package net.scat.lp.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println(Arrays.asList(run.getEnvironment().getActiveProfiles()));
    }
}
