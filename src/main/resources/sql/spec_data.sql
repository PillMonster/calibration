USE calibration;	

DROP TABLE IF EXISTS spec_data;

CREATE TABLE spec_data (
	spec_id INT NOT NULL,
	data_id INT NOT NULL,
	PRIMARY KEY (spec_id, data_id),
	
	FOREIGN KEY (spec_id) REFERENCES spec (spec_id) ON DELETE CASCADE,
	FOREIGN KEY (data_id) REFERENCES data (data_id) ON DELETE CASCADE
) ;

INSERT INTO instrument_spec VALUES 
(1,1),
(1,2),
(1,3),
(2,1),
(2,2),
(2,3),
(3,1),
(3,2),
(3,3);
