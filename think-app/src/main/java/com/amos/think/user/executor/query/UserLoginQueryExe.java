package com.amos.think.user.executor.query;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.amos.think.common.util.DesSecretUtil;
import com.amos.think.dto.data.ErrorCode;
import com.amos.think.dto.query.UserLoginQuery;
import com.amos.think.gateway.impl.database.dataobject.UserDO;
import com.amos.think.gateway.impl.database.mapper.UserMapper;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Date;
import javax.annotation.Resource;

/**
 * DESCRIPTION: UserListByNameQueryExe
 */
@Component
public class UserLoginQueryExe {

	@Resource
	private UserMapper userMapper;

	public Response execute(UserLoginQuery query) {
		UserDO byUserName = userMapper.getPasswordInfo(query.getUsername());
		if (byUserName == null) {
			return SingleResponse.buildFailure(ErrorCode.B_USER_passwordError.getErrCode(), ErrorCode.B_USER_passwordError.getErrDesc());
		}

		String encryptPwd = DesSecretUtil.encrypt(query.getPassword(), byUserName.getSalt());
		if (!byUserName.getPassword().equals(encryptPwd)) {
			return SingleResponse.buildFailure(ErrorCode.B_USER_passwordError.getErrCode(), ErrorCode.B_USER_passwordError.getErrDesc());
		}
		Map<String, Object> data = new HashMap<String, Object>();
		
		// 验证用户名密码成功后开始生成Token
		System.out.println("--> login success : " + query.getUsername());
		// 生成token start
		String token = null;
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		// 设置签发时间
		calendar.setTime(new Date());
		// 设置过期时间
		calendar.add(Calendar.MINUTE, 60*20);// 5分钟
		Date time = calendar.getTime();
		List<String> roleList = new ArrayList<String>();
		roleList.add("admin");
		token = Jwts.builder().setSubject(query.getUsername() + "-" + roleList).setIssuedAt(now)// 签发时间
			.setExpiration(time)// 过期时间
			.signWith(SignatureAlgorithm.HS512, "spring-security-@Jwt!&Secret^#") // 采用什么算法是可以自己选择的，不一定非要采用HS512
			.compact();
		// 生成token end
		
		// Authentication auth = new UsernamePasswordAuthenticationToken(query.getUsername(), query.getPassword());
		// 兼容 vbenUi 数据格式 
		// 错误的时候返回格式 {"code":-1,"result":null,"message":"Incorrect account or password！","type":"error"}
    // 成功时返回格式 {"code":0,"result":{"roles":[{"roleName":"Super Admin","value":"super"}],"userId":"1","username":"vben","token":"fakeToken1","realName":"Vben Admin","desc":"manager"},"message":"ok","type":"success"}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userId", "1");
		result.put("username", "admin");
		result.put("token", "Bearer " + token);
		result.put("realName", "管理员");
		result.put("desc", "manage");
		Map<String, Object> role = new HashMap<String, Object>();
		role.put("roleName", "Super Admin");
		role.put("value", "super");
		List<Map<String, Object>> roles = new ArrayList<Map<String, Object>>();
		roles.add(role);

		data.put("code", 0);
		data.put("result", result);
		data.put("roles", roles);
		data.put("message", "登录成功");
		data.put("type", "success");

		return SingleResponse.of(data);
		// return Response.buildSuccess();
	}

}
