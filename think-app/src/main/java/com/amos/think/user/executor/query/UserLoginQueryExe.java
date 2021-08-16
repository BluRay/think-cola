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
		UserDO byUserName = userMapper.getPasswordInfo(query.getUserName());
		if (byUserName == null) {
			return SingleResponse.buildFailure(ErrorCode.B_USER_passwordError.getErrCode(), ErrorCode.B_USER_passwordError.getErrDesc());
		}

		String encryptPwd = DesSecretUtil.encrypt(query.getPassword(), byUserName.getSalt());
		if (!byUserName.getPassword().equals(encryptPwd)) {
			return SingleResponse.buildFailure(ErrorCode.B_USER_passwordError.getErrCode(), ErrorCode.B_USER_passwordError.getErrDesc());
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("CODE", 20000);
		data.put("MSG", "success");
		data.put("TOKEN", "token");

		return SingleResponse.of(data);
		// return Response.buildSuccess();
	}

}
