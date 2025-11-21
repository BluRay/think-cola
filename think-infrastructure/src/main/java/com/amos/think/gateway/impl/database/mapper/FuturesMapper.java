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
  // 交易品种维护
  List<Map<String, Object>> getRqTradeBooksData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int insertTradeBooksData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int updateTradeBooksData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int deleteTradeBooksData(@Param(value = "paramMap") Map<String, Object> paramMap);
  // 交易帐号维护
  List<Map<String, Object>> getRqTradeAccountData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int insertTradeAccountData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int updateTradeAccountData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int deleteTradeAccountData(@Param(value = "paramMap") Map<String, Object> paramMap);
  // 交易策略维护
  List<Map<String, Object>> getRqTacticsData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int insertTacticsData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int updateTacticsData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int deleteTacticsData(@Param(value = "paramMap") Map<String, Object> paramMap);
  // 跟单帐号配置
  List<Map<String, Object>> getFollowUserData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int insertFollowUserData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int updateFollowUserData(@Param(value = "paramMap") Map<String, Object> paramMap);
  int deleteFollowUserData(@Param(value = "paramMap") Map<String, Object> paramMap);
}
