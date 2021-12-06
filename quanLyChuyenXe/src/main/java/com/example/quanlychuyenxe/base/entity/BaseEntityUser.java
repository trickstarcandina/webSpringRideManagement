package com.example.quanlychuyenxe.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntityUser {
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @Column(unique = true)
    protected String username;

    protected String password;

    protected String role;
}
