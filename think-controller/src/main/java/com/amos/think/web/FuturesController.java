package com.amos.think.web;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.amos.think.api.IFuturesService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("futures")
public class FuturesController {
  @Resource
  private IFuturesService futuresService;

  @GetMapping(value = "/getAccountPageList")
  public Response getAccountPageList(@RequestParam(required = false) Map<String, Object> parmsMap) {
    Map<String, Object> data = new HashMap<>();
    data.put("code", 0);
    data.put("result", futuresService.getAccountPageList(parmsMap));
    data.put("message", "查询成功");
    data.put("type", "success");
    return SingleResponse.of(data);
  }
}
