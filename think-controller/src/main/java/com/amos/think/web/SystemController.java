package com.amos.think.web;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system")
public class SystemController {

  @GetMapping(value = "/getDeptList")
  public Response getDeptList(@RequestParam(required = false) String username) {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("code", 0);
		data.put("result", null);
		data.put("message", "查询成功");
		data.put("type", "success");
    return SingleResponse.of(data);
  }
}
