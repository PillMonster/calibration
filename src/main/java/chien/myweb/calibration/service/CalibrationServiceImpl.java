package chien.myweb.calibration.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentDao;
import chien.myweb.calibration.dao.ResponseDataDao;
import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.ResponseData;
import chien.myweb.calibration.enity.Spec;

@Service
public class CalibrationServiceImpl implements CalibrationService{
	
	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	ResponseDataDao responseDataDao;
	
	@Override
	public List<Map> findCalibrationResult() {  // 查詢待校驗器具	
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Map> cablibrationResultList = new ArrayList<>();
		List<Instrument> instrumentDB = instrumentDao.findInstruments();   
        
		for(Instrument instrument : instrumentDB) {
			
			Map<String, ArrayList> instrumentMap = new LinkedHashMap<>();
			
			ArrayList instrumentList = new ArrayList();
			
			ArrayList calibrationResultByDateList = new ArrayList<>();

			List<Date> reponseDate = responseDataDao.findDistinctCalibrateDateByInstrumentId(instrument.getId());
			
	        for (Date d : reponseDate) {
	        	
	        	String calibrateDate  = df.format(d); // date轉換為String
	        	
	        	// 取出資料庫的內容，存放在Object類別的reponseObject
	        	List<Object[]> reponseObject = responseDataDao.findDistinctByInstrumentIdAndCalibrateDate(instrument.getId(), calibrateDate);
	        	ResponseData responseData = new ResponseData();
	        	ArrayList responsDataList = new ArrayList<>();
	        	
	            for (Object[] object : reponseObject ) {

	            	String date  = df.format(object[0]);
	            	Double spec  = (Double)object[1];
	            	Double usl = (Double)object[2];
	            	Double lsl = (Double)object[3];
	            	Double value = (Double)object[4];
	            	String temp = (String)object[5];
	            	String humidity = (String)object[6];
	            	String result = (String)object[7];

	            	responseData = new ResponseData(date, spec, usl, lsl, value, temp, humidity, result);
	            	responsDataList.add(responseData);
	            }
	            calibrationResultByDateList.add(responsDataList);
	            
	        }
			
			instrumentList.add(instrument);
			
			instrumentMap.put("instrumentInfo", instrumentList);
			instrumentMap.put("calibrationResult", calibrationResultByDateList);

			cablibrationResultList.add(instrumentMap);
		}
		
		return cablibrationResultList;	
	}
	
	@Override
	public List<Instrument> findPrepInstruments() {  // 查詢待校驗器具
		
		List<Instrument> instrumentDB = instrumentDao.findInstruments();
				
        LocalDate currentDate = LocalDate.now(); // 獲取當前日期
        
		List<Instrument> prepInstrument = instrumentDB.stream()
            .filter(prep -> {
            	
            	String number = prep.getNumber(); // 取得此儀器的編號
            	
            	LocalDate lastCalibrateDate = prep.getLast_calibrate_date(); // 取得此儀器的上次校驗日期
            	int cycle =  Integer.parseInt(prep.getCycle()); // 取得此儀器的校驗週期
            	
                // 計算日期相差的月份
                long monthsDifference = ChronoUnit.MONTHS.between(lastCalibrateDate, currentDate);
                //System.out.println("儀器 " + number + " 的上次校驗日期與現在日期相差 " + monthsDifference + " 個月");
                
                // 檢查是否超過週期
                boolean isOverdue = monthsDifference >= cycle;
                
                // 如果超過週期就回傳 true，否則回傳 false
                if (isOverdue) {
                	prep.setIs_calibration("Y");

                	instrumentDao.save(prep); // 更新資料庫
                    return isOverdue;
                } else {
                    return isOverdue;
                }	
            				
            })
            .collect(Collectors.toList());	
		
		return prepInstrument;	
	}
	
	@Override
	public boolean findIsCalibration(Instrument instrument) { // 新增儀器時，判斷器具是否過期
		
		String number = instrument.getNumber(); // 取得此儀器的編號
    	
    	LocalDate lastCalibrateDate = instrument.getLast_calibrate_date(); // 取得此儀器的上次校驗日期
    	int cycle =  Integer.parseInt(instrument.getCycle()); // 取得此儀器的校驗週期
    	
    	// 獲取當前日期
        LocalDate currentDate = LocalDate.now();
        
        // 計算日期相差的月份
        long monthsDifference = ChronoUnit.MONTHS.between(lastCalibrateDate, currentDate);
        System.out.println("新增的儀器上次校驗月份與當前月份相差 " + monthsDifference + " 個月");
        
        // 檢查是否超過週期
        boolean isOverdue = monthsDifference >= cycle;
        
        // 如果超過週期就回傳 true，否則回傳 false
        if (isOverdue) {
            System.out.println("新增儀器編號: " + number + ", 儀器校驗已過期");
            return isOverdue;
        } else {
            System.out.println("新增儀器編號: " + number + ", 儀器校驗未過期");
            return isOverdue;
        }	
	}
	
	/*
	@Override
	public List<Instrument> findPrepInstruments() {
		
		List<Instrument> instrumentDB = instrumentDao.findInstruments();

		List<Instrument> prepInstrument = instrumentDB.stream()
            .filter(prep -> {
            	
            	String number = prep.getNumber(); // 取得每一個儀器的號碼
            	
            	String calibrateMonth = prep.getCalibrate_month(); // 取得每一個儀器的校驗月份

                int todayMonth = LocalDate.now().getMonthValue(); // 取出現在日期的月份
                
                String[] months = calibrateMonth.split(","); // 將 calibrateMonth 字串依逗號分割成字串陣列
                
                boolean isCurrentMonth = false;
                // 遍歷月份數組，逐一比較是否等於目前月份
                for (String month : months) {
                	
                    if (Integer.parseInt(month) == todayMonth) {
                    	System.out.println("儀器編號: " + number + ", 當月校驗月份: " +month); 
                        isCurrentMonth = true;
                        break;
                    }
                }
                return isCurrentMonth;
            })
            .collect(Collectors.toList());
		
		return prepInstrument;	
	}*/
	
	/*@Override
	public List<Instrument> findIsCalibration() {
		
		List<Instrument> instrumentDB = instrumentDao.findInstruments();

		List<Instrument> prepInstrument = instrumentDB.stream()
            .filter(prep -> {
            	
            	String number = prep.getNumber(); // 取得每一個儀器的號碼
            	
            	LocalDate lastCalibrateDate = prep.getLast_calibrate_date(); // 取得每一個儀器的上次校驗日期
            	int cycle =  Integer.parseInt(prep.getCycle()); // 取得每一個儀器的校驗週期
 
            	int calibrationMonth = lastCalibrateDate.plusMonths(cycle).getMonthValue(); // 校驗日期與週期相加，並取出月份
                int todayMonth = LocalDate.now().getMonthValue(); // 取出現在日期的月份
                
                System.out.println("儀器編號: " + number + ", 下次校驗月份: " + calibrationMonth + ", 目前月份: " + todayMonth);

                return calibrationMonth == todayMonth; // 判断下次校驗月份與目前月份是否相同
            })
            .collect(Collectors.toList());
		
		return prepInstrument;
		
	}*/

}
