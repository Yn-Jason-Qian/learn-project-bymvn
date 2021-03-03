package net.scat.lp.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class ServiceApplication {

    @Value("${spring.profiles.active}")
    private String service;

    @GetMapping("/hello")
    public String hello(@RequestParam String name, HttpServletRequest request) {
        System.out.println(name);
        return "Hello " + name + ", this is " + service;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class);
    }
}
