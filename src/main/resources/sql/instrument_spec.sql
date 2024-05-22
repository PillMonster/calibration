USE calibration;	

DROP TABLE IF EXISTS instrument_spec;

CREATE TABLE instrument_spec (
	instrument_id INT NOT NULL,
	spec_id INT NOT NULL,
	PRIMARY KEY (instrument_id, spec_id),
	
	FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id) ON DELETE CASCADE,
	FOREIGN KEY (spec_id) REFERENCES spec (spec_id) ON DELETE CASCADE
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
