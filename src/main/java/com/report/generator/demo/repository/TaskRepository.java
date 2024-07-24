/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.report.generator.demo.repository;

import com.report.generator.demo.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Afanasev E.V.
 * @version 1.0 5/12/2021
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task getFirstByStatus(String status);
}
