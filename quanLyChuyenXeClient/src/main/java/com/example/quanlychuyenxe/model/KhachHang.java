package com.example.quanlychuyenxe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KhachHang {
    @NotEmpty(message = "Thiếu tên")
    private String ten;
    @NotEmpty(message = "Thiếu địa chỉ")
    private String diaChi;
    @NotNull(message = "Thiếu tuổi")
    private Integer tuoi;
    private String ghiChu;
    @NotEmpty(message = "Thiếu username")
    private String username;
    @NotEmpty(message = "Thiếu password")
    private String password;

    private Set<ChuyenXe> chuyenXeList;

    private Boolean isEdit = false;
}
