package com.amos.think.gateway.impl.database.mapper;

import com.amos.think.dto.query.UserListByNameQuery;
import com.amos.think.gateway.impl.database.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    UserDO getPasswordInfo(String username);

    UserDO getUserInfo(String username);

    List<Map<String, Object>> listSysUser(Map<String, Object> parmsMap);
    int listSysUserTotal(Map<String, Object> parmsMap);

    List<UserDO> listByName(UserListByNameQuery query);

    Boolean existUsername(@Param("userId") String userId, @Param("username") String username);

}
