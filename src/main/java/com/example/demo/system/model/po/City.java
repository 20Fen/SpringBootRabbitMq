package com.example.demo.system.model.po;

import lombok.Data;

import java.io.Serializable;


/**
 * Description: 省级区域
 */
@Data
public class City implements Serializable {

    private String id;
    private String pid;
    private String name;
}
