package com.wanyu.community.dao;

import com.wanyu.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 社区首页就是查询帖子，因为是分页查询，所以返回的是一个帖子的集合
    // 传入了一个userId是因为考虑到以后开发功能：查询我自己账户里的帖子，即根据账户id查询帖子。所以当用户id为0，可以不把id拼到sql里
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // 查询帖子一共有多少行，方便分页
    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

    // 增加帖子的方法
    int insertDiscussPost(DiscussPost discussPost);

    // 查询帖子
    DiscussPost selectDiscussPostById(int id);

    // 更新帖子的评论数量
    int updateCommentCount(int id, int commentCount);

}
