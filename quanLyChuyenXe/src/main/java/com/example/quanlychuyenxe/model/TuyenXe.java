package com.example.quanlychuyenxe.model;

import lombok.Data;

@Data
public class TuyenXe {
    private String maSoTuyenXe;
    private String diemDau;
    private String diemCuoi;
    private Integer doDai;
    private Integer doPhucTapCuaTuyenDuong; // 1 dễ, 2 vừa, 3 khó
}
