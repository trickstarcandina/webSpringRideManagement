package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class XeKhach {
    private String bienSo;
    private String tenXeKhach;
    private String mauXe;
    private String hangSanXuat;
    private Integer doiXe; //2018
    private String model;
    private Integer soGhe;
    private Integer soNamSuDung;
    private Date ngayBaoDuongCuoiCung;
}
