package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, String>{
    List<KhachHang> findByTenContaining(String ten);

    @Query(value = "select count(*) from khachhang where username = ?", nativeQuery = true)
    Integer checkUserNameExists(String username);

    @Modifying
    @Query(value = "UPDATE khachhang SET password = ?, dia_chi = ?, ghi_chu = ?," +
            " ten = ?, tuoi = ? WHERE (username = ?)", nativeQuery = true)
    void updateUser(String password, String diaChi, String ghiChu, String ten, Integer tuoi, String username);
}