package com.example.quanlychuyenxe.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class KhachHangRequest {
    @NotEmpty(message = "Thiếu tên")
    private String ten;
    @NotEmpty(message = "Thiếu địa chỉ")
    private String diaChi;
    @NotNull(message = "Thiếu tuổi")
    private Integer tuoi;
    @NotEmpty(message = "Thiếu username")
    private String username;
    @NotEmpty(message = "Thiếu password")
    private String password;
}
