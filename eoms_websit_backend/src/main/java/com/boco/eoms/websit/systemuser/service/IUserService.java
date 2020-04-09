package com.boco.eoms.websit.systemuser.service;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.websit.systemuser.model.EomsSystemUser;

import java.util.List;

/**
 * @author ssh
 * @description:用户操作接口
 * @date 2019/10/3115:19
 */
public interface IUserService {
    /**
     * 操作用户信息，用户登录或者注册
     * @param eomsSystemUser
     * @return
     */
    public JSONObject operateUserInfo(EomsSystemUser eomsSystemUser) throws Exception;

    /**
     * 手机号查询用户信息
     * @return
     */
    EomsSystemUser selectByMobile() throws Exception;

    /**
     * 个人信息修改
     * @param eomsSystemUser
     * @return
     * @throws Exception
     */
    public void updateUser(EomsSystemUser eomsSystemUser) throws Exception;

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     * @throws Exception
     */
    EomsSystemUser selectByMobile(String mobile) throws Exception;

    /**
     * 网站登录
     * @param userId 用户名
     * @param password 密码
     * @param code 短信验证码
     * @return
     */
    JSONObject websitLogin(String userId,String password,String code);
}
