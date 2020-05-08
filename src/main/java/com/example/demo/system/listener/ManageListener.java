package com.example.demo.system.listener;


import com.example.demo.system.dao.mapper.TestMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ManageListener {

    @Resource
    private TestMapper testMapper;

    @RabbitListener(queues = "${spring.rabbitmq-queue-name}")
    @RabbitHandler
    public void del(Message message, Channel channel) throws IOException {
        Object payload = message.getPayload();
        Long shopId = Long.valueOf(payload.toString());
        Map map=new HashMap();
        testMapper.deleteById(map);

        long deliveryTag = (long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //手动ACK
        channel.basicAck(deliveryTag,false);

    }
}
