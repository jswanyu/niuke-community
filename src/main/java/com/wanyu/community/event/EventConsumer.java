package com.wanyu.community.event;

import com.alibaba.fastjson.JSONObject;
import com.wanyu.community.entity.Event;
import com.wanyu.community.entity.Message;
import com.wanyu.community.service.MessageService;
import com.wanyu.community.util.CommunityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname: EventConsumer
 * @author: wanyu
 * @Date: 2022/6/19 17:09
 */

@Slf4j
@Component
public class EventConsumer implements CommunityConstant {

    @Autowired
    private MessageService messageService;

    // kafka监听3个主题：点赞、关注、评论。 虽然也可以每个方法监听一个主题，但由于这里处理的方式类似，合起来写
    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record) {  // 消费者接受的参数类型：ConsumerRecord
        // 对于第三方包的对象，要习惯判定是否为空
        if (record == null || record.value() == null) {
            log.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            log.error("消息格式错误!");
            return;
        }

        // 发送站内通知：表现形式就是往message表里插入记录，建议写之前再看下表结构
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);  // 系统用户id固定为1，用于发送系统消息
        message.setToId(event.getEntityUserId());
        // ConversationId之前是两个用户之间的id，现在可以换为三个主题：点赞、关注、评论
        message.setConversationId(event.getTopic());
        message.setCreateTime(new Date());

        // 内容content之前是存储用户发送私信的文本，现在存储 用于拼出系统通知的各个组成部分
        // 比如通知：用户xxx评论（回复）了你的xxx帖子（xxx评论）
        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());
        // event里其他的属性我们封装到了一个map里，现在表里也没有其他合适的字段放了，因此我们统一也放到content里
        if (!event.getData().isEmpty()) {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        message.setContent(JSONObject.toJSONString(content));

        // 调用私信业务层方法
        messageService.addMessage(message);
    }
}
