package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.request.TKTaiXeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
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

    @Modifying
    @Query(value = "UPDATE `quanlychuyenxe`.`chuyenxe` SET `gia_ve` = ?, `tai_xe1_username` = ?, " +
            "`tai_xe2_username` = ?, `tuyen_xe_id` = ?, `xe_khach_bien_so` = ? WHERE (`id` = ?)", nativeQuery = true)
    void updateChuyenXe(Long giave, String username1, String username2, Integer tuyenXeId, String bienSo, Integer id);

    @Modifying
    @Query(value = "INSERT INTO khachhang_chuyenxe (khach_hang_username, chuyenxe_id) VALUES (?, ?)", nativeQuery = true)
    void saveKhachHangChuyenXe(String username, Integer id);

    @Query(value = "SELECT COUNT(*) FROM quanlychuyenxe.chuyenxe WHERE thoi_gian_ket_thuc" +
            " BETWEEN ? AND ? AND status = 1", nativeQuery = true)
    Integer getChuyenXe(Date start, Date end);

    @Query(value = "SELECT taixe.ten AS ten, taixe.dia_chi AS diaChi, taixe.ngay_sinh AS ngaySinh, laixe, phuxe FROM quanlychuyenxe.taixe\n" +
            "LEFT JOIN\n" +
            "(\n" +
            "\tSELECT COUNT(*) AS laixe, tai_xe1_username FROM quanlychuyenxe.chuyenxe\n" +
            "\tgroup by tai_xe1_username\n" +
            ") AS A\n" +
            "ON taixe.username = A.tai_xe1_username\n" +
            "LEFT JOIN\n" +
            "(\n" +
            "\tSELECT COUNT(*) AS phuxe, tai_xe2_username FROM quanlychuyenxe.chuyenxe\n" +
            "\tgroup by tai_xe2_username\n" +
            ") AS B\n" +
            "ON taixe.username = B.tai_xe2_username\n" +
            "ORDER BY ifnull(A.laixe, 0) + ifnull(B.phuxe, 0) DESC\n" +
            "LIMIT 5", nativeQuery = true)
    List<Object> getListTaiXe();
}
