package com.example.quanlychuyenxe.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class TaiXe {
    private String cmtTaiXe;
    private String ten;
    private String maSoBangLai;
    private String loaiBangLai;
    private String diaChi;
    private Date ngaySinh;
    private Integer thamNien;
    private String username;
    private String password;
}
