package com.boco.eoms.websit.systemuser.mapper;

import com.boco.eoms.websit.systemuser.model.EomsSystemUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @description: 用户表mapper
* @author ssh
* @date 2019/11/1 9:38
*/
public interface EomsSystemUserMapper {

    int insert(EomsSystemUser record);

    /**
     * 根据mobile查询数据
     * @param mobile
     * @return
     */
    EomsSystemUser selectByMobile(String mobile);

    int updateByPrimaryKey(EomsSystemUser record);

    /**
     * 用户每天注册量
     * @return
     */
    List getUserRegisterNum();

    /**
     * 根据用户密码查询数据
     * @param userId
     * @param password
     * @return
     */
    EomsSystemUser selectByUserIdAndPwd(@Param("userId") String userId, @Param("password")String password);
}