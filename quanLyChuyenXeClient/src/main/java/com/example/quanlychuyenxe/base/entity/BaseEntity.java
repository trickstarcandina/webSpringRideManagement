package com.example.quanlychuyenxe.base.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity {
    protected Integer id;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;
}