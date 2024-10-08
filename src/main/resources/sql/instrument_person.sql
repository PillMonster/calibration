USE calibration;	

DROP TABLE IF EXISTS instrument_person;

CREATE TABLE instrument_person (
	instrument_id INT NOT NULL,
	person_id INT NOT NULL,
	PRIMARY KEY (instrument_id, person_id),
	/*
	FOREIGN KEY 為「外鍵」，REFERENCES 為「參照」
	ON DELETE CASCADE：
	  當我們想要刪除有被其他table的外鍵參照的column時，是無法刪除的，
	比如此處我們想刪除person中的資料，但由於instrument_person中有外鍵參照它，所以無法刪除。
	  此時，我們只需在創建person表時，加上ON DELETE CASCADE即可，
	當刪除person中的資料，同時也會將其刪除person對應的資料。
	*/	
	FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id) ON DELETE CASCADE,
	FOREIGN KEY (person_id) REFERENCES persons (person_id) ON DELETE CASCADE
) ;

INSERT INTO instrument_person VALUES 
(1,1),(2,1),(3,1),(4,5),(5,1),(6,1),
(1,2),(2,2),(3,2),(4,6),(5,2),(6,2),
(1,3),(2,3),(3,3),(4,3),(5,7),(6,7),
(1,4),(2,4),(3,4),(4,4),(5,4),(6,4);