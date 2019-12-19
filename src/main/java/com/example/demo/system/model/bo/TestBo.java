package com.example.demo.system.model.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * Description: 入参
 */
@Data
@ApiModel(value = "入参")
public class TestBo extends BaseProtocolIn {


    private String planNo;
    private String endTime;
    private String statTime;
    private String doc;
    private String url;
}
