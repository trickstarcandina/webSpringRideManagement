package com.example.quanlychuyenxe.model.request;

import com.example.quanlychuyenxe.model.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class ChuyenXeRequest {
    private Integer id;
    private Long giaVe;
    private Date thoiGianKhoiHanh;
    private Date thoiGianKetThuc;
    private Integer soLuongHanhKhach = 0;
    private String xe_khach_bien_so;
    private Integer tuyen_xe_id;
    private String usernameLaiXe;
    private String usernamePhuXe;

    private Set<KhachHang> khachHangList;
}
