package com.amos.think.web;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.amos.think.api.IFuturesService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("futures")
public class FuturesController {
  @Resource
  private IFuturesService futuresService;

  @PostMapping(value = "/getAccountPageList")
  public Response getAccountPageList(@RequestBody(required = false) Map<String, Object> parmsMap) {
    Map<String, Object> data = new HashMap<>();
    data.put("code", 0);
    data.put("result", futuresService.getAccountPageList(parmsMap));
    data.put("message", "查询成功");
    data.put("type", "success");
    return SingleResponse.of(data);
  }
  @PostMapping(value = "/uploadAccount")
  public Response uploadAccount(@RequestBody Map<String, Object> parmsMap) {
    System.out.println("--->futures::uploadAccount");
    Map<String, Object> data = new HashMap<>();
    data.put("code", futuresService.uploadAccount(parmsMap));
    data.put("message", "导入成功");
    data.put("type", "success");
    return SingleResponse.of(data);
  }
  @PostMapping(value = "/getAccountLineChart")
  public Response getAccountLineChart(@RequestBody(required = false) Map<String, Object> parmsMap) {
    Map<String, Object> data = new HashMap<>();
    data.put("code", 0);
    data.put("result", futuresService.getAccountLineChart(parmsMap));
    data.put("message", "查询成功");
    data.put("type", "success");
    return SingleResponse.of(data);
  }
}
