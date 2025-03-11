package com.amos.think.api;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.PageResponse;
import java.util.Map;

public interface IFuturesService {
    PageResponse<Map<String, Object>> getAccountPageList(Map<String, Object> parmsMap);

    int uploadAccount(Map<String, Object> parmsMap);
    
    Response getAccountLineChart(Map<String, Object> parmsMap);
}
