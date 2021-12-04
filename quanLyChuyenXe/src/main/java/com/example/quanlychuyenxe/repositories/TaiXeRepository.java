package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.TaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaiXeRepository extends JpaRepository<TaiXe, String> {
    List<TaiXe> findByTenContaining(String ten);
}
