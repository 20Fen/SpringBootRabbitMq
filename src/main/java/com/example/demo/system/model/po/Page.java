package com.example.demo.system.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Description:
 */
@Data
@Setter
@Getter
@ApiModel(value = "分页")
public class Page {

    @Pattern(regexp = "^[0-9]*[1-9][0-9]*$",message = "page只允许是正整数")
    @NotBlank(message = "page不能为空")
    @ApiModelProperty(value = "当前页")
    private String page;
    @Pattern(regexp = "^[0-9]*[1-9][0-9]*$",message = "pageSize只允许是正整数")
    @NotBlank(message = "pageSize不能为空")
    @ApiModelProperty(value = "每页条数")
    private String pageSize;
}
