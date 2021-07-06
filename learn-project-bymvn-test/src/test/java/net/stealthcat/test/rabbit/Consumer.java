package net.stealthcat.test.rabbit;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.136.131");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection("consumer1");

        connection.addBlockedListener(new BlockedListener() {
            @Override
            public void handleBlocked(String reason) throws IOException {
                connection.close();
            }

            @Override
            public void handleUnblocked() throws IOException {

            }
        });
        connection.addShutdownListener(Throwable::printStackTrace);
        Channel channel = connection.createChannel(1);
        channel.queueDeclare("test", false, false, false , null);
        channel.basicQos(1);
        channel.basicConsume("test", false, (consumerTag, message) -> {

            try {
//                System.out.println("Message delivery tag:" + message.getEnvelope().getDeliveryTag());
                String msgStr = new String(message.getBody());
                System.out.printf("%tT[channel1]: Message str = %s%n", new Date(), msgStr);
//                System.out.println("Redeliver: " + message.getEnvelope().isRedeliver());
//                if ("test1".equals(msgStr)) {
//                    if (message.getEnvelope().isRedeliver()) {
//                        channel1.basicAck(message.getEnvelope().getDeliveryTag(), false);
//                    } else {
//                        channel1.basicNack(message.getEnvelope().getDeliveryTag(), false, true);
//                    }
//                } else {
//                }
//                Thread.sleep(1000);
                if (message.getEnvelope().isRedeliver()) {
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } else {
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, consumerTag -> {
            System.out.println("consumer is cancelled.");
        });

        Channel channel1 = connection.createChannel(2);
        channel1.queueDeclare("test", false, false, false , null);
        channel1.basicQos(1);
        channel1.basicConsume("test", false, (consumerTag, message) -> {

            try {
//                System.out.println("Message delivery tag:" + message.getEnvelope().getDeliveryTag());
                String msgStr = new String(message.getBody());
                System.out.printf("%tT[channel2]: Message str = %s%n", new Date(), msgStr);
//                Thread.sleep(2000);
//                System.out.println("Redeliver: " + message.getEnvelope().isRedeliver());
//                if ("test1".equals(msgStr)) {
//                    if (message.getEnvelope().isRedeliver()) {
//                        channel1.basicAck(message.getEnvelope().getDeliveryTag(), false);
//                    } else {
//                        channel1.basicNack(message.getEnvelope().getDeliveryTag(), false, true);
//                    }
//                } else {
//                }
                channel1.basicAck(message.getEnvelope().getDeliveryTag(), false);
            } catch (Exception e) {
                e.printStackTrace();
                channel1.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, consumerTag -> {
            System.out.println("consumer is cancelled.");
        });
    }
}
