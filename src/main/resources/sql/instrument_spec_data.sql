USE calibration;	

DROP TABLE IF EXISTS instrument_spec_data;

CREATE TABLE instrument_spec_data (
	instrument_id INT NOT NULL,
	spec_id INT NOT NULL,
    data_id INT NOT NULL,
	PRIMARY KEY (instrument_id, spec_id, data_id),
	
	FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id) ON DELETE CASCADE,
	FOREIGN KEY (spec_id) REFERENCES spec (spec_id) ON DELETE CASCADE,
    FOREIGN KEY (data_id) REFERENCES data (data_id) ON DELETE CASCADE
) ;

INSERT INTO instrument_spec_data VALUES 
(1,1,1),
(1,2,2),
(1,3,3),
(2,1,1),
(2,2,2),
(2,3,3),
(3,1,1),
(3,2,2),
(3,3,3)