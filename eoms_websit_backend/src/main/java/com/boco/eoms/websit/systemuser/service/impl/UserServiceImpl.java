package com.boco.eoms.websit.systemuser.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.HttpClientServlet;
import com.boco.eoms.base.util.ReturnJsonUtil;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.websit.oauth2.config.Oauth2ServerProps;
import com.boco.eoms.websit.sms.service.IEomsWebsitSmsService;
import com.boco.eoms.websit.systemuser.mapper.EomsSystemUserMapper;
import com.boco.eoms.websit.systemuser.model.EomsSystemUser;
import com.boco.eoms.websit.systemuser.service.IUserService;

/**
 * @author ssh
 * @description:用户业务层
 * @date 2019/10/3115:22
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private Oauth2ServerProps oauth2ServerProps;

    @Autowired
    private EomsSystemUserMapper eomsSystemUserMapper;
    
    @Autowired
    private IEomsWebsitSmsService eomsWebsitSmsService;

    /**
     * 该处不需要事务管理，注册需要入库，前端进行md5加密
     * @param eomsSystemUser
     * @return
     * @throws Exception
     */
    public JSONObject operateUserInfo(EomsSystemUser eomsSystemUser) throws Exception{
        //手机号
        String mobile = StaticMethod.nullObject2String(eomsSystemUser.getMobile());
        //是否注册的标识，true表示已经注册
        boolean isRegister = false;
        if(!"".equals(mobile)){
            EomsSystemUser user = eomsSystemUserMapper.selectByMobile(mobile);
            if(user!=null){
                isRegister = true;
            }
        }
        //注册与否的处理
        if(!isRegister){
            //如果没有注册则校验短信验证码
            String code = StaticMethod.nullObject2String(eomsSystemUser.getCode());
            //校验验证码
            JSONObject checkJson = eomsWebsitSmsService.checkcode(code, mobile);
            boolean flag = (boolean) checkJson.get("flag");
            if(!flag) {
                //返回内容
                JSONObject returnObj = new JSONObject();
                returnObj.put("result", "fail");
                returnObj.put("message", checkJson.get("msg"));
                return returnObj;
            }

            //系统生成默认数据并注册
            eomsSystemUser.setUserId(mobile);
            eomsSystemUser.setUserName(mobile);
            eomsSystemUser.setCreatedTime(new Date());
            eomsSystemUser.setUpdateTime(new Date());
            eomsSystemUser.setId(UUIDHexGenerator.getInstance().getID());
            eomsSystemUser.setStatus("normal");
            //保存数据
            eomsSystemUserMapper.insert(eomsSystemUser);

            //通过oauth2获取token
            return getTokenByUserIdAndPwd(mobile,eomsSystemUser.getPassword());
        }else{
            return ReturnJsonUtil.returnFailInfo("已经注册");
        }
    }

    @Override
    public EomsSystemUser selectByMobile() throws Exception{
    	String userId = StaticMethod.nullObject2String(SecurityContextHolder.getContext().getAuthentication().getName());
        return eomsSystemUserMapper.selectByMobile(userId);
    }

    @Override
    public void updateUser(EomsSystemUser eomsSystemUser) throws Exception {
        if(eomsSystemUser!=null){
            eomsSystemUserMapper.updateByPrimaryKey(eomsSystemUser);
        }
    }

    @Override
    public EomsSystemUser selectByMobile(String mobile) throws Exception {
        return eomsSystemUserMapper.selectByMobile(mobile);
    }

    @Override
    public JSONObject websitLogin(String userId, String password, String code) {
        //根据手机号查询数据
        EomsSystemUser user = eomsSystemUserMapper.selectByMobile(userId);
        if(user==null){
            return ReturnJsonUtil.returnFailInfo("用户不存在");
        }else{
            if(null!=code&&!"".equals(code)&&(null==password||"".equals(password))){
                JSONObject checkJson = eomsWebsitSmsService.checkcode(code, userId);
                //重置密码
                password = user.getPassword();
                boolean flag = (boolean) checkJson.get("flag");
                if(!flag) {
                    return ReturnJsonUtil.returnFailInfo("验证码错误");
                }
            }else if(null!=password&&!"".equals(password)&&((null==code||"".equals(code)))){
                EomsSystemUser passwordWayUser =eomsSystemUserMapper.selectByUserIdAndPwd(userId,password);
                if(passwordWayUser==null){
                    return ReturnJsonUtil.returnFailInfo("密码错误，请重新输入");
                }
            }else{
                return ReturnJsonUtil.returnFailInfo("数据异常");
            }
        }
        return getTokenByUserIdAndPwd(userId,password);
    }

    /**
     * 根据userId和加密的密码设置token
     * @param userId
     * @param password
     * @return
     */
    public JSONObject getTokenByUserIdAndPwd(String userId,String password){
        //设置返回对象
        JSONObject returnObj = new JSONObject();
        String url = "http://"+oauth2ServerProps.getIp()+":"+oauth2ServerProps.getPort()+"/"+oauth2ServerProps.getVersion()+"/oauth/token?username="+userId+"&password="+password+"&grant_type=password&scope=select&client_id="+oauth2ServerProps.getClient_id_value_pwd_mod()+"&client_secret="+oauth2ServerProps.getClient_secret_value_pwd_mod()+"";

        JSONObject tokenMsg = HttpClientServlet.httpPost(url);
        String access_token = StaticMethod.nullObject2String(tokenMsg.get("access_token"));
        logger.info(userId+"对应的票据为:"+access_token);
        if(!"".equals(access_token)){
            returnObj.put("websit_token",access_token);
            //登录成功将验证码记录删除
            eomsWebsitSmsService.deleteByMobile(userId);
            return ReturnJsonUtil.returnSuccessInfo(returnObj,"登录成功");
        }else{
            returnObj.put("websit_token","");
            return ReturnJsonUtil.returnFailInfo(returnObj,"密码错误，请重新输入");
        }
    }

}
