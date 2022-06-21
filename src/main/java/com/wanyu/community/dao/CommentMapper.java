package com.wanyu.community.dao;

import com.wanyu.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname: CommentMapper
 * @author: wanyu
 * @Date: 2022/5/19 20:16
 */

@Mapper
public interface CommentMapper {
    // 根据评论目标类别与目标id完成分页查询
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);
    // 评论的数量
    int selectCountByEntity(int entityType, int entityId);
    // 添加评论
    int insertComment(Comment comment);
    // 根据id查询帖子的用户
    Comment selectCommentById(int id);
}
