package com.example.demo.system.dao.mapper;

import com.example.demo.system.model.bo.FindAllTest;
import com.example.demo.system.model.po.TestPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author yangfl
 * @date 2019年09月24日 15:42
 * Version 1.0
 */
@Mapper
public interface TestMapper {
//    根据条件查询
    List<TestPo> findAll(@Param("model")FindAllTest findAllTest);
//    新建
    Integer insertTest(@Param("list") TestPo testPo);
//    修改
    Integer updataTest(@Param("list") TestPo testPo);
//    根据id查询
    TestPo getById(Map<String,Object> map);
//    删除文件更新数据库字段
    Integer updataTestDoc(Map<String,Object> map);
//    删除数据
    Integer deleteById(Map<String, Object> map);


}
