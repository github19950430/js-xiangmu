package com.boot.rabbitmq.direct;

import com.boot.rabbitmq.Util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Direct {

    private final static String EXCHANGE_NAME = "test_direct_exchange";

    private final static String QUEUE_NAME = "test_direct_queue";

    public static void main(String[] args) throws Exception {

        //创建连接和信道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"key1");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"key2");
        //同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME,false,consumer);
        //获取消息
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("接受成功!" + message + "!!");
            Thread.sleep(1000);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }

}
