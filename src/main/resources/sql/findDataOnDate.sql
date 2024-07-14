SELECT data.data_id FROM data
JOIN instrument_spec_data ON data.data_id = instrument_spec_data.data_id
JOIN instrument ON instrument_spec_data.instrument_id = instrument.instrument_id
WHERE instrument_spec_data.instrument_id = '1' AND data.calibrate_date = instrument.last_calibrate_date;