USE calibration;	

DROP TABLE IF EXISTS instrument_report;

CREATE TABLE instrument_report (
	instrument_id INT NOT NULL,
	report_id INT NOT NULL,
	PRIMARY KEY (instrument_id, report_id),
	/*
	FOREIGN KEY 為「外鍵」，REFERENCES 為「參照」
	ON DELETE CASCADE：
	  當我們想要刪除有被其他table的外鍵參照的column時，是無法刪除的，
	比如此處我們想刪除report中的資料，但由於instrument_report中有外鍵參照它，所以無法刪除。
	  此時，我們只需在創建report表時，加上ON DELETE CASCADE即可，
	當刪除report中的資料，同時也會將其刪除report對應的資料。
	*/	
	FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id) ON DELETE CASCADE,
	FOREIGN KEY (report_id) REFERENCES report (report_id) ON DELETE CASCADE
) ;

INSERT INTO instrument_report VALUES 
(5,1),(6,2);