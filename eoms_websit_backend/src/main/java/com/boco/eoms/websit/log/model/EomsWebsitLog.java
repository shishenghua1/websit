package com.boco.eoms.websit.log.model;

import com.boco.eoms.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
* @description: 网站日志表实体
* @author ssh
* @date 2019/10/30 15:21
*/
@ApiModel(value="网站日志表实体,系统生成的不需要填写")
public class EomsWebsitLog {

    @ApiModelProperty(value="主键id,系统生成",hidden=true)
    private String id;

    @ApiModelProperty(value="访问者",example="测试者",hidden=true)
    private String visitor;

    @ApiModelProperty(value="访问者标识",example="ceshizhe",hidden=true)
    private String visitorId;

    @ApiModelProperty(value="访问者手机号",example="18234470892",hidden=true)
    private String visitorPhone;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="访问时间，即为数据产生时间,系统生成",hidden=true)
    private Date visitTime;

    @ApiModelProperty(value="访问模块/功能，字典值包括首页,产品详情，工具详情，产品试用，工具试用，用户登录，用户退出",example="产品详情")
    private String visitContent;

    @ApiModelProperty(value="访问来源，针对工具/产品的详情，字典值包括首页,菜单，按钮,空",example="菜单")
    private String visitOrigin;

    @ApiModelProperty(value="访问的url",example="https://www.baidu.com/")
    private String visitorUrl;

    @ApiModelProperty(value="访问者ip",example="10.10.1.1")
    private String visitorIp;

    @ApiModelProperty(value="访问的产品id",example="7eb90bc4036145bc80dcbaba5176496c")
    private String visitProductId;

    @ApiModelProperty(value="访问的产品名字",example="产品1")
    private String visitProductName;

    @ApiModelProperty(value="访问的工具id",example="")
    private String visitToolId;

    @ApiModelProperty(value="访问的工具名字",example="")
    private String visitToolName;

    @ApiModelProperty(value="访问者的操作系统,字典值包括但不限于windows，linux，android,ios,mac_os",example="windows")
    private String visitorOs;

    @ApiModelProperty(value="访问者的操作系统版本",example="windows98")
    private String visitorOsVersion;

    @ApiModelProperty(value="访问者的操作系统位数，字典值包括64或32",example="32")
    private String visitorOsBit;

    @ApiModelProperty(value="访问者的网站版本，目前1.0",example="1.0")
    private String visitWebsitVersion;

    @ApiModelProperty(value="访问者的浏览器类型，字典值包括chrome, firefox，safari，ie,360，qq",example="chrome")
    private String visitorBrowserType;

    @ApiModelProperty(value="访问者的浏览器版本",example="ie9")
    private String visitorBrowserVersion;

    @ApiModelProperty(value="访问方式,字典值包括mobile/手机或pc/电脑",example="mobile")
    private String visitWay;

    @ApiModelProperty(value="时长表主键id,系统生成",hidden=true)
    private String durationId;

    @ApiModelProperty(value="访问者唯一标识,该字段确定用户唯一性",example="07-16-76-00-02-86")
    private String visitorPrimaryKey;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="首次访问时间，系统生成",dataType="Date",hidden=true)
    private Date visitFirstTime;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="最新访问时间，系统生成",dataType="Date",hidden=true)
    private Date visitNewestTime;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="退出时间，系统生成",dataType="Date",hidden=true)
    private Date quitTime;

    @ApiModelProperty(value="访问者国家，系统生成",example="中国",hidden=true)
    private String visitorCountry;

    @ApiModelProperty(value="访问者省份，系统生成",example="山西",hidden=true)
    private String visitorProvince;

    @ApiModelProperty(value="访问者地市，系统生成",example="阳泉",hidden=true)
    private String visitorCity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor == null ? null : visitor.trim();
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId == null ? null : visitorId.trim();
    }

    public String getVisitorPhone() {
        return visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone) {
        this.visitorPhone = visitorPhone == null ? null : visitorPhone.trim();
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitContent() {
        return visitContent;
    }

    public void setVisitContent(String visitContent) {
        this.visitContent = visitContent == null ? null : visitContent.trim();
    }

    public String getVisitOrigin() {
        return visitOrigin;
    }

    public void setVisitOrigin(String visitOrigin) {
        this.visitOrigin = visitOrigin == null ? null : visitOrigin.trim();
    }

    public String getVisitorUrl() {
        return visitorUrl;
    }

    public void setVisitorUrl(String visitorUrl) {
        this.visitorUrl = visitorUrl == null ? null : visitorUrl.trim();
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp == null ? null : visitorIp.trim();
    }

    public String getVisitProductId() {
        return visitProductId;
    }

    public void setVisitProductId(String visitProductId) {
        this.visitProductId = visitProductId == null ? null : visitProductId.trim();
    }

    public String getVisitProductName() {
        return visitProductName;
    }

    public void setVisitProductName(String visitProductName) {
        this.visitProductName = visitProductName == null ? null : visitProductName.trim();
    }

    public String getVisitToolId() {
        return visitToolId;
    }

    public void setVisitToolId(String visitToolId) {
        this.visitToolId = visitToolId == null ? null : visitToolId.trim();
    }

    public String getVisitToolName() {
        return visitToolName;
    }

    public void setVisitToolName(String visitToolName) {
        this.visitToolName = visitToolName == null ? null : visitToolName.trim();
    }

    public String getVisitorOs() {
        return visitorOs;
    }

    public void setVisitorOs(String visitorOs) {
        this.visitorOs = visitorOs == null ? null : visitorOs.trim();
    }

    public String getVisitorOsVersion() {
        return visitorOsVersion;
    }

    public void setVisitorOsVersion(String visitorOsVersion) {
        this.visitorOsVersion = visitorOsVersion == null ? null : visitorOsVersion.trim();
    }

    public String getVisitorOsBit() {
        return visitorOsBit;
    }

    public void setVisitorOsBit(String visitorOsBit) {
        this.visitorOsBit = visitorOsBit == null ? null : visitorOsBit.trim();
    }

    public String getVisitWebsitVersion() {
        return visitWebsitVersion;
    }

    public void setVisitWebsitVersion(String visitWebsitVersion) {
        this.visitWebsitVersion = visitWebsitVersion;
    }

    public String getVisitorBrowserType() {
        return visitorBrowserType;
    }

    public void setVisitorBrowserType(String visitorBrowserType) {
        this.visitorBrowserType = visitorBrowserType == null ? null : visitorBrowserType.trim();
    }

    public String getVisitorBrowserVersion() {
        return visitorBrowserVersion;
    }

    public void setVisitorBrowserVersion(String visitorBrowserVersion) {
        this.visitorBrowserVersion = visitorBrowserVersion == null ? null : visitorBrowserVersion.trim();
    }

    public String getVisitWay() {
        return visitWay;
    }

    public void setVisitWay(String visitWay) {
        this.visitWay = visitWay == null ? null : visitWay.trim();
    }

    public String getDurationId() {
        return durationId;
    }

    public void setDurationId(String durationId) {
        this.durationId = durationId == null ? null : durationId.trim();
    }

    public Date getVisitFirstTime() {
        return visitFirstTime;
    }

    public void setVisitFirstTime(Date visitFirstTime) {
        this.visitFirstTime = visitFirstTime;
    }

    public Date getVisitNewestTime() {
        return visitNewestTime;
    }

    public void setVisitNewestTime(Date visitNewestTime) {
        this.visitNewestTime = visitNewestTime;
    }

    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
    }

    public String getVisitorPrimaryKey() {
        return visitorPrimaryKey;
    }

    public void setVisitorPrimaryKey(String visitorPrimaryKey) {
        this.visitorPrimaryKey = visitorPrimaryKey;
    }

    public String getVisitorCountry() {
        return visitorCountry;
    }

    public void setVisitorCountry(String visitorCountry) {
        this.visitorCountry = visitorCountry;
    }

    public String getVisitorProvince() {
        return visitorProvince;
    }

    public void setVisitorProvince(String visitorProvince) {
        this.visitorProvince = visitorProvince;
    }

    public String getVisitorCity() {
        return visitorCity;
    }

    public void setVisitorCity(String visitorCity) {
        this.visitorCity = visitorCity;
    }
}