package com.boco.eoms.websit.log.service.impl;

import com.boco.eoms.base.util.ExcelUtil;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.websit.log.mapper.EomsWebsitDurationLogMapper;
import com.boco.eoms.websit.log.mapper.EomsWebsitLogMapper;
import com.boco.eoms.websit.log.model.ContentNum;
import com.boco.eoms.websit.log.service.IStatisticsService;
import com.boco.eoms.websit.systemuser.mapper.EomsSystemUserMapper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ssh
 * @description:网站统计业务层
 * @date 2019/11/1115:06
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private EomsWebsitLogMapper eomsWebsitLogMapper;

    @Autowired
    private EomsSystemUserMapper eomsSystemUserMapper;

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
        String fileName = new String(("产品官网访问统计").getBytes("gb2312"), "ISO-8859-1") + ".xls";
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

    @Override
    public List<ContentNum> getContentNum(String contentType, String visitContentId) {
        List<ContentNum> contentNum = new ArrayList<>();
        //获取对应产品或者工具的详情访问数量
        List<Map> contentDetailNumList = eomsWebsitLogMapper.getContentVisitNum("","true",contentType+"详情","",visitContentId);
        ContentNum contentDetailNum = new ContentNum();
        if(contentDetailNumList!=null&&contentDetailNumList.size()>0){
            contentDetailNum.setContentType("详情");
            contentDetailNum.setVisitNum(String.valueOf(contentDetailNumList.get(0).get("userNum")));
        }else{
            contentDetailNum.setContentType("详情");
            contentDetailNum.setVisitNum("0");
        }
        contentNum.add(contentDetailNum);

        //获取对应产品或者工具的试用访问数量
        List<Map> contentTryOutNumList = eomsWebsitLogMapper.getContentVisitNum("","false",contentType+"试用","",visitContentId);
        ContentNum contentTryOutNum = new ContentNum();
        if(contentTryOutNumList!=null&&contentTryOutNumList.size()>0){
            contentTryOutNum.setContentType("试用");
            contentTryOutNum.setVisitNum(String.valueOf(contentTryOutNumList.get(0).get("userNum")));
        }else{
            contentTryOutNum.setContentType("试用");
            contentTryOutNum.setVisitNum("0");
        }
        contentNum.add(contentTryOutNum);

        return contentNum;
    }

    /**
     * excel数据的设置
     * @param workbook
     * @return
     */
    public void setExcelData(HSSFWorkbook workbook) throws Exception{
        //每日访问的excel设置
        setEveryDayVisitExcel(workbook);

        //产品sheet内容设置
        setExcelPruductAndTool(workbook,"产品");

        //工具sheet内容设置
        setExcelPruductAndTool(workbook,"工具");
    }

    /**
     * 每日访问的excel设置
     * @param workbook
     */
    public void setEveryDayVisitExcel(HSSFWorkbook workbook){
        //创建一个每日Excel表单,参数为sheet的名字
        HSSFSheet visitSheet = workbook.createSheet("每日访问");
        //初始化集合
        List<String> visitTitleList = Stream.of("日期", "访问人数", "注册人数","登录人数").collect(Collectors.toList());
        //创建表头
        ExcelUtil.setTitle(workbook, visitSheet,visitTitleList);

        //统计每日访问时间
        TreeSet<String> tree = new TreeSet<String>();
        //每天用户访问量
        List<Map> userVisitNum = eomsWebsitLogMapper.getUserVisitNum();
        //每日访问量map
        Map visitMap = new HashMap();
        for (int i = 0; i < userVisitNum.size(); i++) {
            String visitTime=String.valueOf(userVisitNum.get(i).get("visitTime"));
            String userNum = String.valueOf(userVisitNum.get(i).get("userNum"));
            tree.add(visitTime);
            visitMap.put(visitTime,userNum);
        }
        //用户每日注册数
        List<Map> userRegisterNum = eomsSystemUserMapper.getUserRegisterNum();
        //每日注册量map
        Map registerMap = new HashMap();
        for (int i = 0; i < userRegisterNum.size(); i++) {
            String registerTime=String.valueOf(userRegisterNum.get(i).get("registerTime"));
            String registerNum = String.valueOf(userRegisterNum.get(i).get("registerNum"));
            tree.add(registerTime);
            registerMap.put(registerTime,registerNum);
        }
        //用户每日登录数
        List<Map> userLoginNum=eomsWebsitLogMapper.getUserLoginNum();
        //每日登录map
        Map loginMap = new HashMap();
        for (int i = 0; i < userLoginNum.size(); i++) {
            String loginTime=String.valueOf(userLoginNum.get(i).get("loginTime"));
            String userNum = String.valueOf(userLoginNum.get(i).get("userNum"));
            tree.add(loginTime);
            loginMap.put(loginTime,userNum);
        }
        int rowNum = 1;
        for(Iterator iter = tree.iterator(); iter.hasNext(); ) {
            HSSFRow row = visitSheet.createRow(rowNum);
            String time = String.valueOf(iter.next());
            String visitNum = StaticMethod.nullObject2String(visitMap.get(time));
            if("".equals(visitNum)){
                visitNum = "0";
            }
            String registerNum = StaticMethod.nullObject2String(registerMap.get(time));
            if("".equals(registerNum)){
                registerNum = "0";
            }
            String loginNum = StaticMethod.nullObject2String(loginMap.get(time));
            if("".equals(loginNum)){
                loginNum = "0";
            }
            row.createCell(0).setCellValue(time);
            row.createCell(1).setCellValue(visitNum);
            row.createCell(2).setCellValue(registerNum);
            row.createCell(3).setCellValue(loginNum);
            rowNum++;
        }
    }

    /**
     * 根据访问内容为工具或者产品进行excel的设置
     * @param workbook
     * @param visitContent
     */
    public void setExcelPruductAndTool(HSSFWorkbook workbook,String visitContent){
        String excelName = "";
        String contentName ="";
        if(visitContent.contains("产品")){
            excelName="产品";
            contentName = "visitProductName";
        }else if(visitContent.contains("工具")){
            excelName="工具";
            contentName = "visitToolName";
        }
        //创建一个模块Excel表单,参数为sheet的名字
        HSSFSheet contentSheet = workbook.createSheet(excelName);
        //初始化集合
        List<String> contentTitleList = Stream.of("日期", excelName+"名称", "类型","数量").collect(Collectors.toList());
        //创建表头
        ExcelUtil.setTitle(workbook, contentSheet,contentTitleList);
        //模块list
        List<Map> contentList = new ArrayList<>();
        //每天模块详情查看量
        List<Map> contentDetailNum = eomsWebsitLogMapper.getContentVisitNum("day","true",excelName+"详情","","");
        //每天模块试用量
        List<Map> contentTryOutNum = eomsWebsitLogMapper.getContentVisitNum("day","false",excelName+"试用","","");
        contentList.addAll(contentTryOutNum);
        contentList.addAll(contentDetailNum);

        //统计模块访问时间和名称
        TreeSet<String> contentTree = new TreeSet<String>();

        for (int i = 0; i < contentList.size(); i++) {
            String visitTime = String.valueOf(contentList.get(i).get("visitTime"));
            String visitContentName = String.valueOf(contentList.get(i).get(contentName));
            contentTree.add(visitTime+"_"+visitContentName);
        }
        //根据时间设置数据
        int contentRowNum = 1;
        for(Iterator iter = contentTree.iterator(); iter.hasNext(); ) {
            String contentKey = String.valueOf(iter.next());
            //判断是否存在详情数据
            boolean isExistDetail = false;
            //判断是否存在试用数据
            boolean isExistTryOut = false;
            //试用和详情的map
            Map detailMap = new HashMap();
            Map tryOutMap = new HashMap();
            //获取模块对应的详情数据
            for (int i = 0; i < contentList.size(); i++) {
                String visitTime = String.valueOf(contentList.get(i).get("visitTime"));
                String visitContentName = String.valueOf(contentList.get(i).get(contentName));
                String mapKey = visitTime+"_"+visitContentName;
                //访问的模块,用于区分是详情还是试用
                String visitIndexContent = StaticMethod.nullObject2String(contentList.get(i).get("visitContent"));
                if(contentKey.equals(mapKey)&&visitIndexContent.contains("详情")){
                    isExistDetail=true;
                    detailMap = contentList.get(i);
                }
            }
            //获取模块对应的试用数据
            for (int i = 0; i < contentList.size(); i++) {
                String visitTime = String.valueOf(contentList.get(i).get("visitTime"));
                String visitContentName = String.valueOf(contentList.get(i).get(contentName));
                String mapKey = visitTime+"_"+visitContentName;
                //访问的模块,用于区分是详情还是试用
                String visitIndexContent = StaticMethod.nullObject2String(contentList.get(i).get("visitContent"));
                if(contentKey.equals(mapKey)&&visitIndexContent.contains("试用")){
                    isExistTryOut=true;
                    tryOutMap = contentList.get(i);
                }
            }
            //详情存在，试用不存在的情况
            if(isExistDetail&&!isExistTryOut){
                HSSFRow detailRow = contentSheet.createRow(contentRowNum);
                detailRow.createCell(0).setCellValue(String.valueOf(detailMap.get("visitTime")));
                detailRow.createCell(1).setCellValue(String.valueOf(detailMap.get(contentName)));
                detailRow.createCell(2).setCellValue("详情");
                detailRow.createCell(3).setCellValue(String.valueOf(detailMap.get("userNum")));
                contentRowNum++;

                HSSFRow tryOutRow = contentSheet.createRow(contentRowNum);
                tryOutRow.createCell(0).setCellValue(String.valueOf(detailMap.get("visitTime")));
                tryOutRow.createCell(1).setCellValue(String.valueOf(detailMap.get(contentName)));
                tryOutRow.createCell(2).setCellValue("试用");
                tryOutRow.createCell(3).setCellValue("0");
                contentRowNum++;
            }else if(!isExistDetail&&isExistTryOut){
                //详情不存在，试用存在的情况
                HSSFRow detailRow = contentSheet.createRow(contentRowNum);
                detailRow.createCell(0).setCellValue(String.valueOf(tryOutMap.get("visitTime")));
                detailRow.createCell(1).setCellValue(String.valueOf(tryOutMap.get(contentName)));
                detailRow.createCell(2).setCellValue("详情");
                detailRow.createCell(3).setCellValue("0");
                contentRowNum++;

                HSSFRow tryOutRow = contentSheet.createRow(contentRowNum);
                tryOutRow.createCell(0).setCellValue(String.valueOf(tryOutMap.get("visitTime")));
                tryOutRow.createCell(1).setCellValue(String.valueOf(tryOutMap.get(contentName)));
                tryOutRow.createCell(2).setCellValue("试用");
                tryOutRow.createCell(3).setCellValue(String.valueOf(tryOutMap.get("userNum")));
                contentRowNum++;
            }else{
                //详情和试用都存在的情况
                HSSFRow detailRow = contentSheet.createRow(contentRowNum);
                detailRow.createCell(0).setCellValue(String.valueOf(detailMap.get("visitTime")));
                detailRow.createCell(1).setCellValue(String.valueOf(detailMap.get(contentName)));
                detailRow.createCell(2).setCellValue("详情");
                detailRow.createCell(3).setCellValue(String.valueOf(detailMap.get("userNum")));
                contentRowNum++;

                HSSFRow tryOutRow = contentSheet.createRow(contentRowNum);
                tryOutRow.createCell(0).setCellValue(String.valueOf(tryOutMap.get("visitTime")));
                tryOutRow.createCell(1).setCellValue(String.valueOf(tryOutMap.get(contentName)));
                tryOutRow.createCell(2).setCellValue("试用");
                tryOutRow.createCell(3).setCellValue(String.valueOf(tryOutMap.get("userNum")));
                contentRowNum++;
            }
        }
    }
}