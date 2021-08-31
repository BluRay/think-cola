package com.amos.think.api;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import java.util.Map;

public interface ISystemService {
  PageResponse<Map<String, Object>> getDeptPageList(Map<String, Object> parmsMap);

  Response addDept(Map<String, Object> paramMap);

  Response editDept(Map<String, Object> paramMap);

  Response deleteDept(Map<String, Object> paramMap);
}
