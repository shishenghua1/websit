package com.boco.eoms.websit.comment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.base.util.*;
import com.boco.eoms.websit.comment.mapper.EomsWebsitCommentMapper;
import com.boco.eoms.websit.comment.model.EomsWebsitComment;
import com.boco.eoms.websit.comment.service.IWebistCommentService;
import com.boco.eoms.websit.log.model.EomsWebsitLog;
import com.boco.eoms.websit.log.service.impl.WebSitLogServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ssh
 * @description:
 * @date 2019/12/310:09
 */
@Service("webistCommentService")
public class WebistCommentServiceImpl implements IWebistCommentService {

    @Autowired
    private EomsWebsitCommentMapper eomsWebsitCommentMapper;

    private Logger logger = LoggerFactory.getLogger(WebistCommentServiceImpl.class);

    @Transactional
    public JSONObject insert(EomsWebsitComment eomsWebsitComment) throws Exception{
        if(eomsWebsitComment!=null){
            //获取userId
            String userId = StaticMethod.nullObject2String(SecurityContextHolder.getContext().getAuthentication().getName());
            logger.info("留言记录的userId为----------->"+userId);
            if(!"anonymousUser".equals(userId)&&!"".equals(userId)){
                eomsWebsitComment.setCommenter(userId);
                eomsWebsitComment.setCommenterName(userId);
            }
            eomsWebsitComment.setId(UUIDHexGenerator.getInstance().getID());
            eomsWebsitComment.setCommentTime(new Date());

            eomsWebsitCommentMapper.insert(eomsWebsitComment);
            logger.info("留言保存成功");
            return ReturnJsonUtil.returnSuccessInfo("留言保存成功");
        }else{
            logger.info("留言保存失败");
            return ReturnJsonUtil.returnFailInfo("留言保存失败");
        }
    }

    @Override
    public List<EomsWebsitComment> findCommentList() {
        return eomsWebsitCommentMapper.findCommentList();
    }

    /**
     * 统计导出功能
     * @param response
     * @throws Exception
     */
    public void download(HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //新增数据行，并且设置单元格数据
        setExcelData(workbook);
        //解决文件名乱码
        String fileName = new String(("产品官网留言统计").getBytes("gb2312"), "ISO-8859-1") + ".xls";
        //清空response
        response.reset();
        //设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename="+ fileName);
        response.setHeader("Access-Control-Allow-Origin", "*");
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/vnd.ms-excel;charset=gb2312");
        //将excel写入到输出流中
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * excel数据的设置
     * @param workbook
     * @return
     */
    public void setExcelData(HSSFWorkbook workbook) throws Exception {
        //创建一个留言Excel表单,参数为sheet的名字
        HSSFSheet commentSheet = workbook.createSheet("留言记录");
        //初始化集合
        List<String> commentTitleList = Stream.of("留言人","手机号","访问者ip",
                "名称", "类型","吐槽点", "留言内容", "网站版本", "浏览器类型",
                "浏览器版本", "操作系统", "操作系统版本", "操作系统位数",
                "留言时间").collect(Collectors.toList());
        //创建表头
        ExcelUtil.setTitle(workbook, commentSheet, commentTitleList);
        List<EomsWebsitComment> eomsWebsitCommentList = eomsWebsitCommentMapper.findCommentList();

        //新增数据行，并且设置单元格数据
        //名称
        String name = "";
        //类型
        String type="";
        int rowNum = 1;
        for (EomsWebsitComment eomsWebsitComment:eomsWebsitCommentList) {
            String toolId = StaticMethod.nullObject2String(eomsWebsitComment.getCommentToolId());
            String productId = StaticMethod.nullObject2String(eomsWebsitComment.getCommentProductId());
            if("".equals(toolId)&&!"".equals(productId)){
                type = "产品";
                name=StaticMethod.nullObject2String(eomsWebsitComment.getCommentProductName());
            }else if(!"".equals(toolId)&&"".equals(productId)){
                type = "工具";
                name=StaticMethod.nullObject2String(eomsWebsitComment.getCommentToolName());
            }else{
                logger.info("留言数据存在异常，异常的id为----"+eomsWebsitComment.getId());
                continue;
            }
            HSSFRow row = commentSheet.createRow(rowNum);
            row.createCell(0).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenter()));
            row.createCell(1).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenterName()));
            row.createCell(2).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenterIp()));
            row.createCell(3).setCellValue(name);
            row.createCell(4).setCellValue(type);
            row.createCell(5).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommentType()));
            row.createCell(6).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommentContent()));
            row.createCell(7).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommentWebsitVersion()));
            row.createCell(8).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenterBrowserType()));
            row.createCell(9).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenterBrowserVersion()));
            row.createCell(10).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenterOs()));
            row.createCell(11).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenterOsVersion()));
            row.createCell(12).setCellValue(StaticMethod.nullObject2String(eomsWebsitComment.getCommenterOsBit()));
            row.createCell(13).setCellValue(DateUtils.formatDateTime(eomsWebsitComment.getCommentTime()));
            rowNum++;
        }

    }
}
