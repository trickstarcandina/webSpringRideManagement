package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@Setter
@Getter
@Data
public class Admin extends BaseEntityUser {
    private String displayName;
}
