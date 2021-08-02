package com.amos.think.user.executor;

import com.alibaba.cola.dto.Response;
import com.amos.think.convertor.UserConvertor;
import com.amos.think.domain.user.gateway.UserGateway;
import com.amos.think.dto.UserRegisterCmd;
import com.amos.think.dto.clientobject.UserRegisterCO;
import com.amos.think.dto.data.ErrorCode;
import com.amos.think.gateway.impl.database.mapper.UserMapper;
import com.amos.think.user.validator.UserValidator;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * DESCRIPTION: UserAddCmdExe
 * @date 2021/1/10
 */
@Component
public class UserRegisterCmdExe {

	@Resource
	private UserMapper userMapper;
	@Resource
	private UserGateway userGateway;
	
	public Response execute(UserRegisterCmd cmd) {
		UserRegisterCO userRegister = cmd.getUserRegister();
		System.out.println("-->UserRegisterCmdExe userRegister:" + userRegister.getUsername() + "|" + userRegister.getPhoneNo());

		UserValidator.checkUserRegister(userRegister);

		// check 用户名是否重复
		if (userMapper.existUsername(userRegister.getId(), userRegister.getUsername())) {
			return Response.buildFailure(ErrorCode.B_USER_usernameRepeat.getErrCode(),
				ErrorCode.B_USER_usernameRepeat.getErrDesc());
		}
		System.out.println("--> UserConvertor " +UserConvertor.toEntity(userRegister).getPhoneNo() + "|" + UserConvertor.toEntity(userRegister).getMemo());
		userGateway.save(UserConvertor.toEntity(userRegister));

		return Response.buildSuccess();
	}
	
}
