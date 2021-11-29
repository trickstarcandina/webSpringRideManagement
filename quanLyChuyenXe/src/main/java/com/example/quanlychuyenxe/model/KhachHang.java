package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "khachhang")
@Setter
@Getter
@Data
public class KhachHang extends BaseEntityUser {
    @Id
    private String cmtKhachHang;
    private String ten;
    private String diaChi;
    private Integer tuoi;
    private String ghiChu;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "khachhang_chuyenxe",
            joinColumns = @JoinColumn(name = "cmtKhachHang"),
            inverseJoinColumns = @JoinColumn(name = "chuyenxe_id"))
    private List<ChuyenXe> chuyenXeList;
}
