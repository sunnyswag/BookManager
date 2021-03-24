package org.henhaoqi.BookManager.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.henhaoqi.BookManager.entity.Ticket;
import org.henhaoqi.BookManager.entity.User;
import org.henhaoqi.BookManager.service.TicketService;
import org.henhaoqi.BookManager.service.UserService;
import org.henhaoqi.BookManager.utils.ConcurrentUtils;
import org.henhaoqi.BookManager.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class HostInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    /**
     * 始终返回 true，
     * 从 Cookie 中找到相应的用户自动登录
     * Cookie 中没有相应的用户也没事
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception{
        String t = CookieUtils.getCookie("t", request);
        if(!StringUtils.isEmpty(t)){
            Ticket ticket = ticketService.getTicket(t);
            if(ticket != null && ticket.getExpiredAt().after(new Date())){
                User host = userService.getUser(ticket.getUserId());
                ConcurrentUtils.setHost(host);
            }
        }
        return true;
    }
}
