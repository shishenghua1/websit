package com.boco.eoms.websit.log.controller;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.websit.log.model.ContentNum;
import com.boco.eoms.websit.log.service.IStatisticsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ssh
 * @description:网站统计入口
 * @date 2019/11/11 15:00
 */
@RestController
@RequestMapping("/websit/v1/statistics")
@Api(value="网站统计controller",tags={"网站统计restful接口"})
public class StatisticsController {

    @Autowired
    private IStatisticsService statisticsService;

    /**
     * 网站统计excel导出
     *
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "网站统计excel导出,使用方式:直接使用地址进行导出即可。", notes = "网站统计excel导出", produces = "application/octet-stream")
    @ApiResponses({@ApiResponse(code = 500, message = "导出失败")})
    @GetMapping("download")
    public void download(HttpServletResponse response) {
        try {
            statisticsService.download(response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 工具和详情的数量统计
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("getContentNum")
    @ApiResponses({ @ApiResponse(code = 500, message = "查询异常") })
    @ApiOperation(value="工具和产品的数量统计查询",notes="工具和产品的数量统计查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="contentType",value="模块类型，包括产品和工具",required=true,paramType="query",dataType = "String"),
            @ApiImplicitParam(name="visitContentId",value="访问的模块id包括产品和工具的id",required=true,paramType="query",dataType = "String")
    })
    public List<ContentNum> getContentNum(String contentType,String visitContentId){
        try {
            return statisticsService.getContentNum(contentType,visitContentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
