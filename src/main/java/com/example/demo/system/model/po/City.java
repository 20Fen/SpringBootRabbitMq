package com.example.demo.system.model.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;


/**
 * Description: 省级区域
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class City implements Serializable {

    private String id;
    private String pid;
    private String name;
}
