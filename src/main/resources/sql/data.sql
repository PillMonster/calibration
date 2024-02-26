USE calibration;	

DROP TABLE IF EXISTS data;	
CREATE TABLE data (		
  data_id INT AUTO_INCREMENT COMMENT 'id',
  value DOUBLE COMMENT '測量數值',
  result VARCHAR(32) COMMENT '校驗結果',
  calibrate_date DATE COMMENT '校驗日期',
  
  PRIMARY KEY (data_id)
);
INSERT INTO data ( value, result, calibrate_date) VALUES ( 20.01, '正常', '2023-7-18');
INSERT INTO data ( value, result, calibrate_date) VALUES ( 50.00, '正常', '2023-7-18');
INSERT INTO data ( value, result, calibrate_date) VALUES ( 150.01, '正常', '2023-7-18');
INSERT INTO data ( value, result, calibrate_date) VALUES ( 20.01, '正常', '2024-1-18');
INSERT INTO data ( value, result, calibrate_date) VALUES ( 50.01, '正常', '2024-1-18');
INSERT INTO data ( value, result, calibrate_date) VALUES ( 150.00, '正常', '2024-1-18');