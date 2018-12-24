package com.dreamTimes.controller.commons.scheduler;

import com.dreamTimes.commons.Const;
import com.dreamTimes.service.IOrderService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CloseOrder {

    @Autowired
    IOrderService orderService;
    /**
     * 每隔一分钟检查订单的状态，关闭一小时之外没有付款的订单
     */
    @Scheduled(cron = "0 */1 * * * * *")
    public void closeOrder(){
        Integer hour = Const.CLOSE_TIME_SCHEDULER;
//        获取时间节点
        Date closeTime = DateUtils.addHours(new Date(),-hour);
        String time = com.dreamTimes.utils.DateUtils.dateToStr(closeTime);
        orderService.closeOrder(time);
    }
}
