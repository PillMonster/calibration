package chien.myweb.calibration.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.DataDao;
import chien.myweb.calibration.dao.InstrumentDao;
import chien.myweb.calibration.dao.InstrumentSpecDataDao;
import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.RequestData;

@Service
public class DataServiceImpl implements DataService{
	
	@Autowired
	DataDao dataDao;
	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	InstrumentSpecDataDao instrumentSpecDataDao;
	
	// ========== 新增 ==========
	@Override
	public List<Data> addData(Data request) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		List<Data> newData = new ArrayList<>();	// 用來存放更新後的數據初始List
		
		int listLength = request.getValueList().size(); // 量測數據陣列長度大小
		
		int createResult = 0; // 創建結果初始值(新增儀器、規格、數據的關聯紀錄)
		
		// =====取得多個量測值 ====
		for (int i=0;  i<listLength; i++) {	
			
			Data data = new Data();
			
			data.setResult(request.getResult());
			data.setCalibrate_date(request.getCalibrate_date());
			data.setTemperature(request.getTemperature());
			data.setHumidity(request.getHumidity());
			data.setValue(Double.parseDouble(request.getValueList().get(i)));
			
			dataDao.save(data); // 存入資料庫
			
			newData.add(data); // 將物件傳入list，用來傳回前端
			
			Long instrumentId = request.getId();
			Long specId = Long.parseLong(request.getSpecIdList().get(i));
			Long dataId = data.getId();
			
			// 新增儀器、規格、數據的關聯紀錄
			createResult = instrumentSpecDataDao.addInstrumentAndSpecAndData(instrumentId, specId, dataId); 

			System.out.println("新增數據id: " + data.getId() + ", 量測值: " + data.getValue());		
		}
		
		// ===== 更新上次校驗日期 =====
		if (createResult == 1) { 
			
			List<Instrument> instrumentDB = instrumentDao.findByInstrumentId(request.getId());
			
			Optional<Instrument> instrumentOp = instrumentDB.stream()
					.filter(p -> p.getId().equals(request.getId()))
					.findFirst();

			if(instrumentOp.isPresent()){
				
				Instrument updateInstrument = instrumentOp.get(); // 取得該物件
				LocalDate calibrate_date = LocalDate.parse(request.getCalibrate_date(), formatter); // 轉換格式(String to LocalDate)

				updateInstrument.setLast_calibrate_date(calibrate_date); // 設定該物件的校驗日期
				updateInstrument.setIs_calibration("Y"); // 設定該物件已校驗完成
				updateInstrument.setIs_sign("N"); // 設定該物件待簽核
				
				instrumentDao.save(updateInstrument); // 更新資料庫			
			}
			else{
				System.out.println("沒有此儀器或量具。");
		    }
		}
		else {
			System.out.println("新增儀器、規格、數據的關聯紀錄失敗。");
		}
		
		return newData;
	}

	@Override
	public List<Data> findByDataId(Long id) {
		// TODO Auto-generated method stub
		return dataDao.findByDataId(id);
	}

	@Override
	public List<Data> findByResult(String result) {
		// TODO Auto-generated method stub
		return dataDao.findByResult(result);
	}

	@Override
	public List<Data> findCalibrateDate(Data request) {
		String calibrate_date = request.getCalibrate_date();
		return dataDao.findCalibrateDate(calibrate_date);
	}

	@Override
	public List<Data> findDataAll() {
		// TODO Auto-generated method stub
		return dataDao.findDataAll();
	}
	
	@Override
	public List<Long> findDataIdByCalibrateDate(Long instrument_id, String last_calibrate_date) {
		// TODO Auto-generated method stub
		return dataDao.findDataIdByCalibrateDate(instrument_id, last_calibrate_date);
	}

}
