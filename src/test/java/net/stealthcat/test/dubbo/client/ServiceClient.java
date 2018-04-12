package net.stealthcat.test.dubbo.client;

import net.stealthcat.test.dubbo.IService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by qianyang on 18-3-27.
 */
public class ServiceClient {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/consumer.xml");
        IService bean = context.getBean(IService.class);
        System.out.println(bean);
        bean.service();
//        ServiceConsumer consumer = context.getBean(ServiceConsumer.class);
//        consumer.execute();
    }
}
