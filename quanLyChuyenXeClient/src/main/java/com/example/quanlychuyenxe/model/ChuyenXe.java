package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChuyenXe extends BaseEntity {
//    private String maSoChuyenXe;
    private Long giaVe;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thoiGianKhoiHanh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thoiGianKetThuc;
    private Integer soLuongHanhKhach = 0;
    private Integer status;

    private XeKhach xeKhach;
    private TuyenXe tuyenXe;
    private TaiXe taiXe1;
    private TaiXe taiXe2;

    private List<KhachHang> khachHangList;

    private Boolean isEdit = false;
//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tongluong",
//            joinColumns = @JoinColumn(name = "chuyenxe_id"),
//            inverseJoinColumns = @JoinColumn(name = "luongcoban_id"))
//    private List<LuongCoBan> luongCoBanList;
}
