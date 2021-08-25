package com.amos.think.user.executor.query;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.amos.think.common.util.DesSecretUtil;
import com.amos.think.dto.data.ErrorCode;
import com.amos.think.dto.query.UserLoginQuery;
import com.amos.think.gateway.impl.database.dataobject.UserDO;
import com.amos.think.gateway.impl.database.mapper.UserMapper;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
		
		// TODO 生成Token
		// Authentication auth = new UsernamePasswordAuthenticationToken(query.getUserName(), query.getPassword());
		// 兼容 vbenUi 数据格式 
		// 错误的时候返回格式 {"code":-1,"result":null,"message":"Incorrect account or password！","type":"error"}
    // 成功时返回格式 {"code":0,"result":{"roles":[{"roleName":"Super Admin","value":"super"}],"userId":"1","username":"vben","token":"fakeToken1","realName":"Vben Admin","desc":"manager"},"message":"ok","type":"success"}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userId", "1");
		result.put("username", "admin");
		result.put("token", "fakeToken1");
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
