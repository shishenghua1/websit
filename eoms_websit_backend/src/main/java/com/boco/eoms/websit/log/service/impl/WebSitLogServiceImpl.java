package com.boco.eoms.websit.log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.*;
import com.boco.eoms.websit.log.mapper.EomsWebsitDurationLogMapper;
import com.boco.eoms.websit.log.mapper.EomsWebsitLogMapper;
import com.boco.eoms.websit.log.model.EomsWebsitLog;
import com.boco.eoms.websit.log.service.IWebSitLogService;
import com.maxmind.geoip2.DatabaseReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ssh
 * @description:网站日志业务实现层
 * @date 2019/10/3015:46
 */
@Service("webSitLogService")
public class WebSitLogServiceImpl implements IWebSitLogService {

    private Logger logger = LoggerFactory.getLogger(WebSitLogServiceImpl.class);

    @Autowired
    private EomsWebsitLogMapper eomsWebsitLogMapper;

    @Autowired
    private EomsWebsitDurationLogMapper eomsWebsitDurationLogMapper;

    @Autowired
    private Environment env;

    /**
     * 批量操作数据详情信息
     * @param eomsWebsitLog
     * @return
     */
    @Transactional
    public JSONObject insert(EomsWebsitLog eomsWebsitLog) throws Exception{
        if(eomsWebsitLog!=null){
            //获取userId
            String userId = StaticMethod.nullObject2String(SecurityContextHolder.getContext().getAuthentication().getName());
            //访问者唯一标识相关信息
            String visitorPrimaryKey = StaticMethod.nullObject2String(eomsWebsitLog.getVisitorPrimaryKey());
            //设置userId相关的内容
            if("".equals(visitorPrimaryKey)){
                if(!"anonymousUser".equals(userId)&&!"".equals(userId)){
                    eomsWebsitLog.setVisitorPrimaryKey(userId);
                }else{
                    return ReturnJsonUtil.returnFailInfo("日志保存失败");
                }
            }
            logger.info("日志记录的userId为----------->"+userId);
            if(!"anonymousUser".equals(userId)&&!"".equals(userId)){
                eomsWebsitLog.setVisitor(userId);
                eomsWebsitLog.setVisitorId(userId);
                eomsWebsitLog.setVisitorPhone(userId);
            }
            eomsWebsitLog.setId(UUIDHexGenerator.getInstance().getID());
            eomsWebsitLog.setVisitTime(new Date());
            eomsWebsitLog.setVisitFirstTime(new Date());
            eomsWebsitLog.setDurationId(UUIDHexGenerator.getInstance().getID());
            eomsWebsitLog.setVisitNewestTime(new Date());
            //设置访问者的地域信息
            String visitorIp = eomsWebsitLog.getVisitorIp();
            //该处无法判断程序本地运行获取的ip,按照公网处理，地域查询失败默认为空
            if(IPUtils.isInner(visitorIp)){
                //设置内网地域信息
                eomsWebsitLog.setVisitorCountry("中国");
                eomsWebsitLog.setVisitorProvince("北京市");
                eomsWebsitLog.setVisitorCity("北京");
            }else{
                String geoLite2CityPath = env.getProperty("geoLite2City.filePath");
                DatabaseReader databaseReader = IPUtils.getDatabaseReader(geoLite2CityPath);
                if(databaseReader!=null){
                    eomsWebsitLog.setVisitorCountry(StaticMethod.nullObject2String(IPUtils.getCountry(databaseReader,eomsWebsitLog.getVisitorIp())));
                    eomsWebsitLog.setVisitorProvince(StaticMethod.nullObject2String(IPUtils.getProvince(databaseReader,eomsWebsitLog.getVisitorIp())));
                    eomsWebsitLog.setVisitorCity(StaticMethod.nullObject2String(IPUtils.getCity(databaseReader,eomsWebsitLog.getVisitorIp())));
                }else{
                    logger.info("ip获取地域的离线库文件配置有错,请检查");
                }
            }
            //访问模块/功能
            String visitContent = StaticMethod.nullObject2String(eomsWebsitLog.getVisitContent());
            //如果为退出操作，则设置退出时间
            if("用户退出".equals(visitContent)){
                eomsWebsitLog.setQuitTime(new Date());
            }
            EomsWebsitLog eomsWebsitDurationLog = eomsWebsitDurationLogMapper.selectByVisitInfo(eomsWebsitLog.getVisitorPrimaryKey());
            //判断访问时长表是否已存在该访问者的信息
            if(eomsWebsitDurationLog!=null){
                //访问者最新一条数据的退出时间
                String quitTime = StaticMethod.nullObject2String(eomsWebsitDurationLog.getQuitTime());
                //访问者的最近访问时间
                Date visitNewestTime = eomsWebsitDurationLog.getVisitNewestTime();
                //访问者第一次访问的时间
                Date visitFirstTime = eomsWebsitDurationLog.getVisitFirstTime();

                if("".equals(quitTime)){
                    //设置退出时间默认为空
                    eomsWebsitLog.setQuitTime(null);

                    Double minute = DateUtils.getDistanceOfTwoDateMinute(visitNewestTime,new Date());
                    //分钟差
                    Double minuteGap = Double.valueOf(env.getProperty("defaultAccessTime.minute"));
                    if(minute>minuteGap){
                        /**
                         * 如果分钟差大于设定值，则将历史数据设置为退出登录，退出时间为当前时间
                         * 并创建一条新的数据为最新访问记录
                         */
                        eomsWebsitDurationLog.setQuitTime(new Date());
                        eomsWebsitDurationLogMapper.updateByPrimaryKey(eomsWebsitDurationLog);

                        //创建新的记录
                        eomsWebsitDurationLogMapper.insert(eomsWebsitLog);
                    }else{
                        //更新最新访问时间
                        eomsWebsitLog.setDurationId(eomsWebsitDurationLog.getId());
                        eomsWebsitDurationLog.setVisitNewestTime(new Date());
                        eomsWebsitDurationLogMapper.updateByPrimaryKey(eomsWebsitDurationLog);
                    }
                }else{
                    eomsWebsitDurationLogMapper.insert(eomsWebsitLog);
                }
            }else{
                eomsWebsitDurationLogMapper.insert(eomsWebsitLog);
            }


            //根据访问模块的内容是否为空判断网站日志表数据是否需要保存
            if(!"".equals(visitContent)){
                eomsWebsitLogMapper.insert(eomsWebsitLog);
            }
            logger.info("日志保存成功");
            return ReturnJsonUtil.returnSuccessInfo("日志保存成功");
        }else{
            logger.info("日志保存失败");
            return ReturnJsonUtil.returnFailInfo("日志保存失败");
        }
    }
}
