package com.wanyu.community.controller;

import com.wanyu.community.entity.Comment;
import com.wanyu.community.service.CommentService;
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
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

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

        return "redirect:/discuss/detail/" + discussPostId;
    }
}
