package com.amos.think.common.api;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * DESCRIPTION: BaseEntity
 */
@Getter
@Setter
@MappedSuperclass
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @CreatedDate
    private LocalDateTime createTime;

    @CreatedBy
    private String creator;

    @LastModifiedDate
    private LocalDateTime modifyTime;

    @LastModifiedBy
    private String modifier;

}