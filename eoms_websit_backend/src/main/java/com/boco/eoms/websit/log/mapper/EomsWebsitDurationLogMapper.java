package com.boco.eoms.websit.log.mapper;

import com.boco.eoms.websit.log.model.EomsWebsitLog;
import org.apache.ibatis.annotations.Param;

/**
* @description: 网站访问时长mapper
* @author ssh
* @date 2019/10/30 15:31
*/
public interface EomsWebsitDurationLogMapper {

    /**
     * 网站访问时长对象保存
     * @param record
     * @return
     */
    int insert(EomsWebsitLog record);

    /**
     * 通过访问信息查询数据
     * @param visitorPrimaryKey  电脑唯一标识
     * @return
     */
    EomsWebsitLog selectByVisitInfo(String visitorPrimaryKey);

    /**
     * 网站访问时长对象修改
     * @param record
     * @return
     */
    int updateByPrimaryKey(EomsWebsitLog record);
}