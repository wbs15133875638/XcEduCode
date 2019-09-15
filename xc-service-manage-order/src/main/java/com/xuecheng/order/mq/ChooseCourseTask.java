package com.xuecheng.order.mq;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.config.RabbitMQConfig;
import com.xuecheng.order.service.XcTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @FileName: ChooseCourseTac
 * @Author: DELL
 * @Date: 2019/9/15 7:34
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Component
@Slf4j
public class ChooseCourseTask {

    @Autowired
    private XcTaskService xcTaskService;

    @Scheduled(fixedDelay = 60000)
    public void sendChooseCourseTask(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.MINUTE,-1);
        Date time = calendar.getTime();
        List<XcTask> taskList = xcTaskService.findTaskList(time, 100);
        for (XcTask xcTask : taskList) {
            int i = xcTaskService.testTaskIsEnable(xcTask);
            if(i>0){
                xcTaskService.publish(xcTask);
            }
        }
    }

    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE)
    public void receiveChooseCourse(XcTask xcTask){
        xcTaskService.finishTask(xcTask);
    }


}
