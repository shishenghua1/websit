package com.boco.eoms.websit.sms.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.config.QcloudSms;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.websit.sms.controller.EomsWebsitSmsController;
import com.boco.eoms.websit.sms.mapper.EomsWebsitSmsMapper;
import com.boco.eoms.websit.sms.model.EomsWebsitSms;
import com.boco.eoms.websit.sms.service.IEomsWebsitSmsService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

/**
 * 短信service
 * @author chenjianghe
 *
 */
@Service
public class EomsWebsitSmsServiceImpl implements IEomsWebsitSmsService{
	
	private static final Logger logger = LoggerFactory.getLogger(EomsWebsitSmsController.class);
	
	@Autowired
	private EomsWebsitSmsMapper eomsWebsitSmsMapper;
	
	@Autowired
	private QcloudSms qcloudSms;

	/**
	 * 发送验证码
	 */
	@Override
	public JSONObject sendCode(String mobile) {
		JSONObject json = new JSONObject();
		try {
			  //将该手机号下没用的验证码删除
			  deleteByMobile(mobile);
			  String code = (int)((Math.random()*9+1)*100000)+"";
			  String[] params = {code};
			  SmsSingleSender ssender = new SmsSingleSender(qcloudSms.getAppId(), qcloudSms.getAppKey());
			  SmsSingleSenderResult result = ssender.sendWithParam("86", mobile, qcloudSms.getTemplateId(), params, qcloudSms.getSmsSign(), "", "");
			  if(result.result == 0 && "OK".equals(result.errMsg)) {
				  json.put("flag", true);
				  json.put("msg", "短信发送成功");
				  //短信发送成功 存入短信表
				  EomsWebsitSms eomsWebsitSms = new EomsWebsitSms();
				  eomsWebsitSms.setId(UUIDHexGenerator.getInstance().getID());
				  eomsWebsitSms.setCode(code);
				  eomsWebsitSms.setContent("");
				  eomsWebsitSms.setMobile(mobile);
				  eomsWebsitSms.setSendTime(new Date());
				  eomsWebsitSms.setDeleted("0");
				  eomsWebsitSmsMapper.insert(eomsWebsitSms);
			  }else{
				  logger.info(result.toString());
				  json.put("flag", false);
				  json.put("msg", "短信发送失败,请稍后再试!");
			  }
			} catch (HTTPException e) {
			  // HTTP 响应码错误
			  e.printStackTrace();
			  json.put("flag", false);
			  json.put("msg", "响应码错误");
			} catch (org.json.JSONException e) {
			  // json解析错误
			  e.printStackTrace();
			  json.put("flag", false);
			  json.put("msg", "json解析错误");
			}  catch (IOException e) {
			  // 网络 IO 错误
			  e.printStackTrace();
			  json.put("flag", false);
			  json.put("msg", "网络错误错误");
			} catch (Exception e) {
			  e.printStackTrace();
			  json.put("flag", false);
			  json.put("msg", "保存失败");
			} 
		logger.info(json.toJSONString());
		return json;
	}

	/**
	 * 验证码校验
	 */
	@Override
	public JSONObject checkcode(String code,String mobile) {
		JSONObject json = new JSONObject();
		EomsWebsitSms eomsWebsitSms = eomsWebsitSmsMapper.selectByCodeAndMobile(code, mobile);
		if(eomsWebsitSms != null) {
			Date send_time = eomsWebsitSms.getSendTime();
			Date nowDate = new Date();
			long time = 5*50*1000;
			if((nowDate.getTime() - send_time.getTime()) <= time) {
				json.put("flag", true);
				json.put("msg", "成功");
			}else {
				json.put("flag", false);
				json.put("msg", "验证码已超时失效");
			}
		}else {
			json.put("flag", false);
			json.put("msg", "验证码输入错误");
		}
		return json;
	}

	/**
	 * 逻辑删除
	 */
	@Override
	public void deleteByMobile(String mobile) {
		eomsWebsitSmsMapper.deleteByMobile(mobile);
	}

}
