package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, String>{
    List<KhachHang> findByTenContaining(String ten);

    @Query(value = "select count(*) from khachhang where username = ?", nativeQuery = true)
    Integer checkUserNameExists(String username);
}