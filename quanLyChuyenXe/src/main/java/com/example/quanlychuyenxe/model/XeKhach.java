package com.example.quanlychuyenxe.model;

import lombok.Data;

import java.util.Date;

@Data
public class XeKhach {
    private String bienSo;
    private String mauXe;
    private String hangSanXuat;
    private Integer doiXe; //2018
    private String model;
    private Integer soGhe;
    private Integer soNamSuDung;
    private Date ngayBaoDuongCuoiCung;
}
