package com.example.demo.system.controller;

import com.example.demo.system.model.bo.FindAllTest;
import com.example.demo.system.model.po.TestPo;
import com.example.demo.system.service.TestService;
import com.example.demo.system.util.TestUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    @ApiOperation("按条件查询，查询全部")
    public PageInfo<TestPo> findAll(Integer page, Integer pageSize, FindAllTest findAllTest) throws Exception {

        PageInfo<TestPo> all = testService.findAll(page, pageSize, findAllTest);
        return all;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation("添加数据")
    public String insert(TestPo testPo) throws Exception {

        String testId = testService.insert(testPo);
        if (StringUtils.isEmpty(testId)) {
            return "失败";
        }
        return testId;
    }

    @RequestMapping(value = "/byId", method = RequestMethod.GET)
    @ApiOperation("详情查看")
    public TestPo byId(String planNo) throws Exception {

        TestPo test = testService.getById(planNo);
        return test;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    @ApiOperation("删除数据")
    public String deleteById(String planNo) throws Exception {

        String byId = testService.deleteById(planNo);
        return byId;
    }

    @RequestMapping(value = "/deleteUrl", method = RequestMethod.GET)
    @ApiOperation("删除文件")
    public String deleteUrl(String planNo) throws Exception {

        String byId = testService.deleteUrl(planNo);
        return byId;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("上传文件")
    public String upload(String planNo, MultipartFile file) throws Exception {

        String name = testService.upload(planNo, file);
        return name;
    }

    @RequestMapping(value = "/downLoad", method = RequestMethod.GET)
    @ApiOperation("下载文件")
    public void downLoad(String planNo, HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new Exception("planNo为空");
        }
        map.put("planNo", planNo);
        TestPo test = testService.getById(map);
        if (null == test) {
            throw new Exception("数据不存在");
        }
        if (null == test.getDoc() && test.getDoc().equals("") && null == test.getUrl() && test.getUrl().equals("")) {
            throw new Exception("文件不存在");
        }
        String fileName = test.getDoc();
        String fileUrl = test.getUrl();
        File file = new File(fileUrl);
        //如果文件不存在
        if (!file.exists()) {
            throw new Exception("文件已被删除");
        }
//        匹配文件类型，保障上传的类型和下载的类型一致
        response.setHeader("Content-Type", "application/octet-stream");
//        设置在浏览器上下载的文件名
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        TestUtil.down(fileUrl, response);
    }

}
