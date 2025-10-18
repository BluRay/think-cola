package com.amos.think.futures;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Resource;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.SingleResponse;
import org.springframework.stereotype.Service;
import com.alibaba.cola.dto.PageResponse;
import com.amos.think.api.IFuturesService;
import com.amos.think.gateway.impl.database.mapper.FuturesMapper;
import cn.hutool.core.date.DateUtil;

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
    @Override
    public int uploadAccount(Map<String, Object> parmsMap) {
      String account_date = parmsMap.get("account_date").toString();
      String user_name = parmsMap.get("user_name").toString();
      List<List<String>> list = (List<List<String>>) parmsMap.get("upload_data");

      int check = futuresMapper.getAccountTotalCount(parmsMap);
      if (check > 0) {
        // return -1;
        futuresMapper.deleteAccount(parmsMap);
      }
      
      for(int i=1; i < list.size() - 2; i++) {
        Map<String, Object> account = new HashMap<>();
        account.put("d1", list.get(i).get(1));
        account.put("d2", list.get(i).get(2));
        account.put("d3", list.get(i).get(3));
        account.put("d4", list.get(i).get(4));
        account.put("d5", list.get(i).get(5));
        account.put("d6", list.get(i).get(6));
        account.put("d7", list.get(i).get(7));
        account.put("d8", list.get(i).get(8));
        account.put("d9", list.get(i).get(9));
        account.put("d10", list.get(i).get(10));

        account.put("d11", list.get(i).get(11));
        account.put("d12", list.get(i).get(12));
        account.put("d13", list.get(i).get(13));
        account.put("d14", list.get(i).get(14));
        account.put("d15", list.get(i).get(15));
        account.put("d16", list.get(i).get(16));
        account.put("d17", list.get(i).get(17));
        account.put("d18", list.get(i).get(18));
        account.put("d19", list.get(i).get(19));
        account.put("d20", list.get(i).get(20));

        account.put("d21", list.get(i).get(21));
        account.put("d22", list.get(i).get(22));
        account.put("d23", list.get(i).get(23));
        account.put("d24", list.get(i).get(24));
        account.put("d25", list.get(i).get(25));

        account.put("d26", account_date);
        account.put("d27", user_name);
        account.put("d28", DateUtil.now());

        futuresMapper.uploadAccount(account);
      }

      return 0;
    }
    @Override
    public Response getAccountLineChart(Map<String, Object> parmsMap) {
    	Map<String, Object> data = new HashMap<String, Object>();
    	parmsMap.put("series", "风险度");
    	List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> lineDataList = new ArrayList<Map<String, Object>>();
			dateList = futuresMapper.getAccountDateList(parmsMap);
			parmsMap.put("dateList", dateList);
			lineDataList = futuresMapper.getAccountLineData(parmsMap);
			data.put("dateList", dateList);
			data.put("lineData", lineDataList);
			return SingleResponse.of(data);
    }
    
    @Override
    public Response getRqTradeBooksData(Map<String, Object> parmsMap) {
    	Map<String, Object> data = new HashMap<String, Object>();
    	List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();
    	dateList = futuresMapper.getRqTradeBooksData(parmsMap);
    	data.put("dateList", dateList);
    	return SingleResponse.of(data);
    }
    
    @Override
    public Response getRqTradeAccountData(Map<String, Object> parmsMap) {
    	Map<String, Object> data = new HashMap<String, Object>();
    	List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();
    	dateList = futuresMapper.getRqTradeAccountData(parmsMap);
    	data.put("dateList", dateList);
    	return SingleResponse.of(data);
    }
}
