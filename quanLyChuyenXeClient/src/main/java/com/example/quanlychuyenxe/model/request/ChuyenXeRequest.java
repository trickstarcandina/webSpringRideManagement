package com.example.quanlychuyenxe.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ChuyenXeRequest {
    @NotNull(message = "Giá vé không được để trống")
    private Long giaVe;
//    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date thoiGianKhoiHanh;
//    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date thoiGianKetThuc;
    private Integer soLuongHanhKhach = 0;
    private String xe_khach_bien_so;
    private Integer tuyen_xe_id;
    private String cmtLaiXe;
    private String cmtPhuXe;
}
