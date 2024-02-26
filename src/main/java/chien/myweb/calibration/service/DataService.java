package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Data;

public interface DataService {
	
	//單一查詢 (by ID)
	List<Data> findByDataId(Long id);
	
	 //單一查詢 (by Result)
    List<Data> findByResult(String result);
    
    //查詢時間
    List<Data> findCalibrateDate(Data request);
    
    //查詢全部
    List<Data> findDataAll();
}
