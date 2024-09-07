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
  calibrate_month VARCHAR(32) COMMENT '校驗月份',
  last_calibrate_date DATE COMMENT '上次校驗時間',
  mother_instrument_number VARCHAR(32) COMMENT '母儀編號',
  is_calibration VARCHAR(32) COMMENT '是否校驗完成',
  is_sign VARCHAR(32) COMMENT '是否簽名完成',
  PRIMARY KEY (instrument_id)
);

INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number, is_calibration, is_sign) 
		VALUES ('CQ119', '游標卡尺', 'Mitutoyo 200mm', '長度', 'mm', 6, 
				'內校', 'BQC', '1,7', '2024-1-18', '41QBB99303F', 'N', 'N');
INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number, is_calibration, is_sign) 
		VALUES ('CQ219', '游標卡尺', 'Mitutoyo 200mm', '長度', 'mm', 6, 
				'內校', 'BQC', '1,7', '2024-1-18', '41QBB99303F', 'N', 'N');
INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number, is_calibration, is_sign) 
		VALUES ('CQ318', '游標卡尺', 'Mitutoyo 200mm', '長度', 'mm', 6, 
				'內校', 'BQC', '1,7', '2024-1-18', '41QBB99303F', 'N', 'N');
                
INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number, is_calibration, is_sign) 
		VALUES ('EQ003', '絕緣耐壓測試器', '0~1000V', '電壓', 'V', 12, 
				'外校', '商檢中心', '2', '2024-2-20', '', 'N', 'N');
INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number, is_calibration, is_sign) 
		VALUES ('RQ101', '高阻計', 'HIOKI IR4057', '歐姆', 'Ω', 12, 
				'外校', '商檢中心', '5', '2024-5-20', '', 'N', 'N');
INSERT INTO instrument (number, name, type, characteristic, unit, cycle, 
						calibrate_type, calibrate_localation, calibrate_month, 
                        last_calibrate_date, mother_instrument_number, is_calibration, is_sign) 
		VALUES ('VQ301', '數位電表', 'YOKOGAWA 7555', '電壓電流', 'V,mA', 12, 
				'外校', '商檢中心', '4', '2024-4-20', '', 'N', 'N');