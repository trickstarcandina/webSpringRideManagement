package com.example.quanlychuyenxe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhachHang {
    @NotEmpty(message = "Thiếu cmt tài xế")
    private String cmtKhachHang;
    @NotEmpty(message = "Thiếu tên")
    private String ten;
    @NotEmpty(message = "Thiếu địa chỉ")
    private String diaChi;
    @NotNull(message = "Thiếu tuổi")
    private Integer tuoi;
    private String ghiChu;
    private String username;
    private String password;

    private List<ChuyenXe> chuyenXeList;

    private Boolean isEdit = false;
}
