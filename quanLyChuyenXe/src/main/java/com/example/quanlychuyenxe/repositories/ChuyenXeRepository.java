package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.ChuyenXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChuyenXeRepository extends JpaRepository<ChuyenXe, Integer> {
    List<ChuyenXe> findAllByTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContaining(String diemDau, String diemCuoi);

    List<ChuyenXe> findByTaiXe1_CmtTaiXeAndStatus(String cmtTaiXe, Integer status);

    @Query(value = "SELECT * FROM chuyenxe WHERE status = ? AND (tai_xe1_username = ? OR tai_xe2_username=?)", nativeQuery = true)
    List<ChuyenXe> searchTaiXeAndStatus(Integer status, String usernameLaiXe, String usernamePhuXe);

    List<ChuyenXe> findAllByTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContainingAndStatus(String diemDau, String diemCuoi, Integer status);

    @Modifying
    @Query(value = "UPDATE chuyenxe SET status = ? WHERE (id = ?)", nativeQuery = true)
    void updateStatusById(Integer status, Integer id);

    List<ChuyenXe> findAllByTaiXe1NullOrTaiXe2NullAndTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContaining(String diemDau, String diemCuoi);

    @Modifying
    @Query(value = "UPDATE chuyenxe SET tai_xe1_username = ? WHERE (id = ?)", nativeQuery = true)
    void updateLaiXeById(String username, Integer id);

    @Modifying
    @Query(value = "UPDATE chuyenxe SET tai_xe2_username = ? WHERE (id = ?)", nativeQuery = true)
    void updatePhuXeById(String username, Integer id);
}
