package com.boco.eoms.websit.systemuser.model;

import com.boco.eoms.base.util.StaticMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author ssh
 * @description:用户对象
 * @date 2019/10/3115:15
 */
@ApiModel(value="用户表")
public class EomsSystemUser {

    @ApiModelProperty(value="主键id,系统生成",example="7eb90bc4036145bc80dcbaba5176496c")
    private String id;

    @ApiModelProperty(value="用户账号",example="ceshi")
    private String userId;

    @ApiModelProperty(value="用户名称",example="测试者")
    private String userName;

    @ApiModelProperty(value="用户密码",example="ceshi")
    private String password;

    @ApiModelProperty(value="联系电话",example="15201894231")
    private String mobile;

    @ApiModelProperty(value="性别，字典值包括男，和女",example="男")
    private String gender;

    @ApiModelProperty(value="邮箱",example="945252687@qq.com")
    private String email;

    @ApiModelProperty(value="状态,normal启用,disabled禁用,deleted删除",example="normal",hidden=true)
    private String status;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="创建时间,系统生成",dataType="Date",example="2019-06-05 10:25:30")
    private Date createdTime;

    @JsonFormat(pattern = StaticMethod.DATE_FORMAT)
    @ApiModelProperty(value="修改时间,系统生成",dataType="Date",example="2019-06-05 10:25:30")
    private Date updateTime;

    @ApiModelProperty(value="qq号",example="945252687")
    private String qqNumber;

    @ApiModelProperty(value="工作类别，字典值包括学生,研发,产品,运营,管理,采购,行政",example="学生")
    private String workType;

    @ApiModelProperty(value="网站版本",example="1.0")
    private String websitVersion;

    @ApiModelProperty(value="访问者昵称",example="奔走的小草")
    private String visitorNickName;

    @ApiModelProperty(value="短信验证码",example="413221")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWebsitVersion() {
        return websitVersion;
    }

    public void setWebsitVersion(String websitVersion) {
        this.websitVersion = websitVersion;
    }

    public String getVisitorNickName() {
        return visitorNickName;
    }

    public void setVisitorNickName(String visitorNickName) {
        this.visitorNickName = visitorNickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
