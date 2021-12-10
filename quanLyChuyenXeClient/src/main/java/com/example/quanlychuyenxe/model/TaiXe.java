package com.example.quanlychuyenxe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaiXe {
    @NotEmpty(message = "Thiếu cmt tài xế")
    private String cmtTaiXe;
    @NotEmpty(message = "Thiếu tên")
    private String ten;
    @NotEmpty(message = "Thiếu mã số bằng lái")
    private String maSoBangLai;
    @NotEmpty(message = "Thiếu loại bằng lái")
    private String loaiBangLai;
    @NotEmpty(message = "Thiếu địa chỉ")
    private String diaChi;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date ngaySinh;

    @NotNull(message = "Thiếu thâm niên")
    private Integer thamNien;
    @NotEmpty(message = "Thiếu username")
    private String username;
    @NotEmpty(message = "Thiếu password")
    private String password;

    private Boolean isEdit = false;
}
