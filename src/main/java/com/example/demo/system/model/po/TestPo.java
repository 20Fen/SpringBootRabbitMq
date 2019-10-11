package com.example.demo.system.model.po;

import com.example.demo.system.model.bo.BaseProtocolIn;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description: 数据库model
 */
@Data
@ApiModel(value = "数据库model")
public class TestPo extends BaseProtocolIn {

//    @NotBlank(message = "id不能为空")
    private String id;
    private String planNo;
    private String endTime;
    private String statTime;
    private String createTime;
    private String doc;
    private String url;

}
