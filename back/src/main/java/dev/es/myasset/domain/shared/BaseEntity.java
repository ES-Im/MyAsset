package dev.es.myasset.domain.shared;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@SuppressWarnings("unused")
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity extends AbstractEntity {

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @Nullable
    @LastModifiedDate
    private Instant updatedAt;
}
