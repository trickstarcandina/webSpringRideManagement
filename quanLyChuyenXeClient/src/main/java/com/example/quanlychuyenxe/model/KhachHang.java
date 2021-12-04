package com.example.quanlychuyenxe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhachHang {
    private String cmtKhachHang;
    private String ten;
    private String diaChi;
    private Integer tuoi;
    private String ghiChu;
    private String username;
    private String password;

    private List<ChuyenXe> chuyenXeList;

    private Boolean isEdit = false;
}
