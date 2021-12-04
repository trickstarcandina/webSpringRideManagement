package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class XeKhach extends BaseEntity {
    private String bienSo;
    private String mauXe;
    private String hangSanXuat;
    private Integer doiXe; //2018
    private String model;
    private Integer soGhe;
    private Integer soNamSuDung;
    private Date ngayBaoDuongCuoiCung;
}