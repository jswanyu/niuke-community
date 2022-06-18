package com.wanyu.community.dao;

import com.wanyu.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname: MessageMapper
 * @author: wanyu
 * @Date: 2022/5/20 14:50
 */
@Mapper
public interface MessageMapper {

    // 查询当前用户的所有会话列表,针对每个会话只返回一条最新的私信。这里的sql语句注意理解
    List<Message> selectConversations(int userId, int offset, int limit);

    // 查询当前用户的所有会话数量。这里的sql语句注意理解
    int selectConversationCount(int userId);

    // 查询某个会话所包含的私信列表。这里的sql语句注意理解
    List<Message> selectLetters(String conversationId, int offset, int limit);

    // 查询某个会话所包含的私信数量。这里的sql语句注意理解
    int selectLetterCount(String conversationId);

    // 查询未读私信的数量。这里的sql语句注意理解
    int selectLetterUnreadCount(int userId, String conversationId);

    // 新增消息
    int insertMessage(Message message);

    // 修改消息的状态
    int updateStatus(List<Integer> ids, int status);

}
