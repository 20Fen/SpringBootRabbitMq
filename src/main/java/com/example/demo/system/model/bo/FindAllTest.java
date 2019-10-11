package com.example.demo.system.model.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Description: 条件查询model
 */
@Data
@ApiModel(value = "条件查询model")
public class FindAllTest extends BaseProtocolIn{

    private String planNo;
    private String statTime;
    private String endTime;
    private String createTime;
}
