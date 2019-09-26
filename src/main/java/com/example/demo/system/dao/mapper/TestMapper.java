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

    List<TestPo> findAll(@Param("model")FindAllTest findAllTest);

    Integer insertTest(@Param("list") TestPo testPo);

    Integer updataTest(@Param("list") TestPo testPo);

    TestPo getById(Map<String,Object> map);

    void updataTestDoc(Map<String,Object> map);

    Integer deleteById(Map<String, Object> map);

}
