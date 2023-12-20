package com.example.demo.application.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.demo.application.user.UserModifyCmdExe;
import com.example.demo.application.user.UserRegisterCmdExe;
import com.example.demo.application.user.query.UserInfoQueryExe;
import com.example.demo.application.user.query.UserListByParamQueryExe;
import com.example.demo.application.user.query.UserLoginQueryExe;
import com.example.demo.client.api.IUserService;
import com.example.demo.client.dto.UserModifyCmd;
import com.example.demo.client.dto.UserRegisterCmd;
import com.example.demo.client.dto.data.UserVO;
import com.example.demo.client.dto.query.UserListByParamQuery;
import com.example.demo.client.dto.query.UserLoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRegisterCmdExe userRegisterCmdExe;
    @Autowired
    private UserModifyCmdExe userModifyCmdExe;
    @Autowired
    private UserLoginQueryExe userLoginQueryExe;
    @Autowired
    private UserInfoQueryExe userInfoQueryExe;
    @Autowired
    private UserListByParamQueryExe userListByParamQueryExe;

    @Override
    public UserVO register(UserRegisterCmd cmd) {
        return userRegisterCmdExe.execute(cmd);
    }

    @Override
    public UserVO modify(UserModifyCmd cmd) {
        return userModifyCmdExe.execute(cmd);
    }

    @Override
    public void login(UserLoginQuery query) {
        userLoginQueryExe.execute(query);
    }

    @Override
    public SingleResponse<UserVO> getUserInfo(String id) {
        return userInfoQueryExe.execute(id);
    }

    @Override
    public MultiResponse<UserVO> listByName(UserListByParamQuery query) {
        return userListByParamQueryExe.execute(query);
    }

}