package com.team.gameblog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Timestamped {

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column
    private  LocalDateTime modifiedAt;


}
