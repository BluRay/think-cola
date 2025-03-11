package com.amos.think.gateway.impl.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface FuturesMapper {
  List<Map<String, Object>> getAccountPageDate(@Param(value = "paramMap") Map<String, Object> paramMap);
  int getAccountTotalCount(@Param(value = "paramMap") Map<String, Object> paramMap);
  int uploadAccount(@Param(value = "paramMap") Map<String, Object> paramMap);
  int deleteAccount(@Param(value = "paramMap") Map<String, Object> paramMap);
  List<Map<String, Object>> getAccountDateList(@Param(value = "paramMap") Map<String, Object> paramMap);
  List<Map<String, Object>> getAccountLineData(@Param(value = "paramMap") Map<String, Object> paramMap);
}
