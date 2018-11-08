package com.boot.rabbitmq.send;

import com.boot.rabbitmq.Util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class FanoutSend {

    private final static String EXCHANGE_NAME = "test_fanout_exchange";

    public static void main(String[] args) throws Exception {
        //创连接
        Connection connection = ConnectionUtil.getConnection();
        //创信道
        Channel channel = connection.createChannel();

        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //消息内容
        String message = "hello world +++";
        //发送消息
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("发送消息" + message + "成功!");

        //关闭信道和连接
        channel.close();
        connection.close();


    }
}
