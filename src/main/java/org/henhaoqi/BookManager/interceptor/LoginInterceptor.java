package org.henhaoqi.BookManager.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.henhaoqi.BookManager.entity.Ticket;
import org.henhaoqi.BookManager.service.TicketService;
import org.henhaoqi.BookManager.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception{

        // 没有 ticket， 去登录
        String t = CookieUtils.getCookie("t", request);
        if(StringUtils.isEmpty(t)){
            response.sendRedirect("/users/login");
            return false;
        }

        // 无效 ticket，去登录
        Ticket ticket = ticketService.getTicket(t);
        if(ticket == null){
            response.sendRedirect("/users/login");
            return false;
        }

        // 过期 ticket，去登录
        if(ticket.getExpiredAt().before(new Date())){
            response.sendRedirect("/users/login");
            return false;
        }
        return true;
    }
}
