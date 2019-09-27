package com.example.demo.system.controller;

import com.example.demo.system.model.bo.FindAllTest;
import com.example.demo.system.model.po.TestPo;
import com.example.demo.system.service.TestService;
import com.example.demo.system.util.AjaxResult;
import com.example.demo.system.util.CustomException;
import com.example.demo.system.util.ReturnInfo;
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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @date 2019年09月24日 15:00
 * Version 1.0
 */

@RestController
@Api(tags="springBoot测试")
public class TestController extends BaseController{

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
    public AjaxResult insert(TestPo testPo) throws Exception {

        String testId = testService.insert(testPo);
        if (StringUtils.isEmpty(testPo.getPlanNo())) {
            return success(ReturnInfo.SAVE_SUCCESS_MSG, testId);
        } else {
            return success(ReturnInfo.UPDATE_SUCCESS_MSG, testId);
        }
    }

    @RequestMapping(value = "/byId", method = RequestMethod.GET)
    @ApiOperation("详情查看")
    public AjaxResult byId(String planNo) throws Exception {

        TestPo test = testService.getById(planNo);
        if(StringUtils.isEmpty(test)){
            return success(ReturnInfo.QUERY_FAIL_MSG);
        }
        return success((ReturnInfo.QUERY_SUCCESS_MSG),test);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    @ApiOperation("删除数据")
    public AjaxResult deleteById(String planNo) throws Exception {

        String byId = testService.deleteById(planNo);
        if(StringUtils.isEmpty(byId)){
            return success(ReturnInfo.DEL_FAIL_MSG);
        }
        return success((ReturnInfo.DEL_SUCCESS_MSG),byId);
    }

    @RequestMapping(value = "/deleteUrl", method = RequestMethod.GET)
    @ApiOperation("删除文件")
    public AjaxResult deleteUrl(String planNo) throws Exception {

        String byId = testService.deleteUrl(planNo);
        if(StringUtils.isEmpty(byId)){
            return success(ReturnInfo.DEL_FAIL_MSG);
        }
        return success((ReturnInfo.DEL_SUCCESS_MSG),byId);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("上传文件")
    public AjaxResult upload(String planNo, MultipartFile file) throws Exception {

        String name = testService.upload(planNo, file);
        if(name.equals("2")){
            return success(ReturnInfo.UPLOAD_FAIL_MSG);
        }
        return success((ReturnInfo.UPLOAD_SUCCESS_MSG),name);
    }

    @RequestMapping(value = "/downLoad", method = RequestMethod.GET)
    @ApiOperation("下载文件")
    public AjaxResult downLoad(String planNo, HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new CustomException("planNo为空");
        }
        map.put("planNo", planNo);
        TestPo test = testService.getById(map);
        if (null == test) {
            throw new CustomException("数据不存在");
        }
        if (null == test.getDoc() || test.getDoc().equals("") || null == test.getUrl() || test.getUrl().equals("")) {
            return success(ReturnInfo.DOWNLOAD_ERROR_MSG);
        }
        String fileName = test.getDoc();
        String fileUrl = test.getUrl();
        File file = new File(fileUrl);
        //如果文件不存在
        if (!file.exists()) {
            return success(ReturnInfo.DOWNLOAD_ERROR_MSG);
        }
//        匹配文件类型，保障上传的类型和下载的类型一致
        response.setHeader("Content-Type", "application/octet-stream");
//        设置在浏览器上下载的文件名
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        TestUtil.down(fileUrl, response);
        return success(ReturnInfo.DOWNLOAD_SUCCESS_MSG);
    }

}
