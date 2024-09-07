USE calibration;	

DROP TABLE IF EXISTS report;
CREATE TABLE report (		
  report_id INT AUTO_INCREMENT COMMENT 'id',
  report_no VARCHAR(32) COMMENT '報告編號',
  report_name VARCHAR(32) COMMENT '檔案名稱',
  result VARCHAR(32) COMMENT '校驗結果',
  calibrate_date DATE COMMENT '校驗日期',
  is_taf VARCHAR(32) COMMENT '是否有TAF',
  
  PRIMARY KEY (report_id)
);

INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'EQ003_230220', 'EQ003絕緣耐壓測試器.pdf', '合格', '2023-02-20', '有');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'EQ003_240220', 'EQ003絕緣耐壓測試器.pdf', '合格', '2024-02-20', '有');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'RQ101_230520', 'RQ101高阻計.pdf','合格', '2023-05-20', '無');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'RQ101_240520', 'RQ101高阻計.pdf','合格', '2024-05-20', '無');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'VQ301_230420', 'VQ301數位電表.pdf','合格', '2023-04-20', '無');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'VQ301_240420', 'VQ301數位電表.pdf','合格', '2024-04-20', '無');