/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.report.generator.demo.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Afanasev E.V.
 * @version 1.0 5/12/2021
 */
@Entity
@Table
@Data
public class Task implements Serializable {

    private static final long serialVersionUID = -535665521031411214L;

    @Id
    private long id;

    @Column
    private String status;

    @Column(name = "DATE_FROM")
    private Instant dateFrom;

    @Column(name = "DATE_TO")
    private Instant dateTo;
}
