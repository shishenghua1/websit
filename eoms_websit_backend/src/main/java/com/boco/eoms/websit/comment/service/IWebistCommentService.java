package com.boco.eoms.websit.comment.service;

import com.alibaba.fastjson.JSONObject;
import com.boco.eoms.websit.comment.model.EomsWebsitComment;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ssh
 * @description:网站留言service接口层
 * @date 2019/12/310:07
 */
public interface IWebistCommentService {

    JSONObject insert(EomsWebsitComment record) throws Exception;

    /**
     * 留言信息查询
     * @return
     */
    List<EomsWebsitComment> findCommentList();

    /**
     * 网站留言excel导出
     */
    void download(HttpServletResponse response)throws Exception;
}
