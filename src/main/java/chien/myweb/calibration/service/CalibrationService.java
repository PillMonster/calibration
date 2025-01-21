package chien.myweb.calibration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chien.myweb.calibration.enity.Instrument;

public interface CalibrationService{
	
	Map<String, Object> findOutsideCalibrationResult(Long id); // 查詢單一儀器外校結果
	
	Map<String, Object> findCalibrationResult(Long id); // 查詢單一儀器校驗結果
		
	List<Instrument> findPrepInstruments(); // 查詢待校驗器具
	
	List<Instrument> calCalibrateDate(List<Instrument> instrumentDB); // 計算天數確認器具是否需要校驗

	List<Instrument> findPrepSignInstrument(); // 查詢待簽核器具 

	List<Instrument> selectPrepInstruments(List<Instrument> instrumentDB); // 查詢待校驗器具 + 前端選擇條件查詢

	//boolean findIsCalibration(Instrument instrument); // 判斷器具是否過期
}
