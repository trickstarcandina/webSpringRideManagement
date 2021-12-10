package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chuyenxe")
@Setter
@Getter
@Data
public class ChuyenXe extends BaseEntity {

    private Long giaVe;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thoiGianKhoiHanh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thoiGianKetThuc;

    private Integer soLuongHanhKhach;
    private Integer status; // 0 - chưa đủ tài xế; 1 - đã đủ tài xế; 2 - đang di chuyển; 3 - đã kết thúc
    //note ở trạng thái 0,1 thì khách hàng có thể mua đc vé

//    private String bienSo;
//    private String maSoTuyenXe;
//    private String cmtLaiXe;
//    private String cmtPhuXe;

    @ManyToOne
    private XeKhach xeKhach;
    @ManyToOne
    private TuyenXe tuyenXe;
    @ManyToOne
    private TaiXe taiXe1;
    @ManyToOne
    private TaiXe taiXe2;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
//    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "khachhang_chuyenxe",
            joinColumns = @JoinColumn(name = "chuyenxe_id"),
            inverseJoinColumns = @JoinColumn(name = "khach_hang_username"))
    private Set<KhachHang> khachHangList;


//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tongluong",
//            joinColumns = @JoinColumn(name = "chuyenxe_id"),
//            inverseJoinColumns = @JoinColumn(name = "luongcoban_id"))
//    private List<LuongCoBan> luongCoBanList;
}
