package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChuyenXe extends BaseEntity {
//    private String maSoChuyenXe;
    @NotNull(message = "Giá vé không được để trống")
    private Long giaVe;
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thoiGianKhoiHanh;
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thoiGianKetThuc;
    private Integer soLuongHanhKhach = 0;
    private Integer status;

    private XeKhach xeKhach;
    private TuyenXe tuyenXe;
    private TaiXe taiXe1;
    private TaiXe taiXe2;

    private Set<KhachHang> khachHangList;

    private Boolean isEdit = false;
}
