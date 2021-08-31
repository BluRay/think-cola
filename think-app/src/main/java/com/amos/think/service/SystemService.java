package com.amos.think.service;

import java.util.Map;
import javax.annotation.Resource;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.amos.think.api.ISystemService;
import com.amos.think.gateway.impl.database.mapper.SystemMapper;
import org.springframework.stereotype.Service;

@Service
public class SystemService implements ISystemService {
  @Resource
	private SystemMapper systemMapper;

  @Override
  public PageResponse<Map<String, Object>> getDeptPageList(Map<String, Object> parmsMap) {
    String pageNo = (parmsMap.get("currentPage") != null && !parmsMap.get("currentPage").equals(""))
      ? parmsMap.get("currentPage").toString()
      : "1";
    String pageSize = (parmsMap.get("pageSize") != null && !parmsMap.get("pageSize").equals(""))
      ? parmsMap.get("pageSize").toString()
      : "15";
		System.out.println("-->pageNo:" + pageNo + ",pageSize:" + pageSize);
    int start = 0;
    int length = 15;
    if (parmsMap.get("pageSize") != null && !parmsMap.get("pageSize").equals("")) {
      start = (Integer.valueOf(pageNo) - 1) * Integer.valueOf(pageSize);
      length = Integer.valueOf(pageSize);
    }
    parmsMap.put("start", start);
    parmsMap.put("length", length);

    int totalCount = systemMapper.getDeptTotalCount(parmsMap);
		return PageResponse.of(systemMapper.getDeptPageDate(parmsMap), totalCount, length, Integer.valueOf(pageNo));
  }

  @Override
  public Response addDept(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response editDept(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response deleteDept(Map<String, Object> paramMap) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
