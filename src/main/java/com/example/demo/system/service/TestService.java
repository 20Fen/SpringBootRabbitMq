package com.example.demo.system.service;

import com.example.demo.system.model.bo.FindAllTest;
import com.example.demo.system.model.po.TestPo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Description:
 *
 * @author yangfl
 * @date 2019年09月24日 15:20
 * Version 1.0
 */

public interface TestService {
    //    根据条件查询
    PageInfo<TestPo> findAll(Integer page, Integer pageSize, FindAllTest findAllTest) throws Exception;

    //    新建和修改
    String insert(TestPo test) throws Exception;

    //    根据id查询
    TestPo getById(String planNo) throws Exception;

    //    根据id查询上月数据
    TestPo getByIdMonth(String planNo) throws Exception;

    //    上传文件
    String upload(String planNo, MultipartFile file) throws Exception;

    //    删除数据并删除文件
    String deleteById(String planNo) throws Exception;

    //    删除文件并更新数据库字段
    String deleteUrl(String planNo) throws Exception;

    TestPo getById(Map<String, Object> map);
}
