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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Afanasev E.V.
 * @version 1.0 5/21/2021
 */
@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = 8760217578528795976L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq_gen")
    @SequenceGenerator(name = "message_seq_gen", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
    private long id;

    @Column
    private long userId;

    @Column(name = "MESSAGE_DATE")
    private Instant messageDate;

    @Column
    private String text;

}
