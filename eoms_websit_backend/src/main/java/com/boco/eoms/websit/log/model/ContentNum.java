package com.boco.eoms.websit.log.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ssh
 * @description:
 * @date 2019/11/159:38
 */
@ApiModel(value="模块访问数量对象")
public class ContentNum {

    @ApiModelProperty(value="模块类型，包括详情和试用",example="试用")
    private String contentType;

    @ApiModelProperty(value="详情和试用的访问次数",example="20")
    private String visitNum;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(String visitNum) {
        this.visitNum = visitNum;
    }
}
