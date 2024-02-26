USE calibration;	

DROP TABLE IF EXISTS instrument_spec_value;

CREATE TABLE instrument_spec_value (
	instrument_id INT NOT NULL,
	spec_id INT NOT NULL,
    data_id INT NOT NULL,
	PRIMARY KEY (instrument_id, spec_id, data_id),
	
	FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id) ON DELETE CASCADE,
	FOREIGN KEY (spec_id) REFERENCES spec (spec_id) ON DELETE CASCADE,
    FOREIGN KEY (data_id) REFERENCES data (data_id) ON DELETE CASCADE
) ;

INSERT INTO instrument_spec_value VALUES 
(1,1,1),
(1,1,4),
(1,2,2),
(1,2,5),
(1,3,3),
(1,3,6),
(2,1,1),
(2,1,4),
(2,2,2),
(2,2,5),
(2,3,3),
(2,3,6);