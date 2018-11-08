package com.boot.rabbitmq.topics;

import com.boot.rabbitmq.Util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Topics {

    private final static String QUEUE_NAME = "test_topics_queueone";

    private final static String EXCHANGE_NAME = "test_topics_exchange";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"item.del");

        channel.basicQos(1);

        QueueingConsumer consumer  = new QueueingConsumer(channel);

        channel.basicConsume(QUEUE_NAME,false,consumer);
        while (true){

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("接受成功" + message);
            Thread.sleep(1000);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);

        }

    }

}
