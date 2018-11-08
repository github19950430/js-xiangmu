package com.boot.rabbitmq.send;

import com.boot.rabbitmq.Util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TopicsSend {

    private final static String EXCHANGE_NAME = "test_topics_exchange";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //消息内容
        String message = "topic发送hello world";
        channel.basicPublish(EXCHANGE_NAME,"item.del",null,message.getBytes());
        System.out.println("topics发送成功!" + message);

        channel.close();
        connection.close();
    }

}
