package com.boco.eoms.websit.comment.controller;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.ReturnJsonUtil;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.websit.comment.model.EomsWebsitComment;
import com.boco.eoms.websit.comment.service.IWebistCommentService;
import com.boco.eoms.websit.log.model.EomsWebsitLog;
import com.boco.eoms.websit.log.service.IWebSitLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ssh
 * @description:
 * @date 2019/12/310:14
 */
@RestController
@RequestMapping("/websit/v1/comment")
@Api(value="网站留言controller",tags={"网站留言restful接口"})
public class WebSitCommentController {

    @Autowired
    private IWebistCommentService webistCommentService;

    /**
     *网站留言数据保存
     * @return
     * @throws Exception
     */
    @ApiOperation(value="网站留言数据保存",notes="网站留言数据保存")
    @ApiResponses({ @ApiResponse(code = 500, message = "保存异常") })
    @RequestMapping(method = RequestMethod.POST)
    public JSONObject insert(@RequestBody EomsWebsitComment eomsWebsitComment, HttpServletRequest req){
        try {
            //设置访问者ip
            eomsWebsitComment.setCommenterIp(StaticMethod.getIpAddr(req));
            return webistCommentService.insert(eomsWebsitComment);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnJsonUtil.returnFailInfo("留言保存失败");
        }
    }

    /**
     * 网站留言数据查询
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("list")
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="网站留言数据查询",notes="网站留言数据查询",response = EomsWebsitComment.class)
    public Object query(){
        try {
            return webistCommentService.findCommentList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 网站留言excel导出
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "网站留言excel导出,使用方式:直接使用地址进行导出即可。", notes = "网站留言excel导出", produces = "application/octet-stream")
    @ApiResponses({@ApiResponse(code = 500, message = "导出失败")})
    @GetMapping("download")
    public void download(HttpServletResponse response) {
        try {
            webistCommentService.download(response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
