package com.example.demo.system.task;

import com.example.demo.system.config.RabbitConfig;
import com.example.demo.system.util.DateUtil;
import com.example.demo.system.util.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitTask {

    @Autowired
    private RabbitUtil rabbitUtil;

    @Autowired
    private RabbitConfig rabbitConfig;


    @Scheduled(cron="0 0 1 * * ?")
    private void sendMessage(String time) throws Exception {

        String[] split = time.split("-");
        String start = DateUtil.formatDate(new Date())+ " " + split[0];

        long startExpireTime = DateUtil.parseTime(start, "yyyy-MM-dd HH:mm").getTime() - new Date().getTime();

        // 计算当前时间到开关店时间，将shopId分别放入延时队列
        rabbitUtil.sender(rabbitConfig.getOpenExchangeName(),rabbitConfig.getOpenRoutingName(),(int) startExpireTime
        );
    }

}
