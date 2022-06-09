package com.amos.think.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.amos.think.api.IUserService;
import com.amos.think.dto.UserRegisterCmd;
import com.amos.think.dto.clientobject.UserRegisterCO;
import com.amos.think.dto.query.UserLoginQuery;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {
  /**
  CREATE TABLE `SYS_USER` (
  `ID` int(6) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(100) DEFAULT NULL,
  `SALT` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `PHONENO` varchar(20) DEFAULT NULL,
  `GENDER` varchar(2) DEFAULT NULL COMMENT '性别',
  `BIRTHDAY` varchar(20) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `CREATOR` varchar(20) DEFAULT NULL,
  `CREATETIME` varchar(40) DEFAULT NULL,
  `MODIFIER` varchar(20) DEFAULT NULL,
  `MODIFYTIME` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统用户'
   */

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
    // 【/user/login】 自定义登录
    // 【/login】 SpringBoot security 自带的登录
    return userService.login(userLoginQuery);
  }
  @GetMapping(value = "/getUserInfo")
  public Response getUserInfo(@RequestParam(required = true) String username) {
    System.out.println("-->UserController getUserInfo");
    // VBen 通过token 获取用户信息 数据格式：
    /**{
      userId: '1',
      username: 'vben',
      realName: 'Vben Admin',
      avatar: 'https://q1.qlogo.cn/g?b=qq&nk=190848757&s=640',
      desc: 'manager',
      password: 'vben',
      token: 'fakeToken1',
      homePath: '/dashboard/analysis',
      roles: [
        {
          roleName: 'Super Admin',
          value: 'super',
        },
      ],
    }**/
    Map<String, Object> data = new HashMap<String, Object>();
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("userId", "1");
    result.put("username", "admin");
    result.put("realName", "管理员");
    result.put("avatar", "https://q1.qlogo.cn/g?b=qq&nk=190848757&s=640");
    result.put("desc", "系统管理员");
    result.put("homePath", "/dashboard/analysis");
		Map<String, Object> role = new HashMap<String, Object>();
		role.put("roleName", "Super Admin");
		role.put("value", "super");
		List<Map<String, Object>> roles = new ArrayList<Map<String, Object>>();
		roles.add(role);
		result.put("roles", roles);
    data.put("code", 0);
		data.put("result", result);
		data.put("message", "查询成功");
		data.put("type", "success");
    return SingleResponse.of(data);
  }

  @PostMapping(value = "/logout")
  public Response logout() {
    System.out.println("-->UserController logout");
    return Response.buildSuccess();
  }

  @GetMapping(value = "/info")
  @SuppressWarnings("unchecked")
  public MultiResponse<Map<String, Object>> userinfo(@RequestParam(required = false) String token) {
    Map<String, Object> usermap = new HashMap<String, Object>();
    System.out.println("-->UserController info token : " + token);
    // 解析token
    if (token == null || token.isEmpty()) {
      return MultiResponse.buildFailure("-10", "token error");
    }
    Claims claims = Jwts.parser().setSigningKey("spring-security-@Jwt!&Secret^#").parseClaimsJws(token.replace("Bearer ", "")).getBody();
    String token_user = claims.getSubject();
    System.out.println("-->UserController info token_user : " + token_user);
    // SingleResponse<UserVO> user = userService.getUserInfo("");
    usermap.put("avatar", "https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png");
    usermap.put("name", token_user.substring(0, token_user.indexOf("-")));
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
