package com.boco.eoms.websit.sms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.websit.sms.service.IEomsWebsitSmsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 短信接口
 * @author chenjianghe
 *
 */
@RestController
@RequestMapping("/websit/v1/websitSms")
@Api(value="短信controller",tags={"短信restful接口"})
public class EomsWebsitSmsController {

	private static final Logger logger = LoggerFactory.getLogger(EomsWebsitSmsController.class);
	
	@Autowired
	private IEomsWebsitSmsService eomsWebsitSmsService;
	 /**
     * 获取数据详情的数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("sendCode")
    @ApiResponses({ @ApiResponse(code = 500, message = "发送异常") })
    @ApiOperation(value="验证码发送",notes="验证码发送",response = Object.class)
	public Object sendCode(@RequestBody JSONObject jsonObject) {
		JSONObject json = new JSONObject();
    		try {
    			json = eomsWebsitSmsService.sendCode(StaticMethod.nullObject2String(jsonObject.get("mobile")));
		} catch (Exception e) {
			json.put("flag", false);
			json.put("msg", "短信发送异常");
			e.printStackTrace();
			logger.error("短信发送异常" + e);
		}
    		return json;
	}
    
    @ResponseBody
    @PostMapping("checkCode")
    @ApiResponses({ @ApiResponse(code = 500, message = "校验异常") })
    @ApiOperation(value="验证码校验",notes="验证码校验",response = Object.class)
    @ApiImplicitParam(name="mobile",value="手机号",dataType="String",paramType = "query")
	public Object checkcode(String code,String mobile)  {
		JSONObject json = new JSONObject();
    		try {
    			json = eomsWebsitSmsService.checkcode(code, mobile);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("msg", "短信校验异常");
			e.printStackTrace();
			logger.error("短信校验异常" + e);
		}
    		return json;
	}
}
