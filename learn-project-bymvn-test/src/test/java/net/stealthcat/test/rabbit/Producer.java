package net.stealthcat.test.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.136.132");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection("123456");

        Channel channel = connection.createChannel();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack " + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("nack " + deliveryTag);
            }
        });

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("return " + new String(body));
            }
        });
        channel.queueDeclare("test", false, false, false, null);
        Scanner scanner = new Scanner(System.in);
        String message = "message";
        for (int i = 0; i < 20; i++) {
            channel.basicPublish("", "test", null, (message + i).getBytes());
        }
//        while (!(message = scanner.nextLine()).equals("exit")) {
//            channel.basicPublish("", "test", null, message.getBytes());
//        }
        System.exit(0);
    }
}
