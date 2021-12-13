package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
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
public class XeKhach extends BaseEntity {
    @NotEmpty(message = "Thiếu biển số")
    private String bienSo;
    @NotEmpty(message = "Thiếu tên xe khách")
    private String tenXeKhach;
    @NotEmpty(message = "Thiếu màu xe")
    private String mauXe;
    @NotEmpty(message = "Thiếu hãng sản xuất")
    private String hangSanXuat;
    @NotNull(message = "Thiếu đời xe")
    private Integer doiXe; //2018
    @NotEmpty(message = "Thiếu model")
    private String model;
    @NotNull(message = "Thiếu số ghế")
    private Integer soGhe;
    @NotNull(message = "Thiếu số năm sử dụng")
    private Integer soNamSuDung;

    @NotNull(message = "Thiếu ngày bảo dưỡng cuối cùng")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")

    private Date ngayBaoDuongCuoiCung;

    private String username;
    private String password;

    private Boolean isEdit = false;
}
