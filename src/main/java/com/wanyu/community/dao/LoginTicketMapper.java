package com.wanyu.community.dao;

import com.wanyu.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @Classname: LoginTicketMapper
 * @author: wanyu
 * @Date: 2022/5/12 16:51
 */

@Mapper
@Deprecated
public interface LoginTicketMapper {
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 主键自动生成，注入给id属性
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ", //演示用，没有实际价值
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);
}
