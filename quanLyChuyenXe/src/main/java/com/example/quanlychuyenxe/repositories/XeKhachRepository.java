package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.model.XeKhach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface XeKhachRepository extends JpaRepository<XeKhach, String> {

    @Query(value = "select * from xekhach", nativeQuery = true)
    Page<TuyenXe> findAllTuyenXe(Pageable pageable);

    List<XeKhach> findByTenXeKhachContaining(String tenxekhach);
}
