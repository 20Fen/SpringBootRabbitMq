package com.example.demo.system.service;

import com.example.demo.system.model.bo.FindAllTest;
import com.example.demo.system.model.po.TestPo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author yangfl
 * @date 2019年09月24日 15:20
 * Version 1.0
 */

public interface TestService {

    PageInfo<TestPo> findAll(Integer page,Integer pageSize,FindAllTest findAllTest) throws Exception;

    String insert(TestPo test) throws Exception;

    TestPo getById(String planNo) throws Exception;

    String upload(String planNo, MultipartFile file) throws Exception;

    String deleteById(String planNo) throws Exception;

}
