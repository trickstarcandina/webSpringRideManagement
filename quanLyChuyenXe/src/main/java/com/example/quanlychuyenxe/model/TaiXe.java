package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "taixe")
@Setter
@Getter
@Data
public class TaiXe extends BaseEntityUser {
    @Id
    private String cmtTaiXe;
    private String ten;
    private String maSoBangLai;
    private String loaiBangLai;
    private String diaChi;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date ngaySinh;

    private Integer thamNien;
    private String username;
    private String password;
}
