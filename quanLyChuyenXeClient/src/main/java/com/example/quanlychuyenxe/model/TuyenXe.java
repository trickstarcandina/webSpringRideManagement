package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TuyenXe extends BaseEntity {
    private String diemDau;
    private String diemCuoi;
    private Integer doDai;
    private Integer doPhucTapCuaTuyenDuong; // 1 dễ, 2 vừa, 3 khó
}
