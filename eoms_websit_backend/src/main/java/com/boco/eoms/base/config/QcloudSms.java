package com.boco.eoms.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 短信配置
 * @author chenjianghe
 *
 */
@Component
@ConfigurationProperties(prefix="qcloudsms")
public class QcloudSms {

	private int appId;
	
	private String appKey;
	
	private int templateId;
	
	private String smsSign;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getSmsSign() {
		return smsSign;
	}

	public void setSmsSign(String smsSign) {
		this.smsSign = smsSign;
	}
	
	
}
