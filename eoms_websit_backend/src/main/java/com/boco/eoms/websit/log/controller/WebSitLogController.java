package com.boco.eoms.websit.log.controller;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.ReturnJsonUtil;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.websit.log.model.EomsWebsitLog;
import com.boco.eoms.websit.log.service.IWebSitLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ssh
 * @description:网站日志接口
 * @date 2019/10/3015:41
 */
@RestController
@RequestMapping("/websit/v1/log")
@Api(value="网站日志controller",tags={"网站日志restful接口"})
public class WebSitLogController {

    @Autowired
    private IWebSitLogService webSitLogService;

    /**
     *网站日志数据保存
     * @return
     * @throws Exception
     */
    @ApiOperation(value="网站日志数据保存",notes="网站日志数据保存")
    @ApiResponses({ @ApiResponse(code = 500, message = "保存异常") })
    @RequestMapping(method = RequestMethod.POST)
    public JSONObject insert(@RequestBody EomsWebsitLog eomsWebsitLog, HttpServletRequest req){
        try {
            //设置访问者ip
            eomsWebsitLog.setVisitorIp(StaticMethod.getIpAddr(req));
            return webSitLogService.insert(eomsWebsitLog);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("保存失败");
        }
    }

}
