package dev.es.myasset.domain.shared;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    @Temporal(value = TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(value = TIMESTAMP)
    @Nullable
    private LocalDateTime updatedAt;
}
