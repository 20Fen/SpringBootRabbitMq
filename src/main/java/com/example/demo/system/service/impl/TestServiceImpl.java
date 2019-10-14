package com.example.demo.system.service.impl;

import com.example.demo.system.dao.mapper.TestMapper;
import com.example.demo.system.model.po.TestPo;
import com.example.demo.system.model.po.Word;
import com.example.demo.system.service.TestService;
import com.exception.CustomException;
import com.example.demo.system.util.DateUtil;
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
 */
@Log4j2
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    @Value("${paths}")
    private String path;

    /**
     * Description: 根据条件进行查询
     */
    @Override
    public PageInfo<TestPo> findAll(Integer page, Integer pageSize,Map<String,Object> map) throws Exception {
//        前台必须传分页值
//        调用分页插件,执行的语句必须在插件的下面
        int limit = page != null ? page : 1;
        int offset = pageSize != null ? pageSize : 10;
        PageHelper.startPage(limit, offset);

        List<TestPo> all = testMapper.findAll(map);
        if (StringUtils.isEmpty(all)) {
            throw new CustomException("查询失败");
        }
        return new PageInfo<>(all);
    }

    /**
     * Description: 新建和修改数据
     */
    @Override
    @Transactional
    public String insert(TestPo test) throws Exception {

        Map<String, Object> map = new HashMap<>();
//        UUID
        String planNoId = UUID.randomUUID().toString();

        String planNo = test.getPlanNo();
        planNoId = StringUtils.isEmpty(planNo) ? planNoId : planNo;

        TestPo testPo = new TestPo();
        testPo.setPlanNo(planNoId);
        testPo.setStatTime(test.getStatTime());
        testPo.setEndTime(test.getEndTime());
        testPo.setCreateTime(test.getCreateTime());
//        判断PlanNo是否为空
        if (StringUtils.isEmpty(planNo)) {
//        为空就是新建
            testPo.setId(UUID.randomUUID().toString());
//        调用执行新建语句
            testMapper.insertTest(testPo);
        } else {
//            不为空就是修改
            testPo.setPlanNo(planNo);
            map.put("planNo", planNo);
//            调用执行查询语句
            TestPo testPo1 = testMapper.getById(map);
            if (null == testPo1) {
                throw new CustomException("数据不存在");
            }
            testPo.setId(testPo1.getId());
//            调用执行修改语句
            testMapper.updataTest(testPo);
        }
        return planNoId;
    }

    /**
     * Description: 根据ID查询数据
     */
    @Override
    @Transactional
    public TestPo getById(String planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new CustomException("planNo编号不能为空");
        }
        map.put("planNo", planNo);
//           调用执行查询语句
        TestPo test = testMapper.getById(map);
        if (null == test) {
            throw new CustomException("查询数据不存在");
        }
        return test;
    }

    /**
     * Description: 根据ID查询上个月数据
     */
    @Override
    @Transactional
    public TestPo getByIdMonth(String planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new CustomException("planNo编号不能为空");
        }
        String lastMonth = DateUtil.getPreviousMonthDayBegin();
        String endMonth = DateUtil.getPreviousMonthDayEnd();
        map.put("planNo", planNo);
        map.put("statTime", lastMonth);
        map.put("endTime", endMonth);
//           调用执行查询语句
        TestPo test = testMapper.getByIdMonth(map);
        if (null == test) {
            throw new CustomException("查询数据不存在");
        }
        return test;
    }

    /**
     * Description: 删除数据并且删除文件
     */
    @Override
    @Transactional
    public String deleteById(String planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new CustomException("planNo编号不能为空");
        }

        map.put("planNo", planNo);
//        调用执行查询语句
        TestPo test = testMapper.getById(map);
        if (null == test) {
            throw new CustomException("数据不存在");
        }
        if (null != test.getUrl() && !test.getUrl().equals("")) {
//        得到数据库中的文件路径
            Path path1 = Paths.get(test.getUrl());
//        删除文件
            boolean exists = Files.deleteIfExists(path1);
            if (!exists) {
                throw new CustomException("删除文件失败");
            }
//        按照 / 进行截取
            String path = test.getUrl().substring(0, test.getUrl().lastIndexOf(File.separator));
//        删除文件夹
            TestUtil.delFolder(path);
        }
//        调用执行删除语句
        Integer integer = testMapper.deleteById(map);
        if (1 == integer) {
            return test.getPlanNo();
        }
        return null;
    }

    /**
     * Description: 批量删除数据并且删除文件
     */
    @Override
    @Transactional
    public String delete(String[] planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        for (String plan : planNo) {
            if (StringUtils.isEmpty(plan)) {
                throw new CustomException("planNo编号不能为空");
            }
            map.put("planNo", plan);
//        调用执行查询语句
            TestPo test = testMapper.getById(map);
            if (null == test) {
                throw new CustomException("数据不存在");
            }
            if (!test.getDoc().equals("") && null != test.getDoc() || null != test.getUrl() && !test.getUrl().equals("")) {
//        得到数据库中的文件路径
                Path path1 = Paths.get(test.getUrl());
//        删除文件
                boolean exists = Files.deleteIfExists(path1);
                if (!exists) {
                    throw new CustomException("删除文件失败");
                }
//        按照 / 进行截取
                String path = test.getUrl().substring(0, test.getUrl().lastIndexOf(File.separator));
//        删除文件夹
                TestUtil.delFolder(path);
            }
//        调用执行删除语句
            Integer integer = testMapper.deleteById(map);
            if (1 == integer) {
                return test.getPlanNo();
            }
        }
        return null;
    }

    /**
     * Description: 删除文件并更新数据库字段
     */
    @Override
    public String deleteUrl(String planNo) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(planNo)) {
            throw new Exception("planNo为空");
        }
        map.put("planNo", planNo);
//        调用执行查询语句
        TestPo test = testMapper.getById(map);
        if (null == test) {
            throw new CustomException("数据不存在");
        }
//        得到数据库中的文件路径
        Path path = Paths.get(test.getUrl());
//        删除文件
        boolean exists = Files.deleteIfExists(path);
        if (!exists) {
            throw new CustomException("删除文件失败");
        }
        map.put("filename", "");
        map.put("url", "");
//        调用修改数据库字段语句
        testMapper.updataTestDoc(map);
        return test.getPlanNo();
    }

    /**
     * Description: 上传文件
     */
    @Override
    public String upload(String planNo, MultipartFile file) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (file.getSize() == 0) {
            throw new CustomException("文件内容不不能为空");
        }
        if (StringUtils.isEmpty(planNo) || null == file) {
            throw new CustomException("请输入必传值");
        }
        map.put("planNo", planNo);
//        拼接上传路径
        String savePath = path + File.separator + planNo;
//        检查路径是否存在
        TestUtil.checkFilePath(savePath);
//        得到上传的文件名
        String filename = file.getOriginalFilename();
//        调用执行查询语句
        TestPo byId = testMapper.getById(map);
//        判断路径是否为空
        if (byId == null) {
            throw new CustomException("文件已存在");
        }
        try {
//            进行上传
            Integer integer = saveFile(savePath, file, filename);
            if (integer != 1) {
                throw new CustomException("上传文件失败");
            }
        } catch (IOException e) {
            log.error(e);
        }
        map.put("planNo", planNo);
        map.put("filename", filename);
        map.put("url", savePath + File.separator + filename);
//        更新到数据库中
        Integer doc = testMapper.updataTestDoc(map);
        if (doc == 1) {
            return filename;
        }
        return "2";
    }


    /**
     * @description 文件上传
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
     * @description 检测文件大小
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
            throw new CustomException("磁盘空间不足");
        }
        return 1;
    }

    @Override
    public TestPo getById(Map<String, Object> map) {
        return testMapper.getById(map);
    }

    @Override
    public Word getId(String id) throws CustomException {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(id)) {
            throw new CustomException("id编号不能为空");
        }
        map.put("id", id);
//           调用执行查询语句
        Word test = testMapper.getId(map);
        if (null == test) {
            throw new CustomException("查询数据不存在");
        }
        return test;
    }
}