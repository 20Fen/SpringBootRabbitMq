package com.example.demo.system.controller;

import com.example.demo.system.model.bo.TestBo;
import com.example.demo.system.model.po.*;
import com.example.demo.system.service.TestService;
import com.example.demo.system.util.*;
import com.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description: controller restful风格
 */
@Log4j2
@RestController

@Api(tags = "springBoot测试")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;



    @GetMapping(value = "/find")
    @ApiOperation("查询全部")
    public AjaxResult find(@Valid PageReq page) {


        PageInfo<TestPo> all = testService.find(page);
        return success(ReturnInfo.QUERY_SUCCESS_MSG, all);
    }


}
