package com.boco.eoms.websit.log.mapper;

import com.boco.eoms.websit.log.model.EomsWebsitLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @description: 网站日志表mapper
* @author ssh
* @date 2019/10/30 15:31
*/
public interface EomsWebsitLogMapper {

    /**
     * 网站日志对象保存
     * @param record
     * @return
     */
    int insert(EomsWebsitLog record);

    /**
     * 用户每天访问量集合
     * @return
     */
    List getUserVisitNum();

    /**
     * 根据访问模块和访问来源查询用户每天访问量
     * @param visitContent
     * @param visitOrigin
     * @return
     */
    List getUserNumByCondition(@Param("visitContent") String visitContent,@Param("visitOrigin") String visitOrigin);

    /**
     * 条件查询工具/产品访问量,其他模块无法使用该接口
     * @param timeType 时间类型，day表示按天统计，all表示所有数据
     * @param containTourist 是否包含游客，true表示包含，false表示只统计登录用户
     * @param visitContent 访问模块/功能
     * @param visitOrigin 访问来源
     * @param visitContentId 访问的工具或者产品标识
     * @return
     */
    List getContentVisitNum(@Param("timeType") String timeType,@Param("containTourist") String containTourist,
                            @Param("visitContent")String visitContent,@Param("visitOrigin")String visitOrigin,
                            @Param("visitContentId")String visitContentId);

    /**
     * 用户每天登录量,用户重复登录也算
     * @return
     */
    List getUserLoginNum();
}