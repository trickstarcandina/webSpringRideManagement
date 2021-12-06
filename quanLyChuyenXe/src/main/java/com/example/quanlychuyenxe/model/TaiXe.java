package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private Date ngaySinh;
    private Integer thamNien;
}
