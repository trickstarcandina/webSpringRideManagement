package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "xekhach")
@Setter
@Getter
@Data
public class XeKhach {
    @Id
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
