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
import org.hibernate.annotations.CacheModeType;
import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

import static com.report.generator.demo.repository.entity.MessageData.MESSAGE_REF_QUERY_NAME;

/**
 * @author Afanasev E.V.
 * @version 1.0 5/12/2021
 */
@Data
@Entity
@NamedNativeQuery(
    name = MESSAGE_REF_QUERY_NAME,
    query = "{ ? = call message_ref(?, ?) }",
    callable = true,
    cacheMode = CacheModeType.IGNORE,
    resultClass = MessageData.class
)
public class MessageData implements Serializable {

    public static final String MESSAGE_REF_QUERY_NAME = "MessageData.callMessageRef";

    private static final long serialVersionUID = -6780765638993961105L;

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private int rating;

    @Column(name = "MESSAGE_DATE")
    private Instant messageDate;

    @Column
    private String text;

    @Override
    public String toString() {
        return "MessageData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", rating=" + rating +
            ", date=" + messageDate +
            ", text='" + text + '\'' +
            '}';
    }
}
