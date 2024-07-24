/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.report.generator.demo.service;

import com.report.generator.demo.repository.TaskRepository;
import com.report.generator.demo.repository.entity.MessageData;
import com.report.generator.demo.repository.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Objects;

import static com.report.generator.demo.repository.entity.MessageData.MESSAGE_REF_QUERY_NAME;
import static com.report.generator.demo.utils.TaskStatus.CREATED;
import static com.report.generator.demo.utils.TaskStatus.DONE;
import static com.report.generator.demo.utils.TaskStatus.FAIL;
import static com.report.generator.demo.utils.TaskStatus.IN_PROGRESS;
import static org.hibernate.cfg.AvailableSettings.JPA_SHARED_CACHE_RETRIEVE_MODE;
import static org.hibernate.cfg.AvailableSettings.JPA_SHARED_CACHE_STORE_MODE;


/**
 * @author Afanasev E.V.
 * @version 1.0 5/7/2021
 */
@Service
@Slf4j
public class Generator {

    @PersistenceContext
    private EntityManager em;

    private final TaskRepository taskRepository;

    private SessionFactory sessionFactory;

    public Generator(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void initSessionFactory() {
        Session session = em.unwrap(Session.class);
        sessionFactory = session.getSessionFactory();
    }

    @Async("taskScheduler")
    @Scheduled(fixedDelay = 600)
    public void scheduledTask() {
        log.info("scheduledTask is started");
        Task task = getTask();
        if (Objects.isNull(task)) {
            log.info("task not found");
            return;
        }
        log.info("task found");
        generate(task);
    }

    @Transactional
    Task getTask() {
        Task task = taskRepository.getFirstByStatus(CREATED.toString());
        if (Objects.isNull(task)) {
            return null;
        }
        task.setStatus(IN_PROGRESS.toString());
        return taskRepository.save(task);
    }

    @Transactional
    void generate(Task task) {
        log.info("generating report is started");
        try (
            StatelessSession statelessSession = sessionFactory.openStatelessSession()
        ) {
//            ReportExcelWriter writer = new ReportExcelWriter();
            ReportExcelStreamWriter writer = new ReportExcelStreamWriter();
            Query<MessageData> query = statelessSession.createNamedQuery(MESSAGE_REF_QUERY_NAME, MessageData.class);
            query.setParameter(1, task.getDateFrom());
            query.setParameter(2, task.getDateTo());
            query.setHint(JPA_SHARED_CACHE_STORE_MODE, null);
            query.setHint(JPA_SHARED_CACHE_RETRIEVE_MODE, null);
            ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
            int index = 0;
            while (results.next()) {
                index++;
                writer.createRow(index, (MessageData) results.get(0));
                if (index % 100000 == 0) {
                    log.info("progress {} rows", index);
                }
            }
            writer.writeWorkbook();
            task.setStatus(DONE.toString());
            log.info("task {} complete", task);
        } catch (Exception e) {
            task.setStatus(FAIL.toString());
            e.printStackTrace();
            log.error("an error occurred with message {}. While executing the task {}", e.getMessage(), task);
        } finally {
            taskRepository.save(task);
        }
    }

}
