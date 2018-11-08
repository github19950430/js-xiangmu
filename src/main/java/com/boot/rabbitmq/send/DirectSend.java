package com.boot.rabbitmq.send;

import com.boot.rabbitmq.Util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class DirectSend {

    private final static String EXCHANGE_NAME = "test_direct_exchange";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //消息内容
        String message = "hello world!!!!";
        channel.basicPublish(EXCHANGE_NAME,"key2",null,message.getBytes());
        System.out.println("发送成功" + message + "!!!");

        channel.close();
        connection.close();

    }
}
