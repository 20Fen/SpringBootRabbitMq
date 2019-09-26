package com.example.demo.system.controller;

import com.example.demo.system.model.bo.FindAllTest;
import com.example.demo.system.model.po.TestPo;
import com.example.demo.system.service.TestService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @date 2019年09月24日 15:00
 * Version 1.0
 */

@RestController
@Api("springBoot测试")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    @ApiOperation("按条件查询，查询全部")
    public PageInfo<TestPo> findAll(Integer page,Integer pageSize,FindAllTest findAllTest) throws Exception {


        PageInfo<TestPo> all = testService.findAll(page,pageSize,findAllTest);
        return all;
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ApiOperation("添加数据")
    public String insert(TestPo testPo) throws Exception {

        String testId = testService.insert(testPo);
        if (StringUtils.isEmpty(testId)) {
            return "失败";
        }
        return testId;
    }

    @RequestMapping(value = "/byId",method = RequestMethod.GET)
    @ApiOperation("详情查看")
    public TestPo byId(String planNo) throws Exception {

        TestPo test= testService.getById(planNo);

        return test;
    }

    @RequestMapping(value = "/deleteById",method = RequestMethod.GET)
    @ApiOperation("删除")
    public String deleteById(String planNo) throws Exception {

        String byId = testService.deleteById(planNo);

        return byId;
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ApiOperation("上传文件")
    public String upload(String planNo, MultipartFile file) throws Exception {

        String name= testService.upload(planNo,file);

        return name;
    }
}
