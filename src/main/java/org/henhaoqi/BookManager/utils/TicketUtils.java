package org.henhaoqi.BookManager.utils;

import org.henhaoqi.BookManager.entity.Ticket;
import org.joda.time.DateTime;

public class TicketUtils {
    public static Ticket next(int uid){

        Ticket ticket = new Ticket();
        ticket.setTicket(UuidUtils.next());
        ticket.setUserId(uid);
        // 设置 ticket 过期时间
        DateTime expiredTime = new DateTime();
        expiredTime = expiredTime.plusMonths(3);
        ticket.setExpiredAt(expiredTime.toDate());

        return ticket;
    }
}
