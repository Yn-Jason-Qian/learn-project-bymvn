package net.scat.lp.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
public class ConsumerApplication {
    @Autowired
    private Service service;

    @GetMapping("/sayHello")
    public String sayHello() {
        return service.hello("123");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsumerApplication.class).build().run(args);
    }
}
