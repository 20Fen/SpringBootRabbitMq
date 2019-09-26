package com.example.demo.system.model.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Description:
 *
 * @date 2019年09月25日 10:38
 * Version 1.0
 */
@Data
@ApiModel(value = "条件查询model")
public class FindAllTest {

    private String planNo;
    private String statTime;
    private String endTime;
    private String createTime;
}
