package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.ChuyenXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChuyenXeRepository extends JpaRepository<ChuyenXe, Integer> {
}
