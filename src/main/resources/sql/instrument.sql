USE calibration;	

DROP TABLE IF EXISTS instrument;	
CREATE TABLE instrument (		
  instrument_id INT AUTO_INCREMENT COMMENT 'id',
  number VARCHAR(32) COMMENT '儀器編號',
  name VARCHAR(32) COMMENT '名稱',
  type VARCHAR(32) COMMENT '型式',
  characteristic VARCHAR(32) COMMENT '特性',
  unit VARCHAR(32) COMMENT '單位',
  cycle INT COMMENT '週期',
  calibrate_type VARCHAR(32) COMMENT '校驗類型',
  calibrate_localation VARCHAR(32) COMMENT '校驗地點',
  calibrate_month INT COMMENT '校驗月份',
  last_calibrate_date DATE COMMENT '上次校驗時間',
  mother_instrument_number VARCHAR(32) COMMENT '母儀編號',
  PRIMARY KEY (instrument_id)
);

INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number) 
		VALUES ('CQ119', '游標卡尺', 'Mitutoyo 200mm', '長度', 'mm', 6, 
				'內校', 'BQC', 1, '2024-1-18', '41QBB99303F');
INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number) 
		VALUES ('CQ219', '游標卡尺', 'Mitutoyo 200mm', '長度', 'mm', 6, 
				'內校', 'BQC', 1, '2024-1-18', '41QBB99303F');
INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number) 
		VALUES ('CQ318', '游標卡尺', 'Mitutoyo 200mm', '長度', 'mm', 6, 
				'內校', 'BQC', 1, '2024-1-18', '41QBB99303F');
