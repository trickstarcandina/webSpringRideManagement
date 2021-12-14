-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: quanlychuyenxe
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `username` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chuyenxe`
--

DROP TABLE IF EXISTS `chuyenxe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chuyenxe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `gia_ve` bigint DEFAULT NULL,
  `so_luong_hanh_khach` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `thoi_gian_ket_thuc` datetime DEFAULT NULL,
  `thoi_gian_khoi_hanh` datetime DEFAULT NULL,
  `tai_xe1_username` varchar(255) DEFAULT NULL,
  `tai_xe2_username` varchar(255) DEFAULT NULL,
  `tuyen_xe_id` int DEFAULT NULL,
  `xe_khach_bien_so` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK19i3d6qln6hkxbukwhcv67pjj` (`tai_xe1_username`),
  KEY `FKdjt63l39fsp3de2cj75ok56bv` (`tai_xe2_username`),
  KEY `FKe5eb372bdwiv6t32juesweswj` (`tuyen_xe_id`),
  KEY `FK7c8glgpr69fjmkggp0mf6po9h` (`xe_khach_bien_so`),
  CONSTRAINT `FK19i3d6qln6hkxbukwhcv67pjj` FOREIGN KEY (`tai_xe1_username`) REFERENCES `taixe` (`username`),
  CONSTRAINT `FK7c8glgpr69fjmkggp0mf6po9h` FOREIGN KEY (`xe_khach_bien_so`) REFERENCES `xekhach` (`bien_so`),
  CONSTRAINT `FKdjt63l39fsp3de2cj75ok56bv` FOREIGN KEY (`tai_xe2_username`) REFERENCES `taixe` (`username`),
  CONSTRAINT `FKe5eb372bdwiv6t32juesweswj` FOREIGN KEY (`tuyen_xe_id`) REFERENCES `tuyenxe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chuyenxe`
--

LOCK TABLES `chuyenxe` WRITE;
/*!40000 ALTER TABLE `chuyenxe` DISABLE KEYS */;
INSERT INTO `chuyenxe` VALUES (1,NULL,'2021-12-13 10:26:10',50000,3,1,'2021-12-12 20:30:00','2021-12-12 10:30:00','taixe1','taixe2',1,'BS01'),(2,NULL,'2021-12-13 10:26:00',20000,2,1,'2021-12-12 15:30:00','2021-12-12 10:30:00','congphuong','taixe2',3,'BS02'),(3,NULL,'2021-12-13 10:24:39',20000,1,1,'2021-11-14 20:30:00','2021-11-14 10:30:00','taixe1','congphuong',5,'BS02'),(4,'2021-12-13 10:18:56','2021-12-13 10:18:56',60000,0,1,'2021-12-12 20:30:00','2021-12-12 10:30:00','tiendat','taixe3',8,'BS02'),(5,NULL,'2021-12-13 10:24:03',60000,1,1,'2021-12-01 15:30:00','2021-12-01 10:30:00','taixe2','taixe1',2,'BS02'),(6,NULL,'2021-12-13 10:24:08',40000,1,1,'2021-11-24 20:30:00','2021-11-24 10:30:00','taixe3','tiendat',11,'BS02'),(7,NULL,'2021-12-13 10:26:06',30000,2,1,'2021-11-16 20:30:00','2021-11-16 10:30:00','taixe3','taixe1',10,'BS02'),(8,'2021-12-13 10:33:52','2021-12-13 10:33:52',20000,0,0,'2023-06-11 20:30:00','2023-06-11 10:30:00',NULL,NULL,9,'BS01'),(9,'2021-12-13 11:28:29','2021-12-13 11:28:29',12000,0,0,'2022-08-12 15:30:00','2022-08-12 04:30:00',NULL,NULL,11,'BS02');
/*!40000 ALTER TABLE `chuyenxe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `username` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `ghi_chu` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `tuoi` int DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
INSERT INTO `khachhang` VALUES ('khachhang1','2021-12-10 09:04:44','$2a$10$9gzdnGo2FyTItCg6iEXgaOAIzQscPEueEu4Jo0LEYjffR4qhRZ6um',NULL,'2021-12-12 21:13:24','Hà Nam','','Khách hàng 1',25),('khachhang2','2021-12-10 09:06:27','$2a$10$B0boivQBSMH.YZ8IUxaX9ekJno.vnBJwJlq/Y7wm.IzUNX66eBN8e',NULL,'2021-12-12 23:35:27','Hà Đông','','Khách hàng 2',22),('khachhang3','2021-12-10 09:06:36','$2a$10$u9ILx29LI6ZR544EXB5kMOidKkSAQB0RpPJJaOFuinG6gIHJpeC9O',NULL,'2021-12-10 21:42:27','Hà Nam','Alo','Khách hàng 3',15),('khachhang4','2021-12-10 21:36:15','$2a$10$UqDrJkdjZZJlbg1NEjHtaew5aWwKWwFfKEZKTa6RZsZ2oL5edaECy',NULL,'2021-12-12 23:35:17','Sài Gòn','','Khách hàng 4',20),('khachhang5','2021-12-12 10:08:47','$2a$10$iXfG.u.GP7KHGGBz7so37.KaaKn8yFi2h0WINWuHBuU4NXe23IxyS',NULL,'2021-12-12 10:08:47','Thanh Hóa','...http://localhost:8081/admin/khachhang','khachhang5',30);
/*!40000 ALTER TABLE `khachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang_chuyenxe`
--

DROP TABLE IF EXISTS `khachhang_chuyenxe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang_chuyenxe` (
  `khach_hang_username` varchar(255) NOT NULL,
  `chuyenxe_id` int NOT NULL,
  PRIMARY KEY (`chuyenxe_id`,`khach_hang_username`),
  KEY `FKiyqxp7i7w4qi348uj0wq8ives` (`khach_hang_username`),
  CONSTRAINT `FKakajm09e3tro9xdef4vpyf7p9` FOREIGN KEY (`chuyenxe_id`) REFERENCES `chuyenxe` (`id`),
  CONSTRAINT `FKiyqxp7i7w4qi348uj0wq8ives` FOREIGN KEY (`khach_hang_username`) REFERENCES `khachhang` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang_chuyenxe`
--

LOCK TABLES `khachhang_chuyenxe` WRITE;
/*!40000 ALTER TABLE `khachhang_chuyenxe` DISABLE KEYS */;
INSERT INTO `khachhang_chuyenxe` VALUES ('khachhang1',1),('khachhang1',2),('khachhang1',3),('khachhang1',7),('khachhang2',1),('khachhang2',5),('khachhang2',6),('khachhang3',1),('khachhang3',2),('khachhang3',7);
/*!40000 ALTER TABLE `khachhang_chuyenxe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `luongcoban`
--

DROP TABLE IF EXISTS `luongcoban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `luongcoban` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `ghi_chu` varchar(255) DEFAULT NULL,
  `luong` bigint DEFAULT NULL,
  `phu_cap` bigint DEFAULT NULL,
  `thang_luong` datetime DEFAULT NULL,
  `tai_xe_username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5363cphh95vx52py8mkfvp7yn` (`tai_xe_username`),
  CONSTRAINT `FK5363cphh95vx52py8mkfvp7yn` FOREIGN KEY (`tai_xe_username`) REFERENCES `taixe` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luongcoban`
--

LOCK TABLES `luongcoban` WRITE;
/*!40000 ALTER TABLE `luongcoban` DISABLE KEYS */;
INSERT INTO `luongcoban` VALUES (1,NULL,NULL,NULL,5000000,12,'2020-12-30 00:00:00','taixe2'),(2,NULL,NULL,NULL,6000000,55,'2020-12-30 00:00:00','taixe3'),(3,NULL,NULL,NULL,4000000,14,'2020-12-30 00:00:00','taixe1'),(4,NULL,'2021-12-11 08:59:14','...',6000000,52,'2021-12-11 07:00:00','taixe2'),(5,NULL,'2021-12-11 10:11:40','Thưởng tết',7200000,500000,'2021-12-11 00:00:00','taixe1');
/*!40000 ALTER TABLE `luongcoban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taixe`
--

DROP TABLE IF EXISTS `taixe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taixe` (
  `username` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `cmt_tai_xe` varchar(255) DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `loai_bang_lai` varchar(255) DEFAULT NULL,
  `ma_so_bang_lai` varchar(255) DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `tham_nien` int DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taixe`
--

LOCK TABLES `taixe` WRITE;
/*!40000 ALTER TABLE `taixe` DISABLE KEYS */;
INSERT INTO `taixe` VALUES ('admin','2021-12-13 09:53:06','$2a$10$Ltu7C8Ov8dsNUTLKO5vVT.e2yd9XDwTMwnm0EUhQSNpyQkCO3q0JS',NULL,'2021-12-13 09:53:06','CMT2645987423','Hà Nam','Xe ô tô','B2','2000-12-31','Nguyễn Đức Thắng',2),('congphuong',NULL,'654321',NULL,NULL,'phuong6546546','Thái Bình','A1','bl465541','2000-06-12','Cộng Phượng',2),('ducchinh',NULL,'123456',NULL,NULL,'chinh79845','Hải Dương','B1','bl478996','2000-03-13','Đức Chính',5),('minhthang',NULL,'123456',NULL,NULL,'thang79845','Hoài Đức','B1','bl478941','2000-06-11','Trịnh Minh Thắng',5),('quocvu1',NULL,'123456',NULL,NULL,'vu3234324','Hà Đông','A2','bl123456','2000-08-23','Nguyễn Quốc Vũ',3),('taixe1','2021-12-07 00:03:55','$2a$10$rRFMr17agpU8oYGSW6Hz.egLx.o5v.3N6KyIC/t1fVKtu9rXZQPza',NULL,'2021-12-13 10:27:16','CMT98765434','Hà Nội','B2','BL05','1998-01-10','Trần Thị A',3),('taixe2','2021-12-07 00:05:42','$2a$10$bXx6k8ooVeywodOlneLshOwokL804meVpQMnmyaewlz7rgl20jDsm',NULL,'2021-12-13 10:13:50','CMT98765434','Hà Nam','A2','BL01','2000-02-19','Nguyễn Văn B',7),('taixe3','2021-12-09 16:47:29','$2a$10$/f4vBtlZvTM5KRCIZagF7O.qfksqYY9iHfYWH0o.h4Qz10hyKfcLe',NULL,'2021-12-09 17:23:59','CMT98765434','Hà Nam','A1','BL01','1980-12-31','Phạm Minh Chính',5),('tantai',NULL,'123456',NULL,NULL,'tai3234324','Hà Đông ','A2','bl127789','2000-02-23','Tấn Tài',3),('tiendat',NULL,'654321',NULL,NULL,'dat6546546','Hà Đông','A1','bl465564','2000-04-11','Đặng Tiến Đạt',2),('vanhoa',NULL,'123456',NULL,NULL,'hoa79845','Hoài Đức','B1','bl478154','2000-09-12','Văn HOà',5),('vanthanh',NULL,'123456',NULL,NULL,'thanh79845','Hoài Đức','B1','bl478454','2000-06-24','Văn Thanh',5),('vantoan',NULL,'123456',NULL,NULL,'toan3234324','Thanh Xuân','A2','bl121645','2000-08-11','Nguyễn Văn Toàn',3),('xuantruong',NULL,'654321',NULL,NULL,'truong6546546','Cầu Giấy','A1','bl4654541','2000-01-11','Xuân Trường',2);
/*!40000 ALTER TABLE `taixe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tongluong`
--

DROP TABLE IF EXISTS `tongluong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tongluong` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `luong_hoa_hong` bigint DEFAULT NULL,
  `chuyen_xe_id` int DEFAULT NULL,
  `luong_co_ban_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKntff0482b5yhfhswe969lm6bs` (`chuyen_xe_id`),
  KEY `FKluw0rddrfdhmgl3mxdm84750a` (`luong_co_ban_id`),
  CONSTRAINT `FKluw0rddrfdhmgl3mxdm84750a` FOREIGN KEY (`luong_co_ban_id`) REFERENCES `luongcoban` (`id`),
  CONSTRAINT `FKntff0482b5yhfhswe969lm6bs` FOREIGN KEY (`chuyen_xe_id`) REFERENCES `chuyenxe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tongluong`
--

LOCK TABLES `tongluong` WRITE;
/*!40000 ALTER TABLE `tongluong` DISABLE KEYS */;
INSERT INTO `tongluong` VALUES (1,NULL,NULL,263000,1,5),(2,NULL,NULL,185152,1,4),(3,NULL,NULL,18750,3,5),(4,NULL,NULL,13200,3,NULL),(5,NULL,NULL,12500,5,4),(6,NULL,NULL,8800,5,5),(7,NULL,NULL,22500,7,2),(8,NULL,NULL,15840,7,5),(9,NULL,NULL,30000,4,NULL),(10,NULL,NULL,21120,4,2),(11,NULL,NULL,40500,6,2),(12,NULL,NULL,28512,6,NULL),(13,NULL,NULL,26250,2,NULL),(14,NULL,NULL,18480,2,4);
/*!40000 ALTER TABLE `tongluong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tuyenxe`
--

DROP TABLE IF EXISTS `tuyenxe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tuyenxe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `diem_cuoi` varchar(255) DEFAULT NULL,
  `diem_dau` varchar(255) DEFAULT NULL,
  `do_dai` int DEFAULT NULL,
  `do_phuc_tap_cua_tuyen_duong` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tuyenxe`
--

LOCK TABLES `tuyenxe` WRITE;
/*!40000 ALTER TABLE `tuyenxe` DISABLE KEYS */;
INSERT INTO `tuyenxe` VALUES (1,NULL,NULL,'Thanh Hóa','Hà Nội',1052,2),(2,NULL,NULL,'Bắc Giang','Hà Nội',100,1),(3,NULL,NULL,'Bắc Ninh','Hà Nội',105,2),(4,NULL,NULL,'Hải Phòng','Hà Nội',110,3),(5,NULL,NULL,'Kiên Giang','Hà Nội',150,1),(6,NULL,NULL,'An Giang','Hà Nội',107,1),(7,NULL,NULL,'Lào Cai','Hà Nội',102,2),(8,NULL,NULL,'Hưng Yên','Hà Nội',120,2),(9,NULL,NULL,'Thái Bình','Hà Nội',110,3),(10,NULL,NULL,'Bắc Giang','Hải Phòng',180,1),(11,NULL,NULL,'Bắc Giang','Phú Thọ',108,3);
/*!40000 ALTER TABLE `tuyenxe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xekhach`
--

DROP TABLE IF EXISTS `xekhach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xekhach` (
  `bien_so` varchar(255) NOT NULL,
  `doi_xe` int DEFAULT NULL,
  `hang_san_xuat` varchar(255) DEFAULT NULL,
  `mau_xe` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `ngay_bao_duong_cuoi_cung` date DEFAULT NULL,
  `so_ghe` int DEFAULT NULL,
  `so_nam_su_dung` int DEFAULT NULL,
  `ten_xe_khach` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bien_so`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xekhach`
--

LOCK TABLES `xekhach` WRITE;
/*!40000 ALTER TABLE `xekhach` DISABLE KEYS */;
INSERT INTO `xekhach` VALUES ('BS01',2021,'Xanh','Đỏ','2018','2021-12-12',40,5,'Hải Hạnh'),('BS02',2020,'Honda','Xanh','2018','2020-01-31',42,5,'Hồng Hà'),('BS03',2020,'Honda','Vàng','2017','2020-01-31',35,5,'Hồng Hà');
/*!40000 ALTER TABLE `xekhach` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-13 16:07:16
