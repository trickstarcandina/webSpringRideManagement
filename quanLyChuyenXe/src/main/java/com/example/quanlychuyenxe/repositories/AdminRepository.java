package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.Admin;
import com.example.quanlychuyenxe.model.TaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, String> {

    @Query(value = "select count(*) from admin where username = ? and password = ? and role = '1'", nativeQuery = true)
    Integer checkExist(String username, String password);

    @Query(value = "select display_name from admin where username = ? and password = ? and role = '1'", nativeQuery = true)
    String findDisplayName(String username, String password);
}
