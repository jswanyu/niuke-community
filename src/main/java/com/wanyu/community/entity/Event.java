package com.wanyu.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname: Event
 * @author: wanyu
 * @Date: 2022/6/19 16:57
 */
public class Event {

    private String topic; // 事件类型：点赞，关注，评论
    private int userId;
    private int entityType;
    private int entityId;
    private int entityUserId;
    private Map<String, Object> data = new HashMap<>();  // 为了扩展性，将其他的属性放到map里

    public String getTopic() {
        return topic;
    }

    // set方法把事件对象返回，可以对事件对象进一步设置属性（链式结构）。而不用对每种字段组合都设置构造函数
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    // 这里传入的不是map，而是key和value，set方法里put进map
    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
