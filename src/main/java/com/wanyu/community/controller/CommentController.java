package com.wanyu.community.controller;

import com.wanyu.community.entity.Comment;
import com.wanyu.community.entity.DiscussPost;
import com.wanyu.community.entity.Event;
import com.wanyu.community.event.EventProducer;
import com.wanyu.community.service.CommentService;
import com.wanyu.community.service.DiscussPostService;
import com.wanyu.community.util.CommunityConstant;
import com.wanyu.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @Classname: CommentController
 * @author: wanyu
 * @Date: 2022/5/20 13:16
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;

    // 增加完评论后，我们希望它能回到当前帖子页面，所以需要传进来帖子id
    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment) {
        // 登录才能评论
        if(hostHolder.getUser()==null){
            return "redirect:/login";
        }
        comment.setUserId(hostHolder.getUser().getId()); // 需要得到当前用户的id
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        // 触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", discussPostId);                   // 帖子id放到名为data的map里，通知消息里是写的谁评论了你，那么应该跳转到具体哪条帖子上
        if (comment.getEntityType() == ENTITY_TYPE_POST) {           // 可能是帖子的评论，添加该实体的用户id
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) { // 也可能是评论的评论，即回复
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);

        return "redirect:/discuss/detail/" + discussPostId;
    }
}
