package com.example.demo.system.model.po;

import com.example.demo.system.model.bo.BaseProtocolIn;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Description: 数据库model
 */
//字段值为空的不展示
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@ApiModel(value = "数据库model")
public class TestPo extends BaseProtocolIn {

//    判断非空
//    @NotBlank(message = "id不能为空")
//    字段值效验
//    @Pattern(regexp = "^[0-9]*[1-9][0-9]*$", message = "page只允许是正整数")
    private String id;
//    字段说明
//    @ApiModelProperty(value = "说明")
    private String planNo;
//    字段长度效验
//    @Length(max = 16, message = "最大只能输入16个字符")
    private String endTime;
    private String statTime;
    private String createTime;
    private String updateTime;
    private String doc;
    private String url;

}
