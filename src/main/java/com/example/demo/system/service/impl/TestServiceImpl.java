package com.example.demo.system.service.impl;

import com.example.demo.system.dao.mapper.TestMapper;
import com.example.demo.system.model.bo.FindAllTest;
import com.example.demo.system.model.po.TestPo;
import com.example.demo.system.service.TestService;
import com.example.demo.system.util.TestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Description:
 *
 * @author yangfl
 * @date 2019年09月24日 15:21
 * Version 1.0
 */
@Log4j2
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    @Value("${paths}")
    private String path;


    @Override
    public PageInfo<TestPo> findAll(Integer page, Integer pageSize, FindAllTest findAllTest) throws Exception {

        int limit = page != null ? page : 1;
        int offset = pageSize != null ? pageSize : 10;
        PageHelper.startPage(limit, offset);
        List<TestPo> all = testMapper.findAll(findAllTest);
        if (StringUtils.isEmpty(all)) {
            throw new Exception("查询失败");
        }

        return new PageInfo<>(all);
    }

    @Override
    @Transactional
    public String insert(TestPo test) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String planNoId = UUID.randomUUID().toString();

        String planNo = test.getPlanNo();
        planNoId = StringUtils.isEmpty(planNo) ? planNoId : planNo;

        TestPo testPo = new TestPo();
        testPo.setPlanNo(planNoId);
        testPo.setStatTime(test.getStatTime());
        testPo.setEndTime(test.getEndTime());
        testPo.setCreateTime(test.getCreateTime());

        if (StringUtils.isEmpty(planNo)) {
            testPo.setId(UUID.randomUUID().toString());
            testMapper.insertTest(testPo);
            return planNoId;
        } else {
            testPo.setPlanNo(planNo);
            map.put("planNo", planNo);
            TestPo testPo1 = testMapper.getById(map);
            if (null == testPo1) {
                throw new Exception("数据不存在");
            }
            testMapper.updataTest(testPo);
            return testPo1.getPlanNo();
        }
    }

    @Override
    @Transactional
    public TestPo getById(String planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new Exception("编号不能为空");
        }

        map.put("planNo", planNo);
        TestPo test = testMapper.getById(map);
        if (null == test) {
            throw new Exception("查询数据不存在");
        }
        return test;
    }

    @Override
    @Transactional
    public String deleteById(String planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new Exception("planNo为空");
        }

        map.put("planNo", planNo);
        TestPo test = testMapper.getById(map);
        if (null == test) {
            throw new Exception("数据不存在");
        }
        Path path = Paths.get(test.getUrl());
        boolean exists = Files.deleteIfExists(path);
        if (!exists) {
            throw new Exception("删除文件失败");
        }
        Integer integer = testMapper.deleteById(map);
        if (1 == integer) {
            return test.getPlanNo();
        }
        return null;
    }

    @Override
    public String deleteUrl(String planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new Exception("planNo为空");
        }
        map.put("planNo", planNo);
        TestPo test = testMapper.getById(map);
        if (null == test) {
            throw new Exception("数据不存在");
        }
        Path path = Paths.get(test.getUrl());
        boolean exists = Files.deleteIfExists(path);
        if (!exists) {
            throw new Exception("删除文件失败");
        }
        map.put("filename", "");
        map.put("url", "");
        testMapper.updataTestDoc(map);

        return test.getPlanNo();
    }


    @Override
    public String upload(String planNo, MultipartFile file) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (file.getSize() == 0) {
            throw new Exception("文件内容不不能为空");
        }

        if (StringUtils.isEmpty(planNo) || null == file) {
            throw new Exception("请输入必传值");
        }
        map.put("planNo", planNo);
        String savePath = path + File.separator + planNo;
        TestUtil.checkFilePath(savePath);
        String filename = file.getOriginalFilename();
        TestPo byId = testMapper.getById(map);

        if (null != byId.getDoc() && !byId.getDoc().equals("") && null != byId.getUrl() && !byId.getUrl().equals("")) {
            throw new Exception("文件已存在");
        }
        try {

            Integer integer = saveFile(savePath, file, filename);
            if (integer != 1) {
                throw new Exception("上传文件失败");
            }
        } catch (IOException e) {
            log.error(e);
        }
        map.put("planNo", planNo);
        map.put("filename", filename);
        map.put("url", savePath + File.separator + filename);
        testMapper.updataTestDoc(map);
        return filename;
    }



    /**
     * @return void
     * @description 上传文件
     * @Param [ savePath file ]
     */
    private Integer saveFile(String savePath, MultipartFile file, String filename) throws Exception {
        Integer num = checkFileSize(file.getSize(), savePath);
        if (num == 1) {
//            得到上传的文件的字节
            byte[] bytes = file.getBytes();
//            上传的文件的全路径
            Path path = Paths.get(savePath + File.separator + filename);
//            调用方法上传
            Files.write(path, bytes);
        }
        return 1;
    }

    /**
     * @return java.lang.Boolean
     * @description 检测文件大小
     * @Param [ size ]
     */
    private Integer checkFileSize(long size, String savePath) throws Exception {
//        获取存储当前文件后剩余的磁盘空间
        Long space = TestUtil.getFreeDiskSpace(savePath);
        File file = new File(savePath);
//        获取当前磁盘空间
        long usableSpace = file.getUsableSpace();
        System.out.println(usableSpace);
//        判断剩余的磁盘空间是否小于当前上传的文件大小
        if (space < size) {
            throw new Exception("磁盘空间不足");
        }
        return 1;
    }


    @Override
    public TestPo getById(Map<String, Object> map) {
        return testMapper.getById(map);

    }
}