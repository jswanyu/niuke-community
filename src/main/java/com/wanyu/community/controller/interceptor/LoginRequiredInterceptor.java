package com.wanyu.community.controller.interceptor;

import com.wanyu.community.annotation.LoginRequired;
import com.wanyu.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Classname: LoginRequiredInterceptor
 * @author: wanyu
 * @Date: 2022/5/15 22:47
 */

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder; // 根据HostHolder能否获取到对象来判断用户是否登录成功

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 首先判断拦截的对象Object handler是不是一个方法，我们这里只拦截方法，HandlerMethod是mvc提供的代表方法的对象
        if (handler instanceof HandlerMethod) {
            // 既然是方法，就把他转成方法，更好的调用api，而不是用一开始的普通对象Object handler
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod(); // 这个代表方法的对象，能够获取到被拦截的具体是什么方法
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class); // 尝试去获取被拦截方法的注解，此处只获取我们所需要的@LoginRequired
            // 如果loginRequired不为空，说明被拦截方法加了@LoginRequired注解，并且没获取到登录的用户，即用户登录没成功，那就拦截这个请求
            if (loginRequired != null && hostHolder.getUser() == null) {
                // 拒绝之后得告诉他去哪，此处直接重定向至登录页面，意思就是像访问这个就去登陆
                response.sendRedirect(request.getContextPath() + "/login");  // 注意response重定向的写法，这里不能直接用mvc的return "redirect:...";
                return false; // 返回false，即为拒绝后续的请求，即拦截请求
            }
        }
        return true;
    }
}
