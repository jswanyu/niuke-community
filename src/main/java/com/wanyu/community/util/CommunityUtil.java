package com.wanyu.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

public class CommunityUtil {

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");  // 不需要横线，只要字母数字
    }

    // MD5加密，只能加密，不能解密
    // hello -> abc123def456
    // hello + 3e4a8 -> abc123def456abc  加盐
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {   // commons.lang3中非常好用的判断空值
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());  // md5DigestAsHex将传入的参数加密为16进制
    }

    // 参数：编码、提示信息、业务数据map
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

}
