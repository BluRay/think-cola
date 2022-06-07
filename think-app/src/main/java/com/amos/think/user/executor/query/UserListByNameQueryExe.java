package com.amos.think.user.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.amos.think.convertor.UserConvertor;
import com.amos.think.dto.data.UserVO;
import com.amos.think.dto.query.UserListByNameQuery;
import com.amos.think.gateway.impl.database.dataobject.UserDO;
import com.amos.think.gateway.impl.database.mapper.UserMapper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserListByNameQueryExe {

	@Resource
	private UserMapper userMapper;

	public MultiResponse<Map<String, Object>> execute() {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		resultList = userMapper.listSysUser(null);
		return MultiResponse.of(resultList);
	}

	public PageResponse<Map<String, Object>> execute(Map<String, Object> parmsMap) {
		String pageNo = (parmsMap.get("currentPage") != null && !parmsMap.get("currentPage").equals(""))
      ? parmsMap.get("currentPage").toString()
      : "1";
    String pageSize = (parmsMap.get("pageSize") != null && !parmsMap.get("pageSize").equals(""))
      ? parmsMap.get("pageSize").toString()
      : "15";
		System.out.println("-->pageNo:" + pageNo + ",pageSize:" + pageSize);
    int start = 0;
    int length = 15;
    if (parmsMap.get("pageSize") != null && !parmsMap.get("pageSize").equals("")) {
      start = (Integer.valueOf(pageNo) - 1) * Integer.valueOf(pageSize);
      length = Integer.valueOf(pageSize);
    }
    parmsMap.put("start", start);
    parmsMap.put("length", length);
		int totalCount = userMapper.listSysUserTotal(parmsMap);
		return PageResponse.of(userMapper.listSysUser(parmsMap), totalCount, length, Integer.valueOf(pageNo));
	}

	public MultiResponse<UserVO> execute(UserListByNameQuery query) {
		List<UserDO> userDOList = userMapper.listByName(query);
		List<UserVO> userVOList = userDOList.stream()
			.map(UserConvertor::toValueObject)
			.collect(Collectors.toList());

		return MultiResponse.of(userVOList);
	}

}
