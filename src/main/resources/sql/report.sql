USE calibration;	

DROP TABLE IF EXISTS report;
CREATE TABLE report (		
  report_id INT AUTO_INCREMENT COMMENT 'id',
  report_no VARCHAR(32) COMMENT '報告編號',
  result VARCHAR(32) COMMENT '校驗結果',
  calibrate_date DATE COMMENT '校驗日期',
  is_taf VARCHAR(32) COMMENT '是否有TAF',
  
  PRIMARY KEY (report_id)
);

INSERT INTO report ( report_no, result, calibrate_date, is_taf) VALUES ( '20240720001', '合格', '2023-2-20', 'Y');
INSERT INTO report ( report_no, result, calibrate_date, is_taf) VALUES ( '20240720002', '合格', '2023-7-20', 'Y');
INSERT INTO report ( report_no, result, calibrate_date, is_taf) VALUES ( '20240720003', '合格', '2024-2-20', 'N');