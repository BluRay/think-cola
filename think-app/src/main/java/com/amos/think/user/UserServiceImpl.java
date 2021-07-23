package com.amos.think.user;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.amos.think.api.IUserService;
import com.amos.think.dto.UserModifyCmd;
import com.amos.think.dto.UserRegisterCmd;
import com.amos.think.dto.data.UserVO;
import com.amos.think.dto.query.UserLoginQuery;
import com.amos.think.user.executor.UserModifyCmdExe;
import com.amos.think.user.executor.UserRegisterCmdExe;
import com.amos.think.user.executor.query.UserInfoQueryExe;
import com.amos.think.user.executor.query.UserListByNameQueryExe;
import com.amos.think.user.executor.query.UserLoginQueryExe;
import org.springframework.stereotype.Service;
import java.util.Map;
import javax.annotation.Resource;

@Service
@CatchAndLog
public class UserServiceImpl implements IUserService {

	/**
	 * xxxExe 避免 Service 膨胀利器
	 */
	@Resource
	private UserRegisterCmdExe userRegisterCmdExe;
	@Resource
	private UserModifyCmdExe userModifyCmdExe;
	@Resource
	private UserLoginQueryExe userLoginQueryExe;
	@Resource
	private UserInfoQueryExe userInfoQueryExe;
	@Resource
	private UserListByNameQueryExe userListByNameQueryExe;

	@Override
	public Response register(UserRegisterCmd cmd) {

		return userRegisterCmdExe.execute(cmd);
	}

	@Override
	public Response modify(UserModifyCmd cmd) {

		return userModifyCmdExe.execute(cmd);
	}

	@Override
	public Response login(UserLoginQuery query) {

		return userLoginQueryExe.execute(query);
	}

	@Override
	public SingleResponse<UserVO> getUserInfo(String username) {

		return userInfoQueryExe.execute(username);
	}

	@Override
	public MultiResponse<Map<String, Object>> listSysUser() {
		return userListByNameQueryExe.execute();
	}

	@Override
	public PageResponse<Map<String, Object>> pageListSysUser(Map<String, Object> parmsMap){
		return userListByNameQueryExe.execute(parmsMap);
	}

}
