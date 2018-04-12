package net.stealthcat.test.dubbo.client;

import net.stealthcat.test.dubbo.IService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by qianyang on 18-3-27.
 */
@Component
public class ServiceConsumer {

    @Resource
    IService service;

    public void execute() throws Exception {
        String result = service.service();
        System.out.println("Consumer service, result = " + result);
    }
}
