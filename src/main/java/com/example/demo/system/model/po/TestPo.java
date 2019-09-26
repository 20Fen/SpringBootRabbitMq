package com.example.demo.system.model.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Description:
 *
 * @author yangfl
 * @date 2019年09月24日 16:30
 * Version 1.0
 */
@Data
@ApiModel(value = "数据库model")
public class TestPo {

    private String id;
    private String planNo;
    private String endTime;
    private String statTime;
    private String createTime;
    private String doc;
    private String url;

}
