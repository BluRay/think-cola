package com.amos.think.gateway.impl.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface FuturesMapper {
  List<Map<String, Object>> getAccountPageDate(@Param(value = "paramMap") Map<String, Object> paramMap);
  int getAccountTotalCount(@Param(value = "paramMap") Map<String, Object> paramMap);
}
