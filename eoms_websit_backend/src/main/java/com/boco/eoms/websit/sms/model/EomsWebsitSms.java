package com.boco.eoms.websit.sms.model;

import java.util.Date;

/**
 * 短信model 
 * @author chenjianghe
 *
 */
public class EomsWebsitSms {

	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 验证码
	 */
	private String code;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 发送时间
	 */
	private Date sendTime;
	
	/**
	 * 删除标识
	 */
	private String deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
}
