package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngayBaoDuongCuoiCung;
}
