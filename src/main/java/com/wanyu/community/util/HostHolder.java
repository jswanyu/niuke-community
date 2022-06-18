package com.wanyu.community.util;

/**
 * @Classname: HostHolder
 * @author: wanyu
 * @Date: 2022/5/15 14:41
 */

import com.wanyu.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息,用于代替session对象.
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);  // 调用ThreadLocal的set方法，它是根据当前线程把值放到一个map里，不同的线程有不同的map
    }

    public User getUser() {
        return users.get();  // 调用ThreadLocal的get方法，取也是根据当前线程取
    }

    // 请求结束后就把所有的map清理掉，要不然光存不清理，占用资源
    public void clear() {
        users.remove();
    }
}

