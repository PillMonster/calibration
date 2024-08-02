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
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( '15353_230720', '15353示波器.pdf','合格', '2023-07-20', '有');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( '15353_240729', '15353示波器.pdf','合格', '2024-07-29', '有');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'XQ123_230217', 'XQ123電壓表.pdf','合格', '2023-02-17', '有');
INSERT INTO report ( report_no, report_name, result, calibrate_date, is_taf) VALUES ( 'XQ123_240219', 'XQ123電壓表.pdf','合格', '2024-02-19', '有');