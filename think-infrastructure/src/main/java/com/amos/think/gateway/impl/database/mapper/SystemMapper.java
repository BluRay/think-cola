package com.amos.think.gateway.impl.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface SystemMapper {

  List<Map<String, Object>> getDeptPageDate(@Param(value = "paramMap") Map<String, Object> paramMap);

  int getDeptTotalCount(@Param(value = "paramMap") Map<String, Object> paramMap);
  
}
