package net.stealthcat.test.rabbit;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        Connection connection = connectionFactory.newConnection("localhost");
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
        Channel channel = connection.createChannel();
        channel.queueDeclare("test", false, false, false , null);

        channel.basicConsume("test", false, (consumerTag, message) -> {

            try {
                System.out.println("Message delivery tag:" + message.getEnvelope().getDeliveryTag());
                String msgStr = new String(message.getBody());
                System.out.println("Message str:" + msgStr);
                if ("test1".equals(msgStr)) {
                    if (message.getEnvelope().isRedeliver()) {
                        channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    } else {
                        channel.basicNack(message.getEnvelope().getDeliveryTag(), false, true);
                    }
                } else {
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, consumerTag -> {
            System.out.println("consumer is cancelled.");
        });
    }
}
