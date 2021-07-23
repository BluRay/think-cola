package com.amos.think.test;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.amos.think.api.IUserService;
import com.amos.think.dto.UserModifyCmd;
import com.amos.think.dto.UserRegisterCmd;
import com.amos.think.dto.clientobject.UserModifyCO;
import com.amos.think.dto.clientobject.UserRegisterCO;
import com.amos.think.dto.data.ErrorCode;
import com.amos.think.dto.data.UserVO;
import com.amos.think.dto.query.UserLoginQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DESCRIPTION: UserServiceTest
 * 
 CREATE TABLE `SYS_USER` (
  `ID` int(6) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户'
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    private static final String username = UUID.randomUUID().toString();
    private static final String password = "666666";

    @Before
    public void setUp() {
        System.out.println("test username is [" + username + "]");
    }

    @Test
    public void user_1_Register() {
        //1.prepare
        UserRegisterCmd registerCmd = new UserRegisterCmd();

        UserRegisterCO registerCO = new UserRegisterCO();
        registerCO.setName("amos.wang");
        registerCO.setUsername(username);
        registerCO.setPassword(password);
        registerCO.setPhoneNo("189****8861");
        registerCO.setGender(1);
        registerCO.setBirthday(LocalDate.of(1996, 6, 26));
        registerCO.setDescription("https://amos.wang/");

        registerCmd.setUserRegister(registerCO);

        //2.execute
        Response response = userService.register(registerCmd);

        //3.assert
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void user_2_RegisterByRepeatUsername() {
        //1.prepare
        UserRegisterCmd registerCmd = new UserRegisterCmd();

        UserRegisterCO registerCO = new UserRegisterCO();
        registerCO.setUsername(username);
        registerCO.setPassword(password);

        registerCmd.setUserRegister(registerCO);

        //2.execute
        Response response = userService.register(registerCmd);

        //3.assert error
        Assert.assertEquals(ErrorCode.B_USER_usernameRepeat.getErrCode(), response.getErrCode());
    }

    @Test
    public void user_3_Login() {
        //1.prepare
        UserLoginQuery userLoginQuery = new UserLoginQuery();
        userLoginQuery.setUserName(username);
        userLoginQuery.setPassword(password);

        //2.execute
        Response response = userService.login(userLoginQuery);

        //3.assert success
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void user_4_Modify() {
        //1.prepare
        SingleResponse<UserVO> userInfoResponse = userService.getUserInfo(username);
        Assert.assertTrue(userInfoResponse.isSuccess() && userInfoResponse.getData() != null);

        String userId = userInfoResponse.getData().getId();

        UserModifyCmd userModify = new UserModifyCmd();

        UserModifyCO userModifyCO = new UserModifyCO();
        userModifyCO.setId(userId);
        userModifyCO.setName("小道远");
        userModifyCO.setUsername(username);
        userModifyCO.setPhoneNo("189----8861");
        userModifyCO.setGender(0);
        userModifyCO.setBirthday(LocalDate.of(1996, 5, 11));
        userModifyCO.setDescription("https://github.com/AmosWang0626");

        userModify.setUserModify(userModifyCO);

        //2.execute
        Response response = userService.modify(userModify);

        //3.assert
        Assert.assertTrue(response.isSuccess());
    }

}
