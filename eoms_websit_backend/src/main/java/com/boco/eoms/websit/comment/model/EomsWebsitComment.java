package com.boco.eoms.websit.comment.model;

import com.boco.eoms.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
* @description: 网站留言表实体
* @author ssh
* @date 2019/12/3 9:40
*/
@ApiModel(value="网站留言表实体,系统生成的不需要填写")
public class EomsWebsitComment {

    @ApiModelProperty(value="主键id,系统生成",hidden=true)
    private String id;

    @ApiModelProperty(value="留言者标识,系统生成",example="15201086824")
    private String commenter;

    @ApiModelProperty(value="留言者名字,系统生成",example="15201086824")
    private String commenterName;

    @ApiModelProperty(value="留言者ip,系统生成",example="15201086824")
    private String commenterIp;

    @ApiModelProperty(value="评论的产品id",example="7eb90bc4036145bc80dcbaba5176496c")
    private String commentProductId;

    @ApiModelProperty(value="评论的产品名字",example="产品1")
    private String commentProductName;

    @ApiModelProperty(value="评论的工具id",example="")
    private String commentToolId;

    @ApiModelProperty(value="评论的工具名字",example="")
    private String commentToolName;

    @ApiModelProperty(value="留言类型,字典值包括界面，易用性，功能和应用场景",example="界面")
    private String commentType;

    @ApiModelProperty(value="留言内容",example="界面太白")
    private String commentContent;

    @ApiModelProperty(value="留言者的操作系统,字典值包括但不限于windows，linux，android,ios,mac_os",example="windows")
    private String commenterOs;

    @ApiModelProperty(value="留言者的操作系统版本",example="windows98")
    private String commenterOsVersion;

    @ApiModelProperty(value="留言者的操作系统位数，字典值包括64或32",example="32")
    private String commenterOsBit;

    @ApiModelProperty(value="留言者的网站版本，目前1.0",example="1.0")
    private String commentWebsitVersion;

    @ApiModelProperty(value="留言者的浏览器类型，字典值包括chrome, firefox，safari，ie,360，qq",example="chrome")
    private String commenterBrowserType;

    @ApiModelProperty(value="留言者的浏览器版本",example="ie9")
    private String commenterBrowserVersion;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="留言时间，系统生成",dataType="Date",example="2019-06-05 10:25:30")
    private Date commentTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter == null ? null : commenter.trim();
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName == null ? null : commenterName.trim();
    }

    public String getCommenterIp() {
        return commenterIp;
    }

    public void setCommenterIp(String commenterIp) {
        this.commenterIp = commenterIp == null ? null : commenterIp.trim();
    }

    public String getCommentProductId() {
        return commentProductId;
    }

    public void setCommentProductId(String commentProductId) {
        this.commentProductId = commentProductId == null ? null : commentProductId.trim();
    }

    public String getCommentProductName() {
        return commentProductName;
    }

    public void setCommentProductName(String commentProductName) {
        this.commentProductName = commentProductName == null ? null : commentProductName.trim();
    }

    public String getCommentToolId() {
        return commentToolId;
    }

    public void setCommentToolId(String commentToolId) {
        this.commentToolId = commentToolId == null ? null : commentToolId.trim();
    }

    public String getCommentToolName() {
        return commentToolName;
    }

    public void setCommentToolName(String commentToolName) {
        this.commentToolName = commentToolName == null ? null : commentToolName.trim();
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType == null ? null : commentType.trim();
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public String getCommenterOs() {
        return commenterOs;
    }

    public void setCommenterOs(String commenterOs) {
        this.commenterOs = commenterOs == null ? null : commenterOs.trim();
    }

    public String getCommenterOsVersion() {
        return commenterOsVersion;
    }

    public void setCommenterOsVersion(String commenterOsVersion) {
        this.commenterOsVersion = commenterOsVersion == null ? null : commenterOsVersion.trim();
    }

    public String getCommenterOsBit() {
        return commenterOsBit;
    }

    public void setCommenterOsBit(String commenterOsBit) {
        this.commenterOsBit = commenterOsBit == null ? null : commenterOsBit.trim();
    }

    public String getCommentWebsitVersion() {
        return commentWebsitVersion;
    }

    public void setCommentWebsitVersion(String commentWebsitVersion) {
        this.commentWebsitVersion = commentWebsitVersion == null ? null : commentWebsitVersion.trim();
    }

    public String getCommenterBrowserType() {
        return commenterBrowserType;
    }

    public void setCommenterBrowserType(String commenterBrowserType) {
        this.commenterBrowserType = commenterBrowserType == null ? null : commenterBrowserType.trim();
    }

    public String getCommenterBrowserVersion() {
        return commenterBrowserVersion;
    }

    public void setCommenterBrowserVersion(String commenterBrowserVersion) {
        this.commenterBrowserVersion = commenterBrowserVersion == null ? null : commenterBrowserVersion.trim();
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }
}