package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.TongLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TongLuongRepository extends JpaRepository<TongLuong, Integer> {
}
