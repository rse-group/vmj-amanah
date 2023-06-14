-- Insert Chart of Accounts

INSERT INTO "chartofaccount_comp" ("id") VALUES
(1010000);
(1020000);
(1020100);
(1030000),
(2010000),
(1040000),
(1050000),
(1070000),
(1080100),
(1080200),
(1990000),
(1999900),
(3010000),
(3020000),
(4010000),
(2010100),
(4020000),
(4030000),
(4050000),
(4990000),
(5010000),
(5020000),
(4020300),
(2010200),
(4030100),
(4030200),
(2020000),
(4010100),
(4010200),
(4010300),
(4020100),
(4020200),
(4040000),
(4040100),
(4040200),
(4040400),
(4050100),
(4999900),
(5020200),
(2020100),
(2020200),
(1000000),
(3000000),
(4000000),
(5000000),
(1010100),
(1010200),
(1020200),
(1080300),
(5030200),
(5010100),
(5010200),
(5020100),
(5020300),
(5029900),
(5030000),
(5030100),
(5030300),
(5030400),
(5030500),
(5030600),
(5030700),
(5030800),
(5030900),
(5031000),
(5039900),
(5031100),
(1030100),
(1030200),
(1040100),
(1049900),
(1050200),
(1070400),
(2000000),
(1060000),
(1080000),
(4040300),
(1040200),
(1050100),
(1060100),
(1070100),
(1060200),
(1069900),
(1070200),
(1070300),
(3030000);


INSERT INTO "chartofaccount_impl" ("code", "description", "isvisible", "name", "id", "parentid", "isrestricted", "cashflowtype", "level") VALUES
(1000000, '    ', '0', 'ASET', 1000000, NULL, 'f', NULL, 0);
INSERT INTO "chartofaccount_impl" ("code", "description", "isvisible", "name", "id", "parentid", "isrestricted", "cashflowtype", "level") VALUES
(1080100, 'Akumulasi penyusutan aset yang dimiliki oleh organisasi dalam bentuk tanah dan bangunan', '1', 'Ak. Peny. Bangunan -/-', 1080100, 1080000, 'f', 2, 2);
INSERT INTO "chartofaccount_impl" ("code", "description", "isvisible", "name", "id", "parentid", "isrestricted", "cashflowtype", "level") VALUES
(1080200, 'Akumulasi penyusutan aset yang dimiliki oleh organisasi dalam bentuk peralatan', '1', 'Ak. Peny. Mebel, ATK, Peralatan -/-', 1080200, 1080000, 'f', 2, 2);
INSERT INTO "chartofaccount_impl" ("code", "description", "isvisible", "name", "id", "parentid", "isrestricted", "cashflowtype", "level") VALUES
(1010000, '    ', '0', 'Kas', 1010000, 1000000, 'f', NULL, 1),
(1020000, '    ', '0', 'Rekening Bank', 1020000, 1000000, 'f', NULL, 1),
(1020100, 'Saldo yang tersimpan di rekening bank, untuk kebutuhan/pembiayaan khusus, seperti rekening untuk zakat fithrah, hanya untuk fakir miskin saja.', '1', 'Rekening Bank - Khusus', 1020100, 1020000, 'f', NULL, 2),
(1030000, '', '0', 'Piutang', 1030000, 1000000, 'f', NULL, 1),
(1040000, 'Investasi yang ditempatkan oleh organisasi dalam jangka waktu pendek (s/d 1 thn)', '1', 'Investasi Jangka Pendek', 1040000, 1000000, 'f', NULL, 1),
(1050000, 'Aset lancar pada organisasi yang tidak digolongkan pada kategori diatas', '0', 'Aset Lancar Lainnya', 1050000, 1000000, 'f', NULL, 1),
(1070000, '', '0', 'Aset Tetap dan Inventaris', 1070000, 1000000, 'f', NULL, 1),
(1990000, 'Aset lainnya yang tidak dapat diklasifikasikan dalam akun diatas', '0', 'Aset Lainnya', 1990000, 1000000, 'f', NULL, 1),
(1999900, 'Aset lainnya yang tidak dapat diklasifikasikan dalam akun diatas', '1', 'Aset Lainnya - Lainnya', 1999900, 1990000, 'f', NULL, 2),
(2010000, '', '0', 'Liabilitas Jangka Pendek', 2010000, 2000000, 'f', 1, 1),
(2010100, 'Pendapatan yang telah diterima oleh organisasi, namun belum diakui oleh organisasi', '1', 'Pendapatan diterima Dimuka', 2010100, 2010000, 'f', 1, 2),
(2010200, 'Utang yang dimiliki oleh organisasi dengan jatuh tempo kurang dari 1 tahun', '1', 'Utang jangka pendek', 2010200, 2010000, 'f', 1, 2),
(2020000, '', '0', 'Liabilitas Jangka Panjang', 2020000, 2000000, 'f', 1, 1),
(2020100, 'Utang yang dimiliki oleh organisasi dengan jatuh tempo lebih dari 1 tahun', '1', 'Utang jangka panjang', 2020100, 2020000, 'f', 1, 2),
(2020200, 'Liabilitas yang dicadangkan oleh organisasi dengan tujuan untuk imbalan kerja bagi pekerja', '1', 'Liabilitas imbalan kerja', 2020200, 2020000, 'f', 1, 2),
(5000000, '    ', '0', 'BEBAN', 5000000, NULL, 'f', NULL, 0),
(1080300, 'Akumulasi penyusutan aset yang dimiliki oleh organisasi dalam bentuk kendaraan', '1', 'Ak. Peny. Kendaraan -/-', 1080300, 1080000, 'f', 2, 2),
(1010100, 'Uang tunai yang disimpan pengurus, untuk kebutuhan umum seperti saldo kotak amal Jumat', '1', 'Kas - Umum', 1010100, 1010000, 'f', NULL, 2),
(1010200, 'Uang tunai yang disimpan pengurus, untuk kebutuhan khusus seperti saldo kas untuk operasional dan konsumsi petugas', '1', 'Kas - Khusus', 1010200, 1010000, 'f', NULL, 2),
(1020200, 'Saldo yang tersimpan di rekening bank, dan memang jelas bebas digunakan untuk institusi sesuai aturan yang berlaku,', '1', 'Rekening Bank - Umum', 1020200, 1020000, 'f', NULL, 2),
(5030000, 'Biaya untuk kebutuhan umum seperti administrasi, iuran kebersihan, dan keamanan', '0', 'Biaya Umum', 5030000, 5000000, 'f', 1, 1),
(1030100, 'Sejumlah uang yang akan diterima oleh organisasi akibat timbulnya bunga dari investasi', '1', 'Piutang Bunga', 1030100, 1030000, 'f', 2, 2),
(1030200, 'Piutang lainnya yang dimiliki oleh organisasi, seperti kasbon oleh pegawai, pinjaman dana sementara kepada pihak lain, dsb', '1', 'Piutang Lainnya', 1030200, 1030000, 'f', 1, 2),
(1040100, 'Investasi yang ditempatkan oleh organisasi dalam bentuk deposito berjangka', '1', 'Investasi Jangka Pendek - Deposito', 1040100, 1040000, 'f', 3, 2),
(1049900, 'Investasi lainya yang ditempatkan oleh organisasi dalam waktu pendek', '1', 'Investasi Jangka Pendek - Lainnya', 1049900, 1040000, 'f', 3, 2),
(1050200, 'Sejumlah barang yang dimiliki organisasi dan sifatnya habis pakai, seperti persediaan kertas, materai, dsb', '1', 'Persediaan', 1050200, 1050000, 'f', 1, 2),
(1070400, 'Nilai perolehan aset yang dimiliki oleh organisasi dalam bentuk kendaraan', '1', 'ATI Kendaraan', 1070400, 1070000, 'f', 2, 2),
(5010100, 'Pembiayaan dalam bentuk menyalurkan kembali donasi yang diterima kepada pihak lain, seperti penyaluran zakat maal kepada para mustahiq.', '1', 'Penyaluran Donasi - Tidak Terbatas', 5010100, 5010000, 'f', 1, 2),
(5010200, 'Pembiayaan dalam bentuk menyalurkan kembali donasi yang diterima kepada pihak lain, seperti penyaluran zakat maal kepada para mustahiq.', '1', 'Penyaluran Donasi - Terbatas', 5010200, 5010000, 't', 1, 2),
(5020100, 'Pembiayaan untuk gaji dan honor, seperti gaji marbot, gaji keamanan, honor pembicaran, honor ustadz, moderator', '1', 'Gaji', 5020100, 5020000, 'f', 1, 2),
(5020300, 'Pembiayaan untuk gaji dan honor, seperti gaji marbot, gaji keamanan, honor pembicaran, honor ustadz, moderator', '1', 'Pendidikan', 5020300, 5020000, 'f', 1, 2),
(5029900, 'Pembiayaan untuk gaji dan honor, seperti gaji marbot, gaji keamanan, honor pembicaran, honor ustadz, moderator', '1', 'BTK Lainnya', 5029900, 5020000, 'f', 1, 2),
(5030100, 'Pembiayaan administrasi seperti biaya pengurusan akta notaris, biaya transfer dll', '1', 'Biaya Administrasi', 5030100, 5030000, 'f', 1, 2),
(5030200, 'Pembiayaan sewa ruangan, alat, sewa jasa, termasuk jasa biaya tranportasi, kurir, akomodasi', '1', 'Biaya Sewa dan Jasa', 5030200, 5030000, 'f', 1, 2),
(5030300, 'Pengeluaran yang terkait dengan kegiatan atau program institusi', '1', 'Pengeluaran Kegiatan/Program', 5030300, 5030000, 'f', 1, 2),
(5030400, 'Pembiayaan untuk publikasi program, kegiatan', '1', 'Biaya Publikasi', 5030400, 5030000, 'f', 1, 2),
(5030500, 'Pembiayaan untuk konsumsi kegiatan/program', '1', 'Biaya Konsumsi', 5030500, 5030000, 'f', 1, 2),
(4010300, 'Donasi dari swasta seperti dari dana CSR atau sumbangan karyawan.', '1', 'Donasi Institusi - Terbatas', 4010300, 4010000, 't', 1, 2),
(3010000, 'Aset Netto tanpa pembatasan', '1', 'Aset Netto Tanpa Pembatasan', 3010000, 3000000, 'f', NULL, 1),
(3020000, 'Aset Netto dengan pembatasan', '1', 'Aset Netto Dengan Pembatasan', 3020000, 3000000, 'f', NULL, 1),
(4020200, 'Donasi dari individu yang tidak diketahui sumber nya atau dinyatakan anonymous.', '1', 'Donasi Anonymous', 4020200, 4020000, 'f', 1, 2),
(4020300, 'Donasi dari individu yang tidak diketahui sumber nya atau dinyatakan anonymous.', '1', 'Donasi Individu - Terbatas', 4020300, 4020000, 't', 1, 2),
(4030000, 'Rekapitulasi segala Penerimaan institusi dari hasil usaha baik usaha internal maupun kerjasama', '0', 'Hasil Usaha', 4030000, 4000000, 'f', NULL, 1),
(4030100, 'Penerimaan institusi dari kegiatan penjualan barang', '1', 'Hasil penjualan', 4030100, 4030000, 'f', 2, 2),
(4030200, 'Penerimaan institusi dari kegiatan upah atau jasa', '1', 'Hasil upah jasa', 4030200, 4030000, 'f', 2, 2),
(4040000, 'Penerimaan institusi dari usaha lain-lain atau bonus/komisi', '0', 'Jasa Layanan / Bunga', 4040000, 4000000, 'f', 3, 1),
(4040100, 'Penerimaan institusi dari usaha lain-lain atau bonus/komisi', '1', 'Penghasilan Investasi Jangka Pendek', 4040100, 4040000, 'f', 3, 2),
(4040200, 'Penerimaan institusi dari usaha lain-lain atau bonus/komisi', '1', 'Penghasilan Investasi Jangka Panjang', 4040200, 4040000, 'f', 3, 2),
(4040300, 'Penerimaan institusi dari usaha lain-lain atau bonus/komisi', '1', 'Pendapatan Jasa Bank', 4040300, 4040000, 'f', 3, 2),
(4040400, 'Penerimaan institusi dari usaha lain-lain atau bonus/komisi', '1', 'Bonus / Komisi', 4040400, 4040000, 'f', 2, 2),
(4050000, 'Penerimaan dalam bentuk iuran rutin, seperti iuran kebersihan.', '0', 'Iuran', 4050000, 4000000, 'f', NULL, 1),
(1040200, 'Investasi yang ditempatkan oleh organisasi dalam bentuk surat berharga', '1', 'Investasi Jangka Pendek - Surat Berharga', 1040200, 1040000, 'f', 3, 2),
(1050100, 'Biaya yang telah dibayarkan oleh organisasi namun belum digunakan, seperti sewa gedung dibayar dimuka, dsb', '1', 'Biaya Dibayar Dimuka & Uang Muka', 1050100, 1050000, 'f', 1, 2),
(1060000, 'Investasi yang ditempatkan oleh organisasi dalam waktu panjang (lebih dari 1 thn)', '0', 'Investasi Jangka Panjang', 1060000, 1000000, 'f', NULL, 1),
(1060100, 'Aset milik organisasi dalam bentuk properti yang menjadi pendapatan bagi organisasi (mis. disewakan)', '1', 'Properti Investasi', 1060100, 1060000, 'f', 2, 2),
(1060200, 'Aset organisasi dalam bentuk penyertaan saham pada suatu perusahaan', '1', 'Investasi Jangka Panjang - Penyertaan Saham', 1060200, 1060000, 'f', 3, 2),
(1069900, 'Investasi jangka panjang pada organisasi dalam bentuk yang tidak diklasifikasikan diatas', '1', 'Investasi Jangka Panjang - Lainnya', 1069900, 1060000, 'f', 3, 2),
(1070100, 'Nilai perolehan aset yang dimiliki oleh organisasi dalam bentuk tanah dan bangunan', '1', 'ATI Tanah', 1070100, 1070000, 'f', 2, 2),
(1070200, 'Nilai perolehan aset yang dimiliki oleh organisasi dalam bentuk peralatan', '1', 'ATI Bangunan', 1070200, 1070000, 'f', 2, 2),
(1070300, 'Nilai perolehan aset yang dimiliki oleh organisasi dalam bentuk kendaraan', '1', 'ATI Mebel, ATK, dan Peralatan', 1070300, 1070000, 'f', 2, 2),
(1080000, '', '0', 'Akum. Penyusutan Aset Tetap -/-', 1080000, 1000000, 'f', NULL, 1),
(2000000, 'LIABILITIAS', '0', 'LIABILITAS', 2000000, NULL, 'f', NULL, 0),
(3000000, 'ASET NETO', '0', 'ASET NETO', 3000000, NULL, 'f', NULL, 0),
(4000000, '    ', '0', 'PENDAPATAN', 4000000, NULL, 'f', NULL, 0),
(4010000, '    ', '0', 'Donasi Institusi', 4010000, 4000000, 'f', NULL, 1),
(4010100, 'Donasi dari pemerintah misalnya seperti BLT', '1', 'Donasi Pemerintah', 4010100, 4010000, 'f', 1, 2),
(4010200, 'Donasi dari swasta seperti dari dana CSR atau sumbangan karyawan.', '1', 'Donasi Swasta (CSR)', 4010200, 4010000, 'f', 1, 2),
(4020000, '    ', '0', 'Donasi Individu', 4020000, 4000000, 'f', NULL, 1),
(4020100, 'Donasi dari individu yang tercatat sumber dan identitas pengirim', '1', 'Donasi Individu Tercatat', 4020100, 4020000, 'f', 1, 2),
(4050100, 'Penerimaan dalam bentuk iuran rutin, seperti iuran kebersihan.', '1', 'Iuran Anggota', 4050100, 4050000, 'f', 1, 2),
(4990000, 'Penerimaan yang belum bisa dikategorikan.', '0', 'Pendapatan Lainnya', 4990000, 4000000, 'f', NULL, 1),
(4999900, 'Penerimaan yang belum bisa dikategorikan.', '1', 'Pendapatan Lainnya ', 4999900, 4990000, 'f', 1, 2),
(5010000, 'Pembiayaan dalam bentuk menyalurkan kembali donasi yang diterima kepada pihak lain, seperti penyaluran zakat maal kepada para mustahiq.', '0', 'Penyaluran Donasi', 5010000, 5000000, 'f', NULL, 1),
(5020000, 'Pembiayaan untuk gaji dan honor, seperti gaji marbot, gaji keamanan, honor pembicaran, honor ustadz, moderator', '0', 'Gaji dan Upah', 5020000, 5000000, 'f', NULL, 1),
(5020200, 'Pembiayaan untuk gaji dan honor, seperti gaji marbot, gaji keamanan, honor pembicaran, honor ustadz, moderator', '1', 'Uang Makan dan Transport', 5020200, 5020000, 'f', 1, 2),
(5030600, 'Pembiayaan pembeliaan peralatan untuk kegiatan atau untuk kepentingan ins', '1', 'Biaya Peralatan', 5030600, 5030000, 'f', 1, 2),
(5030700, 'Segala pembiayaan/ pengeluaran yang rutin terjadi atau tidak terikat program/kegiatan khusus', '1', 'Pengeluaran Umum/Rutin', 5030700, 5030000, 'f', 1, 2),
(5030800, 'Pembiayaan bahan habis pakai yang rutin digunakan, seperti printer, sabun, cairan pembersih.', '1', 'Biaya Bahan Habis Pakai', 5030800, 5030000, 'f', 1, 2),
(5030900, 'Pembiayaan rutin operasional, seperti listrik, air, internet', '1', 'Biaya Operasional', 5030900, 5030000, 'f', 1, 2),
(5031000, 'Pembiayaan terkait perawatan aset institusi, termasuk cat tembok rutin.', '1', 'Biaya Perawatan', 5031000, 5030000, 'f', 1, 2),
(5031100, 'Pembiayaan renovasi, perbaikan kerusakan bangunan, alat, kendaraan', '1', 'Biaya Renovasi/ Reparasi', 5031100, 5030000, 'f', 1, 2),
(5039900, 'Pembiayaan lain yang belum terkategorikan atau belum jelas kategorinya', '1', 'Biaya-biaya Lainnya', 5039900, 5030000, 'f', 1, 2);

-- Seed sample Journal Entries

INSERT INTO "automaticreport_journalentry_comp" ("id") VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14),
(15),
(16),
(17),
(18),
(19),
(20),
(21),
(22),
(23),
(24),
(25),
(26),
(27),
(28),
(29),
(30),
(31),
(32),
(33),
(34),
(35),
(36),
(37),
(38),
(39),
(10001),
(10002),
(10004),
(10005),
(10007),
(10008),
(10009),
(10011),
(10012),
(10014),
(10015),
(10017),
(10018),
(10021),
(10022),
(10024),
(10025),
(10027),
(10028),
(10030),
(10031),
(10033),
(10034),
(10036),
(10037),
(10039),
(10040),
(10042),
(10043),
(10045),
(10046),
(10048),
(10049),
(10051),
(10052),
(10054),
(10055),
(10056),
(10058),
(10059),
(10061),
(10062),
(10064),
(10065),
(10067),
(10068),
(10070),
(10071),
(10073),
(10074),
(10076),
(10077),
(10079),
(10080),
(10082),
(10083),
(10085),
(10086),
(10088),
(10089),
(10091),
(10092),
(10094),
(10095),
(10097),
(10098),
(10100),
(10101),
(10103),
(10104),
(10105),
(10107),
(10108),
(10110),
(10111),
(10113),
(10114),
(10116),
(10117),
(10119),
(10120),
(40),
(10121),
(10122);


INSERT INTO "automaticreport_journalentry_impl" ("id", "accountid", "debitamount", "creditamount", "record_id", "valuedate", "description") VALUES
(10067, 1020200, 461667, 0, 22, '2022-12-31', NULL);
INSERT INTO "automaticreport_journalentry_impl" ("id", "accountid", "debitamount", "creditamount", "record_id", "valuedate", "description") VALUES
(10014, 1020200, 461667, 0, 5, '2022-12-31', NULL);
INSERT INTO "automaticreport_journalentry_impl" ("id", "accountid", "debitamount", "creditamount", "record_id", "valuedate", "description") VALUES
(10001, 1020200, 500000000, 0, 1, '2022-12-31', NULL);
INSERT INTO "automaticreport_journalentry_impl" ("id", "accountid", "debitamount", "creditamount", "record_id", "valuedate", "description") VALUES
(10002, 4010200, 0, 500000000, 1, '2022-12-31', NULL),
(10004, 1050100, 30000000, 0, 2, '2022-12-31', NULL),
(10005, 1020200, 0, 30000000, 2, '2022-12-31', NULL),
(10007, 5020100, 12000000, 0, 3, '2022-12-31', NULL),
(10008, 2010200, 0, 200000, 3, '2022-12-31', NULL),
(10009, 1020200, 0, 11800000, 3, '2022-12-31', NULL),
(10011, 5030200, 2500000, 0, 4, '2022-12-31', NULL),
(10012, 1050100, 0, 2500000, 4, '2022-12-31', NULL),
(10015, 4040300, 0, 461667, 5, '2022-12-31', NULL),
(10017, 5030100, 25000, 0, 6, '2022-12-31', NULL),
(10018, 1020200, 0, 25000, 6, '2022-12-31', NULL),
(10021, 2010200, 200000, 0, 7, '2022-12-31', NULL),
(10022, 1020200, 0, 200000, 7, '2022-12-31', NULL),
(10024, 1020200, 1000000, 0, 8, '2022-12-31', NULL),
(10025, 4020200, 0, 1000000, 8, '2022-12-31', NULL),
(10027, 1020200, 20000000, 0, 9, '2022-12-31', NULL),
(10028, 4010200, 0, 20000000, 9, '2022-12-31', NULL),
(10030, 1020200, 250000, 0, 10, '2022-12-31', NULL),
(10031, 4020300, 0, 250000, 10, '2022-12-31', NULL),
(10033, 5010200, 250000, 0, 11, '2022-12-31', NULL),
(10034, 1020200, 0, 250000, 11, '2022-12-31', NULL),
(10036, 1070300, 80000000, 0, 12, '2022-12-31', NULL),
(10037, 1020200, 0, 80000000, 12, '2022-12-31', NULL),
(10039, 1070400, 20000000, 0, 13, '2022-12-31', NULL),
(10040, 1020200, 0, 20000000, 13, '2022-12-31', NULL),
(10042, 1020200, 1000000, 0, 14, '2022-12-31', NULL),
(10043, 4020200, 0, 1000000, 14, '2022-12-31', NULL),
(10045, 5010100, 1500000, 0, 15, '2022-12-31', NULL),
(10046, 1020200, 0, 1500000, 15, '2022-12-31', NULL),
(10048, 5031100, 345000, 0, 16, '2022-12-31', NULL),
(10049, 1020200, 0, 345000, 16, '2022-12-31', NULL),
(10051, 5010100, 1500000, 0, 17, '2022-12-31', NULL),
(10052, 1020200, 0, 1500000, 17, '2022-12-31', NULL),
(10054, 5020100, 12000000, 0, 18, '2022-12-31', NULL),
(10055, 2010200, 0, 200000, 18, '2022-12-31', NULL),
(10056, 1020200, 0, 11800000, 18, '2022-12-31', NULL),
(10058, 5030200, 2500000, 0, 19, '2022-12-31', NULL),
(10059, 1050100, 0, 2500000, 19, '2022-12-31', NULL),
(10061, 5030600, 1666667, 0, 20, '2022-12-31', NULL),
(10062, 1080200, 0, 1666667, 20, '2022-12-31', NULL),
(10073, 2010200, 200000, 0, 24, '2022-12-31', NULL),
(10064, 5030600, 416667, 0, 21, '2022-12-31', NULL),
(10065, 1080300, 0, 416667, 21, '2022-12-31', NULL),
(10068, 4040300, 0, 461667, 22, '2022-12-31', NULL),
(10070, 5030100, 25000, 0, 23, '2022-12-31', NULL),
(10071, 1020200, 0, 25000, 23, '2022-12-31', NULL),
(10074, 1020200, 0, 200000, 24, '2022-12-31', NULL),
(10076, 1020200, 1000000, 0, 25, '2022-12-31', NULL),
(10077, 4020200, 0, 1000000, 25, '2022-12-31', NULL),
(10079, 1020200, 500000, 0, 26, '2022-12-31', NULL),
(10080, 4020100, 0, 500000, 26, '2022-12-31', NULL),
(10082, 5030900, 5300000, 0, 27, '2022-12-31', NULL),
(10083, 1020200, 0, 5300000, 27, '2022-12-31', NULL),
(10085, 5030500, 20000000, 0, 28, '2022-12-31', NULL),
(10086, 1020200, 0, 20000000, 28, '2022-12-31', NULL),
(10088, 1020200, 1000000, 0, 29, '2022-12-31', NULL),
(10089, 4020200, 0, 1000000, 29, '2022-12-31', NULL),
(10091, 5010100, 1500000, 0, 30, '2022-12-31', NULL),
(10092, 1020200, 0, 1500000, 30, '2022-12-31', NULL),
(10094, 5030500, 1500000, 0, 31, '2022-12-31', NULL),
(10095, 1020200, 0, 1500000, 31, '2022-12-31', NULL),
(10097, 5010100, 1500000, 0, 32, '2022-12-31', NULL),
(10098, 1020200, 0, 1500000, 32, '2022-12-31', NULL),
(10100, 1040100, 100000000, 0, 33, '2022-12-31', NULL),
(10101, 1020200, 0, 100000000, 33, '2022-12-31', NULL),
(10103, 5020100, 12000000, 0, 34, '2022-12-31', NULL),
(10104, 2010200, 0, 200000, 34, '2022-12-31', NULL),
(10105, 1020200, 0, 11800000, 34, '2022-12-31', NULL),
(10107, 5030200, 2500000, 0, 35, '2022-12-31', NULL),
(10108, 1050100, 0, 2500000, 35, '2022-12-31', NULL),
(10110, 5030600, 1666667, 0, 36, '2022-12-31', NULL),
(10111, 1080200, 0, 1666667, 36, '2022-12-31', NULL),
(10113, 5030600, 416667, 0, 37, '2022-12-31', NULL),
(10114, 1080300, 0, 416667, 37, '2022-12-31', NULL),
(10117, 4040300, 0, 461667, 38, '2022-12-31', NULL),
(10119, 5030100, 25000, 0, 39, '2022-12-31', NULL),
(10120, 1020200, 0, 25000, 39, '2022-12-31', NULL),
(10116, 1020200, 461667, 0, 38, '2022-12-31', NULL),
(10121, 1010100, 500000, 0, 40, '2023-01-01', NULL),
(10122, 4010300, 0, 500000, 40, '2023-01-01', NULL);

-- Define Procedures for Journal Creation upon Income / Expense insertion
CREATE OR REPLACE FUNCTION public.create_journal_from_income()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
txId INTEGER := ceil(extract(epoch from now()));
debitId INTEGER := ceil(extract(epoch from now())) + 1;
creditId INTEGER := ceil(extract(epoch from now())) + 2;
BEGIN
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (txId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (debitId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (creditId);

    -- Insert Journal Entry
    -- For Income - Debit: Source of Fund, Credit: Income Account
    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (debitId, '1010100', (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description  FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (creditId, (SELECT coa_id FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    RETURN NEW;
END;
$function$


CREATE OR REPLACE FUNCTION public.create_journal_from_income()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
txId INTEGER := ceil(extract(epoch from now()));
debitId INTEGER := ceil(extract(epoch from now())) + 1;
creditId INTEGER := ceil(extract(epoch from now())) + 2;
BEGIN
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (txId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (debitId);
    INSERT INTO automaticreport_journalentry_comp(id) VALUES (creditId);

    -- Insert Journal Entry
    -- For Income - Debit: Source of Fund, Credit: Income Account
    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (debitId, '1010100', (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description  FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    INSERT INTO automaticreport_journalentry_impl(id, accountid, debitamount, creditamount, record_id, valuedate, description) VALUES (creditId, (SELECT coa_id FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), 0, (SELECT amount FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), txId, (SELECT datestamp FROM financialreport_comp WHERE financialreport_comp.id = NEW.id), (SELECT description FROM financialreport_comp WHERE financialreport_comp.id = NEW.id) );

    RETURN NEW;
END;
$function$

-- Create trigger to execute the function
CREATE TRIGGER create_journal_upon_income_insertion
  BEFORE INSERT
  ON financialreport_income
  FOR EACH ROW
  EXECUTE PROCEDURE create_journal_from_income();

CREATE TRIGGER create_journal_upon_expense_insertion
  BEFORE INSERT
  ON financialreport_expense
  FOR EACH ROW
  EXECUTE PROCEDURE create_journal_from_expense();