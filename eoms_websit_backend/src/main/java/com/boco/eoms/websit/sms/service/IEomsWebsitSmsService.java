package com.boco.eoms.websit.sms.service;

import com.alibaba.fastjson.JSONObject;

public interface IEomsWebsitSmsService {
	
	public JSONObject sendCode(String mobile);
	
	public JSONObject checkcode(String code,String mobile);
	
	public void deleteByMobile(String mobile);
}
