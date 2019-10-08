package com.example.demo.system.util;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeLimit {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeLimit(){

    }

    public TimeLimit(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
