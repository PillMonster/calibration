USE calibration;	

DROP TABLE IF EXISTS data;	
CREATE TABLE data (		
  data_id INT AUTO_INCREMENT COMMENT 'id',
  value DOUBLE COMMENT '測量數值',
  result VARCHAR(32) COMMENT '校驗結果',
  calibrate_date DATE COMMENT '校驗日期',
  temperature VARCHAR(32) COMMENT '溫度',
  humidity VARCHAR(32) COMMENT '濕度',
  
  PRIMARY KEY (data_id)
);
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 20.00, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 49.99, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 99.99, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 20.01, '合格', '2024-1-18', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 50.00, '合格', '2024-1-18', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 100.01, '合格', '2024-1-18', '24.2', '54.5%');

INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 19.99, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 49.99, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 100.00, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 20.01, '合格', '2024-1-18', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 50.01, '合格', '2024-1-18', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 100.00, '合格', '2024-1-18', '24.2', '54.5%');

INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 20.00, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 50.01, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 99.99, '合格', '2023-7-20', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 20.00, '合格', '2024-1-18', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 50.00, '合格', '2024-1-18', '24.2', '54.5%');
INSERT INTO data ( value, result, calibrate_date, temperature, humidity) VALUES ( 99.99, '合格', '2024-1-18', '24.2', '54.5%');