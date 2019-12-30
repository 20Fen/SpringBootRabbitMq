package com.example.demo.system.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Description:分页
 */
@Data
@ApiModel(value = "分页")
public class PageReq implements Serializable {

    @Pattern(regexp = "^[0-9]*[1-9][0-9]*$",message = "page只允许是正整数")
    @NotBlank(message = "page不能为空")
    @ApiModelProperty(value = "当前页")
    @Min(value = 1,message = "当前页必须大于0")
    private String page;
    @Pattern(regexp = "^[0-9]*[1-9][0-9]*$",message = "pageSize只允许是正整数")
    @NotBlank(message = "pageSize不能为空")
    @ApiModelProperty(value = "每页条数")
    @Min(value = 1,message = "当前页必须大于0")
    private String pageSize;
    @ApiParam(value = "是否强制分页", defaultValue = "true",hidden=true)
    private boolean forcePage = true;

    @Transient
    public boolean isNeedPage() {
        boolean isPage = (page != null&& Integer.parseInt(page) > 0 && pageSize != null && Integer.parseInt(pageSize) > 0);
        if(forcePage &&! isPage) {
            page = String.valueOf(1);
            pageSize = String.valueOf(10);
            isPage = true;
        }
        return isPage;
    }
}
