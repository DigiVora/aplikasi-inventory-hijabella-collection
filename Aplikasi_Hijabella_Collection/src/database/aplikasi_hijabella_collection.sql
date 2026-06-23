-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for aplikasi_hijabella_collection
CREATE DATABASE IF NOT EXISTS `aplikasi_hijabella_collection` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `aplikasi_hijabella_collection`;

-- Dumping structure for table aplikasi_hijabella_collection.history
CREATE TABLE IF NOT EXISTS `history` (
  `id_history` varchar(25) NOT NULL,
  `tanggal` datetime NOT NULL,
  `jenis` enum('PEMBELIAN','PENJUALAN') NOT NULL,
  `id_referensi` varchar(25) DEFAULT NULL,
  `id_produk` varchar(25) NOT NULL,
  `jumlah` int NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `id_user` varchar(25) NOT NULL,
  PRIMARY KEY (`id_history`),
  KEY `fk_history_produk` (`id_produk`),
  KEY `fk_history_user` (`id_user`),
  CONSTRAINT `fk_history_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`),
  CONSTRAINT `fk_history_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table aplikasi_hijabella_collection.history: ~1 rows (approximately)
INSERT INTO `history` (`id_history`, `tanggal`, `jenis`, `id_referensi`, `id_produk`, `jumlah`, `keterangan`, `id_user`) VALUES
	('HS-0137-B9TR', '2026-06-13 01:37:38', 'PENJUALAN', 'PJ-0130-Z9LM', 'PR-0110-A8KS', 25, 'Alhamdulillah Laris', 'HC-2147-X7XQ-2891');

-- Dumping structure for table aplikasi_hijabella_collection.history_activity
CREATE TABLE IF NOT EXISTS `history_activity` (
  `id_activity` varchar(25) NOT NULL,
  `tanggal` datetime NOT NULL,
  `aktivitas` varchar(100) NOT NULL,
  `data_lama` varchar(255) DEFAULT NULL,
  `data_baru` varchar(255) DEFAULT NULL,
  `id_user` varchar(25) NOT NULL,
  PRIMARY KEY (`id_activity`),
  KEY `fk_activity_user` (`id_user`),
  CONSTRAINT `fk_activity_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table aplikasi_hijabella_collection.history_activity: ~0 rows (approximately)
INSERT INTO `history_activity` (`id_activity`, `tanggal`, `aktivitas`, `data_lama`, `data_baru`, `id_user`) VALUES
	('HA-2149-C5YU', '2026-06-13 01:54:17', 'DAFTAR AKUN', NULL, 'achmadyakin', 'HC-2147-X7XQ-2891');

-- Dumping structure for table aplikasi_hijabella_collection.pembelian
CREATE TABLE IF NOT EXISTS `pembelian` (
  `id_pembelian` varchar(25) NOT NULL,
  `tanggal_pembelian` date NOT NULL,
  `jumlah_masuk` int NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `id_user` varchar(25) NOT NULL,
  `id_supplier` varchar(25) NOT NULL,
  `id_produk` varchar(25) NOT NULL,
  PRIMARY KEY (`id_pembelian`),
  KEY `fk_pembelian_user` (`id_user`),
  KEY `fk_pembelian_supplier` (`id_supplier`),
  KEY `fk_pembelian_produk` (`id_produk`),
  CONSTRAINT `fk_pembelian_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`),
  CONSTRAINT `fk_pembelian_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`),
  CONSTRAINT `fk_pembelian_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table aplikasi_hijabella_collection.pembelian: ~1 rows (approximately)
INSERT INTO `pembelian` (`id_pembelian`, `tanggal_pembelian`, `jumlah_masuk`, `keterangan`, `id_user`, `id_supplier`, `id_produk`) VALUES
	('PB-0118-B9TR', '2026-06-12', 25, 'Restok Hijab Pashmina Ceruty', 'HC-2147-X7XQ-2891', 'SP-0108-A8KQ', 'PR-0110-A8KS');

-- Dumping structure for table aplikasi_hijabella_collection.penjualan
CREATE TABLE IF NOT EXISTS `penjualan` (
  `id_penjualan` varchar(25) NOT NULL,
  `tanggal_penjualan` date NOT NULL,
  `jumlah_keluar` int NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `id_user` varchar(25) NOT NULL,
  `id_produk` varchar(25) NOT NULL,
  PRIMARY KEY (`id_penjualan`),
  KEY `fk_penjualan_user` (`id_user`),
  KEY `fk_penjualan_produk` (`id_produk`),
  CONSTRAINT `fk_penjualan_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`),
  CONSTRAINT `fk_penjualan_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table aplikasi_hijabella_collection.penjualan: ~0 rows (approximately)
INSERT INTO `penjualan` (`id_penjualan`, `tanggal_penjualan`, `jumlah_keluar`, `keterangan`, `id_user`, `id_produk`) VALUES
	('PJ-0130-Z9LM', '2026-06-12', 25, 'Alhamdulillah Laris', 'HC-2147-X7XQ-2891', 'PR-0110-A8KS');

-- Dumping structure for table aplikasi_hijabella_collection.produk
CREATE TABLE IF NOT EXISTS `produk` (
  `id_produk` varchar(25) NOT NULL,
  `id_supplier` varchar(25) DEFAULT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `kategori` varchar(50) DEFAULT NULL,
  `stok` int NOT NULL DEFAULT '0',
  `harga` int NOT NULL DEFAULT (0),
  `deskripsi` text,
  `id_user` varchar(25) NOT NULL,
  PRIMARY KEY (`id_produk`),
  KEY `fk_produk_user` (`id_user`),
  KEY `fk_produk_supplier` (`id_supplier`),
  CONSTRAINT `fk_produk_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`),
  CONSTRAINT `fk_produk_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table aplikasi_hijabella_collection.produk: ~1 rows (approximately)
INSERT INTO `produk` (`id_produk`, `id_supplier`, `nama_produk`, `kategori`, `stok`, `harga`, `deskripsi`, `id_user`) VALUES
	('PR-0110-A8KS', 'SP-0108-A8KQ', 'Hijab Pashmina Ceruty', 'Hijab', 50, 75000, 'Pashmina premium ceruty babydoll', 'HC-2147-X7XQ-2891');

-- Dumping structure for table aplikasi_hijabella_collection.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id_supplier` varchar(25) NOT NULL,
  `nama_supplier` varchar(100) NOT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `no_telp` varchar(20) NOT NULL,
  `id_user` varchar(25) NOT NULL,
  PRIMARY KEY (`id_supplier`),
  UNIQUE KEY `no_telp` (`no_telp`),
  KEY `fk_supplier_user` (`id_user`),
  CONSTRAINT `fk_supplier_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table aplikasi_hijabella_collection.supplier: ~0 rows (approximately)
INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `alamat`, `no_telp`, `id_user`) VALUES
	('SP-0108-A8KQ', 'PT Siti Khadijah Nusantara', 'Jl. Menteng Raya Bintaro Sektor 7 No. 18, Tangerang Selatan', '08111611228', 'HC-2147-X7XQ-2891');

-- Dumping structure for table aplikasi_hijabella_collection.user
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` varchar(25) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `whatshapp` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `uk_username` (`username`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`) USING BTREE,
  UNIQUE KEY `uk_whatshapp` (`whatshapp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table aplikasi_hijabella_collection.user: ~1 rows (approximately)
INSERT INTO `user` (`id_user`, `nama_lengkap`, `username`, `email`, `whatshapp`, `password`, `created_at`) VALUES
	('HC-2147-X7XQ-2891', 'Admin Hijabella Collection', 'admin', 'admin@gmail.com', '081234567890', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '2026-06-17 16:14:58');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
