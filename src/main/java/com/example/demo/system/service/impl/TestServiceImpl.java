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
import java.io.File;
import java.io.IOException;
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
    public PageInfo<TestPo> findAll(Integer page,Integer pageSize,FindAllTest findAllTest) throws Exception {

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
        if(null == test){
            throw new Exception("数据不存在");
        }
        Integer integer = testMapper.deleteById(map);
        if(1 == integer){
            return test.getPlanNo();
        }
        return null;
    }

    @Override
    public String upload(String planNo, MultipartFile file) throws Exception {

        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isEmpty(planNo) && null == file) {
            throw new Exception("请上传文件");
        }
        map.put("planNo", planNo);
        String savePath = path + File.separator + planNo;
        TestUtil.checkFilePath(savePath);
        String filename = file.getOriginalFilename();
        TestPo byId = testMapper.getById(map);

        if (null != byId.getDoc()) {
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
            byte[] bytes = file.getBytes();
            Path path = Paths.get(savePath + File.separator + filename);
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

        Long space = TestUtil.getFreeDiskSpace(savePath);
        File file = new File(savePath);
        long usableSpace = file.getUsableSpace();
        System.out.println(usableSpace);
        if (space < size) {
            throw new Exception("磁盘空间不足");
        }
        return 1;
    }


}