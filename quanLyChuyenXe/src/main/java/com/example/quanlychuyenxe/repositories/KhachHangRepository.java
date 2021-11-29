package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>{
}
