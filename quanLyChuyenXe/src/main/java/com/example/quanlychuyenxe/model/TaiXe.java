package com.example.quanlychuyenxe.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaiXe {
    private String cmtTaiXe;
    private String ten;
    private String maSoBangLai;
    private String loaiBangLai;
    private String diaChi;
    private Date ngaySinh;
    private Integer thamNien;
}
