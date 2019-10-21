package com.example.demo.system.model.po;

import com.example.demo.system.model.bo.BaseProtocolIn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * Description: 数据库model
 */
@Data
@ApiModel(value = "数据库model")
public class TestPo extends BaseProtocolIn {

//    @NotBlank(message = "id不能为空")
//    @ApiModelProperty(value = "振幅危险阈值(kPa)")
//    @Length(max = 16, message = "最大只能输入16个字符")
//    @Pattern(regexp = "^[0-9]*[1-9][0-9]*$", message = "page只允许是正整数")

private String id;
    private String planNo;
    private String endTime;
    private String statTime;
    private String createTime;
    private String doc;
    private String url;

}
