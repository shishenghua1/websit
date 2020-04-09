package com.boco.eoms.websit.log.service;

import com.boco.eoms.websit.log.model.ContentNum;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ssh
 * @description:网站统计service接口
 * @date 2019/11/11 15:06
 */
public interface IStatisticsService {
    /**
     * 统计数据导出
     */
    void download(HttpServletResponse response)throws Exception;

    /**
     * 根据产品和工具标识查询访问次数
     * @param contentType
     * @param visitContentId
     * @return
     */
    List<ContentNum> getContentNum(String contentType, String visitContentId);
}
