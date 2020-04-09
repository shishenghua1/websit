package com.boco.eoms.websit.log.service;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.websit.log.model.EomsWebsitLog;

/**
 * @author ssh
 * @description:网站日志service
 * @date 2019/10/3015:44
 */
public interface IWebSitLogService {
    /**
     * 保存网站日志数据
     * @param record
     * @return
     * @throws Exception
     */
    public JSONObject insert(EomsWebsitLog record)  throws Exception;
}
