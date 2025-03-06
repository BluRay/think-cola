package com.amos.think.futures;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.alibaba.cola.dto.PageResponse;
import com.amos.think.api.IFuturesService;
import com.amos.think.gateway.impl.database.mapper.FuturesMapper;

@Service
public class FuturesService implements IFuturesService {
    @Resource
	private FuturesMapper futuresMapper;
    @Override
    public PageResponse<Map<String, Object>> getAccountPageList(Map<String, Object> parmsMap) {
        String pageNo = (parmsMap.get("currentPage") != null && !parmsMap.get("currentPage").equals(""))
          ? parmsMap.get("currentPage").toString()
          : "1";
        String pageSize = (parmsMap.get("pageSize") != null && !parmsMap.get("pageSize").equals(""))
          ? parmsMap.get("pageSize").toString()
          : "15";
        int start = 0;
        int length = 15;
        if (parmsMap.get("pageSize") != null && !parmsMap.get("pageSize").equals("")) {
          start = (Integer.valueOf(pageNo) - 1) * Integer.valueOf(pageSize);
          length = Integer.valueOf(pageSize);
        }
        parmsMap.put("start", start);
        parmsMap.put("length", length);
        int totalCount = futuresMapper.getAccountTotalCount(parmsMap);
        return PageResponse.of(futuresMapper.getAccountPageDate(parmsMap), totalCount, length, Integer.valueOf(pageNo));
    }
    
}
