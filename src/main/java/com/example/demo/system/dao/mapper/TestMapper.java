package com.example.demo.system.dao.mapper;

import com.example.demo.system.model.po.TestPo;
import com.example.demo.system.model.po.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description: mapper接口类
 */
@Mapper
public interface TestMapper {
    //    根据条件查询
    List<TestPo> findAll(Map<String, Object> map);

    //    新建
    Integer insertTest(@Param("list") TestPo testPo);

    //    修改
    Integer updataTest(@Param("list") TestPo testPo);

    //    根据id查询
    TestPo getById(Map<String, Object> map);

    //    根据id查询上月数据
    TestPo getByIdMonth(Map<String, Object> map);

    //    删除文件更新数据库字段
    Integer updataTestDoc(Map<String, Object> map);

    //    删除数据
    Integer deleteById(Map<String, Object> map);

    //    批量插入
    Integer insertTestAll(@Param("list") List<TestPo> testPo);

    Word getId(Map<String, Object> map);

}
