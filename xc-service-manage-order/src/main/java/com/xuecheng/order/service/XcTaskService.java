package com.xuecheng.order.service;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.order.dao.XcTaskHisRepository;
import com.xuecheng.order.dao.XcTaskRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @FileName: XcTaskService
 * @Author: DELL
 * @Date: 2019/9/15 7:30
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class XcTaskService {
    @Autowired
    private XcTaskRepository xcTaskRepository;

    @Autowired
    private XcTaskHisRepository xcTaskHisRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<XcTask> findTaskList (Date updateTime, int size) {
        Pageable pageable = PageRequest.of(0, size);
        Page<XcTask> page = xcTaskRepository.findByUpdateTimeBefore(updateTime, pageable);
        return page.getContent();
    }

    public void publish (XcTask xcTask) {
        rabbitTemplate.convertAndSend(xcTask.getMqExchange(), xcTask.getMqRoutingkey(), xcTask);
        xcTask.setUpdateTime(new Date());
        xcTaskRepository.save(xcTask);
    }

    @Transactional(rollbackFor = Exception.class)
    public int testTaskIsEnable(XcTask xcTask){
        return xcTaskRepository.updateTaskVersion(xcTask.getId(),xcTask.getVersion());
    }

    @Transactional(rollbackFor = Exception.class)
    public void finishTask(XcTask xcTask){
        xcTask.setDeleteTime(new Date());
        XcTaskHis xcTaskHis = new XcTaskHis();
        BeanUtils.copyProperties(xcTask,xcTaskHis);
        xcTaskRepository.delete(xcTask);
        xcTaskHisRepository.save(xcTaskHis);
    }

}
