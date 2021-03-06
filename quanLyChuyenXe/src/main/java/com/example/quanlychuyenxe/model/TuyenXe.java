package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tuyenxe")
@Setter
@Getter
@Data
public class TuyenXe extends BaseEntity {
    private String diemDau;
    private String diemCuoi;
    private Integer doDai;
    private Integer doPhucTapCuaTuyenDuong; // 1 dễ, 2 vừa, 3 khó
}
