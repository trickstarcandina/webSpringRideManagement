create database VAN_TAI;

use VAN_TAI;

DROP TABLE IF EXISTS `XE_KHACH`;
CREATE TABLE `XE_KHACH` (
	`bienSo` varchar(50) NOT NULL,
    `mauXe` varchar(50) NOT NULL,
    `hangSanXuat` varchar(50) NOT NULL,
    `doiXe` int NOT NULL,
    `model` varchar(50) NOT NULL,
    `soGhe` int NOT NULL,
    `soNamSuDung` int NOT NULL,
    `ngayBaoDuongCuoiCung` date NOT NULL,
    primary key (`bienSo`) 
);

DROP TABLE IF EXISTS `TAI_XE`;
CREATE TABLE `TAI_XE` (
	`cmtTaiXe` varchar(50) NOT NULL,
    `ten` nvarchar(50) NOT NULL,
    `maSoBangLai` varchar(50) NOT NULL,
    `loaiBangLai` varchar(50) NOT NULL,
    `diaChi` nvarchar(255) NOT NULL,
    `ngaySinh` date NOT NULL,
    `thamnien` int NOT NULL,
    primary key (`cmtTaiXe`) 
);

DROP TABLE IF EXISTS `TUYEN_XE`;
CREATE TABLE `TUYEN_XE` (
	`maSoTuyenXe` varchar(50) NOT NULL,
    `diemDau` varchar(50) NOT NULL,
    `diemCuoi` varchar(50) NOT NULL,
    `doDai` int NOT NULL,
    `doPhucTapCuaTuyenDuong` int NOT NULL,
    primary key (`maSoTuyenXe`) 
);

DROP TABLE IF EXISTS `CHUYEN_XE`;
CREATE TABLE `CHUYEN_XE` (
	`maSoChuyenXe` varchar(50) NOT NULL,
    `tenLaiXe` nvarchar(50) NOT NULL,
    `tenPhuXe` nvarchar(50) NOT NULL,
    `soKhach` int NOT NULL,
    `giaVe` int NOT NULL,
    `thoiGian` date NOT NULL,
    `bienSo` varchar(50) NOT NULL,
    `maSoTuyenXe` varchar(50) NOT NULL,
    `cmtLaiXe` varchar(50) NOT NULL,
    `cmtPhuXe` varchar(50) NOT NULL,
    `luongLaiXe` int NOT NULL,
    `luongPhuXe` int NOT NULL,
    primary key (`maSoChuyenXe`) ,
    constraint fk_xeKhach foreign key (`bienSo`) references `XE_KHACH`(`bienSo`),
    constraint fk_tuyenXe foreign key (`maSoTuyenXe`) references `TUYEN_XE`(`maSoTuyenXe`),
    constraint fk_laiXe foreign key (`cmtLaiXe`) references `TAI_XE`(`cmtTaiXe`),
    constraint fk_phuXe foreign key (`cmtPhuXe`) references `TAI_XE`(`cmtTaiXe`)
);

DROP TABLE IF EXISTS `KHACH_HANG`;
CREATE TABLE `KHACH_HANG` (
	`cmtKhachHang` varchar(50) NOT NULL,
    `tenKhachHang` nvarchar(50) NOT NULL,
    `diaChiKhachHang` nvarchar(50) NOT NULL,
    `tuoi` varchar(50) NOT NULL,
    `ghiChu` varchar(50) NOT NULL,
    primary key (`cmtKhachHang`) 
);

DROP TABLE IF EXISTS `CHUYEN_XE_KHACH_HANG`;
CREATE TABLE `CHUYEN_XE_KHACH_HANG` (
	`maSoChuyenXeKhachHang` varchar(50) NOT NULL,
    `maSoChuyenXe` varchar(50) NOT NULL,
    `cmtKhachHang` varchar(50) NOT NULL,
    primary key (`maSoChuyenXeKhachHang`) ,
    constraint fk_xeChuyenXe foreign key (`maSoChuyenXe`) references `CHUYEN_XE`(`maSoChuyenXe`),
    constraint fk_khachHang foreign key (`cmtKhachHang`) references `KHACH_HANG`(`cmtKhachHang`)
);