package com.wanyu.community.event;

import com.alibaba.fastjson.JSONObject;
import com.wanyu.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Classname: EventProducer
 * @author: wanyu
 * @Date: 2022/6/19 17:04
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event) {
        // 将事件发布到指定的主题，需要两个参数：发送的主题和发送的内容
        // 这里的内容可以把整个event对象都转成JSON字符串发过去，至于怎么处理这个对象的信息，让消费者决定
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

}
