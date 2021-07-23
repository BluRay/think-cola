package com.amos.think.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.amos.think.api.IUserService;
import com.amos.think.dto.UserRegisterCmd;
import com.amos.think.dto.clientobject.UserRegisterCO;
import com.amos.think.dto.query.UserLoginQuery;
import org.springframework.web.bind.annotation.*;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {

  @Resource
  private IUserService userService;

  @GetMapping(value = "/hello")
  public String hello() {
    System.out.println("-->UserController hello");
    String now = DateUtil.now();
    String md5Hex1 = DigestUtil.md5Hex("123456");
    return "Hello, welcome to COLA world!!!!!【csbyd@1234】" + now + "|" + md5Hex1;
  }

  @PostMapping(value = "/register")
  public Response register(@RequestBody UserRegisterCO userRegister) {
    UserRegisterCmd userRegisterCmd = new UserRegisterCmd();
    userRegisterCmd.setUserRegister(userRegister);

    return userService.register(userRegisterCmd);
  }

  @PostMapping(value = "/login")
  public Response login(@RequestBody UserLoginQuery userLoginQuery) {
    System.out.println("-->UserController login");
    return userService.login(userLoginQuery);
  }

  @PostMapping(value = "/logout")
  public Response logout() {
    System.out.println("-->UserController logout");
    return Response.buildSuccess();
  }

  @GetMapping(value = "/info")
  public MultiResponse<Map<String, Object>> userinfo(@RequestParam(required = false) String name) {
    Map<String, Object> usermap = new HashMap<String, Object>();
    usermap.put("avatar", "https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png");
    usermap.put("name", "测试用户名");
    usermap.put("introduction", "introduction");
    usermap.put("roles", Arrays.asList("super_admin", "admin"));
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    resultList.add(usermap);
    return MultiResponse.of(resultList);
  }

  @GetMapping(value = "/list")
  public MultiResponse<Map<String, Object>> list() {
    System.out.println("-->UserController list");
    return userService.listSysUser();
  }

  @PostMapping(value = "/pageList")
  public PageResponse<Map<String, Object>> pageList(@RequestBody Map<String, Object> parmsMap) {
    System.out.println("-->UserController pageList");
    return userService.pageListSysUser(parmsMap);
  }

}
