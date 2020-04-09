package com.boco.eoms.websit.systemuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.ReturnJsonUtil;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.websit.systemuser.model.EomsSystemUser;
import com.boco.eoms.websit.systemuser.service.IUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ssh
 * @description:用户操作接口，包括登录，注册等功能
 * @date 2019/10/3115:09
 */
@RestController
@RequestMapping("/websit/v1/systemuser")
@Api(value="用户controller",tags={"用户restful接口"})
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     *网站注册接口
     * @return
     * @throws Exception
     */
    @ApiOperation(value="网站注册",notes="网站注册")
    @ApiResponses({ @ApiResponse(code = 500, message = "保存异常") })
    @RequestMapping(method = RequestMethod.POST)
    public JSONObject insert(@RequestBody EomsSystemUser eomsSystemUser){
        try {
            return userService.operateUserInfo(eomsSystemUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("注册失败");
        }
    }

    /**
     * 获取数据详情的数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("getUser")
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="用户信息查询",notes="用户信息查询",response = EomsSystemUser.class)
    public Object query(){
        try {
            return userService.selectByMobile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *个人信息修改
     * @return
     * @throws Exception
     */
    @ApiOperation(value="个人信息修改,条件依据为userId",notes="个人信息修改,条件依据为userId")
    @ApiResponses({ @ApiResponse(code = 500, message = "修改异常") })
    @RequestMapping(method=RequestMethod.PUT)
    public JSONObject updateUser(@RequestBody EomsSystemUser eomsSystemUser){
        try {
            userService.updateUser(eomsSystemUser);
            return ReturnJsonUtil.returnSuccessInfo("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("修改失败");
        }
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ApiOperation(value="网站登录",notes="网站登录")
    @ApiResponses({ @ApiResponse(code = 500, message = "登录异常") })
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户名",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="password",value="密码",required=false,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="code",value="短信验证码",required=false,paramType="query",dataType = "String")
    })
    public JSONObject websitLogin(String userId,@ApiParam(name="password",value="密码",required=false)String password,
                                  @ApiParam(name="code",value="短信验证码",required=false) String code){
        try {
            return userService.websitLogin(userId,password,code);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断用户是否存在
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("userExists")
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="判断用户是否存在,true表示存在,false表示不存在",notes="判断用户是否存在",response = String.class)
    @ApiImplicitParam(name="userId",value="账户标识",required=true,paramType="query",dataType = "String")
    public String userExists(String userId){
        try {
            EomsSystemUser user = userService.selectByMobile(userId);
            if(user==null){
                return "false";
            }else{
                return "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

}
