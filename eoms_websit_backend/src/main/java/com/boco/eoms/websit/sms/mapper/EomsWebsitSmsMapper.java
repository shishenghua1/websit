package com.boco.eoms.websit.sms.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.boco.eoms.websit.sms.model.EomsWebsitSms;

/**
 * 短信mapper
 * @author chenjianghe
 *
 */
public interface EomsWebsitSmsMapper {

	/**
	 * 插入
	 * @param eomsWebsitSms
	 * @return
	 */
	int insert(EomsWebsitSms eomsWebsitSms);
	
	/**
	 * 根据手机号查询最新一条验证码
	 * @param mobile
	 * @return
	 */
	Map selectCodeByMobile(@Param("mobile") String mobile);
	
	/**
	 * 根据手机号和验证码获取
	 * @param code
	 * @param mobile
	 * @return
	 */
	EomsWebsitSms selectByCodeAndMobile(@Param("code") String code,@Param("mobile") String mobile);
	
	/**
	 * 删除
	 * @param mobile
	 */
	void deleteByMobile(@Param("mobile") String mobile);
	
}
