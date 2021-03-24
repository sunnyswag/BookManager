package org.henhaoqi.BookManager.biz;

import org.apache.commons.lang3.StringUtils;
import org.henhaoqi.BookManager.entity.Ticket;
import org.henhaoqi.BookManager.entity.User;
import org.henhaoqi.BookManager.entity.exceptions.LoginRegisterException;
import org.henhaoqi.BookManager.service.TicketService;
import org.henhaoqi.BookManager.service.UserService;
import org.henhaoqi.BookManager.utils.ConcurrentUtils;
import org.henhaoqi.BookManager.utils.MD5;
import org.henhaoqi.BookManager.utils.TicketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginBiz {
    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    /**
     * 登录逻辑，先检查邮箱和密码，然后更新 ticket
     * @param email
     * @param password
     * @return 返回最新的 ticket
     * @throws Exception
     */

    public String login(String email, String password) throws Exception{
        User user = userService.getUser(email);

        // 检查登录信息
        if(user == null)
            throw new LoginRegisterException("邮箱不存在");
        if(!StringUtils.equals(MD5.next(password), user.getPassword()))
            throw new LoginRegisterException(("密码不正确"));

        // 检查 ticket
        Ticket t = ticketService.getTicket(user.getId());

        // TODO 退出登录的时候不是删除了吗？
        // 如果没有 ticket，则生成一个
        if(t == null){
            t = TicketUtils.next(user.getId());
            ticketService.addTicket(t);
            return t.getTicket();
        } else if(t.getExpiredAt().before(new Date())){ // 已过期，则删除
            ticketService.deleteTicket(t.getId());
        }

        t = TicketUtils.next(user.getId());
        ticketService.addTicket(t);
        ConcurrentUtils.setHost(user);
        return t.getTicket();
    }

    /**
     * 用户退出登录，删除数据库中用户的 ticket
     * @param t
     */

    public void logout(String t){
        ticketService.deleteTicket(t);
    }

    public String register(User user) throws Exception{

        // 检查邮箱是否存在
        if(userService.getUser(user.getEmail()) != null){
            throw new LoginRegisterException("用户邮箱已经存在！");
        }

        // 加密密码
        String plain = user.getPassword();
        String md5 = MD5.next(plain);
        user.setPassword(md5);

        // 向数据库中添加用户
        userService.addUser(user);
        // 向数据库中添加 ticket
        Ticket ticket = TicketUtils.next(user.getId());
        ticketService.addTicket(ticket);

        // 放入 ThreadLocal 中
        ConcurrentUtils.setHost(user);
        return ticket.getTicket();
    }
}
