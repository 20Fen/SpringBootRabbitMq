package com.example.demo.system.util;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sender(String exchange, String routingKey,Integer expireTime) {

        rabbitTemplate.convertAndSend(exchange, routingKey,message -> {
            message.getMessageProperties().setHeader("x-delay", expireTime);
            return message;
        });

    }
}
