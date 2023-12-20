package com.example.demo.client.api;


import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.example.demo.client.dto.UserModifyCmd;
import com.example.demo.client.dto.UserRegisterCmd;
import com.example.demo.client.dto.data.UserVO;
import com.example.demo.client.dto.query.UserListByParamQuery;
import com.example.demo.client.dto.query.UserLoginQuery;

public interface IUserService {
    /**
     * 注册用户
     *
     * @param cmd 用户注册请求
     * @return Response
     */
    UserVO register(UserRegisterCmd cmd);

    /**
     * 用户信息修改
     *
     * @param cmd 用户信息修改请求
     * @return Response
     */
    UserVO modify(UserModifyCmd cmd);

    /**
     * 用户登录
     *
     * @param query 用户登录请求
     */
    void login(UserLoginQuery query);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    SingleResponse<UserVO> getUserInfo(String id);

    /**
     * 根据用户名称查询
     *
     * @param query 用户查询请求
     * @return MultiResponse
     */
    MultiResponse<UserVO> listByName(UserListByParamQuery query);
}
