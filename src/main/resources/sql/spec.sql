USE calibration;	

DROP TABLE IF EXISTS spec;	
CREATE TABLE spec (		
  spec_id INT AUTO_INCREMENT COMMENT 'id',
  specification DOUBLE COMMENT '規格',
  USL DOUBLE COMMENT '規格上限',
  LSL DOUBLE COMMENT '規格下限',
  
  PRIMARY KEY (spec_id)
);
INSERT INTO spec ( specification, USL, LSL) VALUES ( 20.0, 20.02, 19.98);
INSERT INTO spec ( specification, USL, LSL) VALUES ( 50.0, 50.02, 49.98);
INSERT INTO spec ( specification, USL, LSL) VALUES ( 100.0, 100.02, 99.98);