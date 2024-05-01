package chien.myweb.calibration.service;

import java.util.List;
import java.util.Map;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.RequestData;

public interface DataService {
	
	// 新增
	List<Data> addData(Data request);
	
	//單一查詢 (by ID)
	List<Data> findByDataId(Long id);
	
	 //單一查詢 (by Result)
    List<Data> findByResult(String result);
    
    //查詢時間
    List<Data> findCalibrateDate(Data request);
    
    //查詢全部
    List<Data> findDataAll();

}
