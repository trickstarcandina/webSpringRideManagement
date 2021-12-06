package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chuyenxe")
@Setter
@Getter
@Data
public class ChuyenXe extends BaseEntity {
//    private String maSoChuyenXe;
    private Integer soKhach;
    private Long giaVe;
    private Date thoiGianKhoiHanh;
    private Date thoiGianKetThuc;
    private Integer soLuongHanhKhach;

//    private String bienSo;
//    private String maSoTuyenXe;
//    private String cmtLaiXe;
//    private String cmtPhuXe;

    @ManyToOne
    private XeKhach xeKhach;
    @ManyToOne
    private TuyenXe tuyenXe;
    @ManyToOne
    private TaiXe taiXe1;
    @ManyToOne
    private TaiXe taiXe2;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "khachhang_chuyenxe",
            joinColumns = @JoinColumn(name = "chuyenxe_id"),
            inverseJoinColumns = @JoinColumn(name = "cmtKhachHang"))
    private List<KhachHang> khachHangList;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tongluong",
//            joinColumns = @JoinColumn(name = "chuyenxe_id"),
//            inverseJoinColumns = @JoinColumn(name = "luongcoban_id"))
//    private List<LuongCoBan> luongCoBanList;
}
