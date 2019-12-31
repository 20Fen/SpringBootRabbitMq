package com.example.demo.system.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * Description: 省级区域
 */
@Data
public class CityModel implements Serializable {

    private String id;
    private String pid;
    private String name;

    private List<CityModel> citys;
}
