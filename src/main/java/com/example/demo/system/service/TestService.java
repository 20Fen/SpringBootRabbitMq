package com.example.demo.system.service;

import com.example.demo.system.model.po.TestPo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Description: 接口类
 */

public interface TestService {
    //    根据条件查询
    PageInfo<TestPo> findAll(Integer page, Integer pageSize,Map<String,Object> map) throws Exception;

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

    //    批量删除数据并删除文件
    String delete(String[] planNo) throws Exception;

    //    删除文件并更新数据库字段
    String deleteUrl(String planNo) throws Exception;

    TestPo getById(Map<String, Object> map);
}
