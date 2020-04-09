package com.boco.eoms.websit.comment.mapper;

import com.boco.eoms.websit.comment.model.EomsWebsitComment;

import java.util.List;

/**
* @description: 留言的mapper
* @author ssh
* @date 2019/12/3 9:58
*/
public interface EomsWebsitCommentMapper {

    int insert(EomsWebsitComment record);

    /**
     * 留言信息查询
     * @return
     */
    List<EomsWebsitComment> findCommentList();
}