package chien.myweb.calibration.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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
import chien.myweb.calibration.dao.CalibrationDao;
import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Calibration;
import chien.myweb.calibration.enity.Spec;

@Service
public class CalibrationServiceImpl implements CalibrationService{
	
	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	CalibrationDao calibrationDao;
	
	
	@Override
	public Map<String, Object> findCalibrationResult(Long id) {  // 查詢單一儀器校驗結果	
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		Instrument instrument = new Instrument();
		
		// ===== 取得儀器資訊 =====
		List<Instrument> instrumentDB = instrumentDao.findByInstrumentId(id);

		Optional<Instrument> instrumentOp = instrumentDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();

		if(instrumentOp.isPresent()){
			instrument = instrumentOp.get();
		}
		else{
			System.out.println("沒有此儀器或量具");
	    }
        
			
		ArrayList calibrationDataByDateList = new ArrayList<>(); // List初始化，存放多個校驗日期之校驗結果
		Map<String, Object> instrumentResultMap = new LinkedHashMap<>();	// Map初始化，存放一個器具的資訊及校驗結果，Linked為先進先出排列

		List<Date> reponseDate = calibrationDao.findDistinctCalibrateDateByInstrumentId(id);
		Collections.sort(reponseDate); 
		
        for (Date d : reponseDate) {
        	
        	String calibrateDate  = df.format(d); // date轉換為String
        	
        	// 取出資料庫的內容，存放在Object類別的reponseObject
        	List<Object[]> reponseObject = calibrationDao.findDistinctByInstrumentIdAndCalibrateDate(id, calibrateDate);
        	
        	
        	Calibration calibrationDataObj = new Calibration(); // 物件初始化，存放一個物件
        	ArrayList calibrationDataList = new ArrayList<>(); // List初始化，存放多個物件
        	
            for (Object[] object : reponseObject ) {

            	String date  = df.format(object[0]);
            	Double spec  = (Double)object[1];
            	Double usl = (Double)object[2];
            	Double lsl = (Double)object[3];
            	Double value = (Double)object[4];
            	String temp = (String)object[5];
            	String humidity = (String)object[6];
            	String result = (String)object[7];

            	calibrationDataObj = new Calibration(date, spec, usl, lsl, value, temp, humidity, result); // 單一物件(一個規格)注入建構元
            	calibrationDataList.add(calibrationDataObj); // 多個物件(不同規格)放入一個List(一個日期)
            }
            calibrationDataByDateList.add(calibrationDataList); // 多個List(多個日期)放入一個List
	
			instrumentResultMap.put("instrumentInfo", instrument); // 一個儀器資訊(Object)
			instrumentResultMap.put("calibrationResult", calibrationDataByDateList); // 多個校驗結果

		}
		
		return instrumentResultMap;	
	}
	
	
	@Override
	public List<Instrument> findPrepInstruments() {  // 查詢待校驗器具
		
		
		
		List<Instrument> instrumentDB = instrumentDao.findInstruments();
				
        LocalDate currentDate = LocalDate.now(); // 獲取當前日期
        Month currentMonth = currentDate.getMonth();
        int currentMonthValue = currentMonth.getValue();
        System.out.println("當前月份為: " + currentMonthValue);
        
		List<Instrument> prepInstrument = instrumentDB.stream()
            .filter(prep -> {
            	
            	// ========== 方法1: 使用器具排定的校驗月份, 判斷是否與當前月份相同, 或取出資料庫中的校驗欄位, 判斷是否已校驗 ==========
            	/*boolean isOverdue = false;
            	
            	String number = prep.getNumber(); // 取得此儀器的編號
            	String calibrateMonth = prep.getCalibrate_month();
            	String isCalibrate = prep.getIs_calibration();   	
            	
            	String[] month = calibrateMonth.split(",");
            	System.out.print("編號: " + number);
            	
            	for (String m : month) {
            		System.out.print(", 校驗月份為: " + m);
            		
            		int objMonthNumber = Integer.parseInt(m);	
            		
            		isOverdue = (objMonthNumber == currentMonthValue) || (isCalibrate.equals("N"));
     
            	}
            	System.out.println();
            	
            	if (isOverdue) {
                    return isOverdue;
                } else {
                    return isOverdue;
                }*/
            	
            	// ========== 方法2: 透過上次校驗日期與現在日期, 判斷相差是否大於週期 ==========	
            	String number = prep.getNumber(); // 取得此儀器的編號
            	LocalDate lastCalibrateDate = prep.getLast_calibrate_date(); // 取得此儀器的上次校驗日期
            	Long cycle =  Long.parseLong(prep.getCycle()) * 30; // 取得此儀器的校驗週期
            	
                // 計算日期相差的月份
                long monthsDifference = ChronoUnit.MONTHS.between(lastCalibrateDate, currentDate);
                //System.out.println("儀器 " + number + " 的上次校驗日期與現在日期相差 " + monthsDifference + " 個月");
                
                LocalDate today = LocalDate.now();
                long daysBetween = ChronoUnit.DAYS.between(lastCalibrateDate, today);
                long dayDifferenc = cycle - daysBetween ;
                
                System.out.println("編號: " + number + ", 週期: " + cycle + ", 天數相差: " + daysBetween + ", 兩者相減: " + dayDifferenc);
                // 檢查是否超過週期
                //boolean isOverdue = monthsDifference >= cycle -1;
                boolean isOverdue = dayDifferenc <= 30 ;
                //System.out.println("編號: " + number + ", 離校驗日相差: " +  monthsDifference  + " 個月, 是否超過週期: " + isOverdue);
                
                // 如果超過週期就回傳 true，否則回傳 false
                if (isOverdue) {
                	//prep.setIs_calibration("Y");
                	//instrumentDao.save(prep); // 更新資料庫
                    return isOverdue;
                } else {
                    return isOverdue;
                }	
            				
            })
            .collect(Collectors.toList());	// 如果器具有超過週期，將結果轉換成集合資料
		
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
	
	/*@Override
	public List<Map> findCalibrationResult() {  // 查詢所有儀器校驗結果		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		
		List<Map> cablibrationResultList = new ArrayList<>(); //  List初始化，存放所有器具的資訊及校驗結果
		
		List<Instrument> instrumentDB = instrumentDao.findInstruments(); 
        
		for(Instrument instrument : instrumentDB) {
			
			ArrayList calibrationDataByDateList = new ArrayList<>(); // List初始化，存放多個校驗日期之校驗結果
			Map<String, Object> instrumentMap = new LinkedHashMap<>();	// Map初始化，存放一個器具的資訊及校驗結果，Linked為先進先出排列

			List<Date> reponseDate = calibrationDao.findDistinctCalibrateDateByInstrumentId(instrument.getId());
			Collections.sort(reponseDate); 
			
	        for (Date d : reponseDate) {
	        	
	        	String calibrateDate  = df.format(d); // date轉換為String
	        	
	        	// 取出資料庫的內容，存放在Object類別的reponseObject
	        	List<Object[]> reponseObject = calibrationDao.findDistinctByInstrumentIdAndCalibrateDate(instrument.getId(), calibrateDate);
	        	
	        	
	        	Calibration calibrationDataObj = new Calibration(); // 物件初始化，存放一個物件
	        	ArrayList calibrationDataList = new ArrayList<>(); // List初始化，存放多個物件
	        	
	            for (Object[] object : reponseObject ) {

	            	String date  = df.format(object[0]);
	            	Double spec  = (Double)object[1];
	            	Double usl = (Double)object[2];
	            	Double lsl = (Double)object[3];
	            	Double value = (Double)object[4];
	            	String temp = (String)object[5];
	            	String humidity = (String)object[6];
	            	String result = (String)object[7];

	            	calibrationDataObj = new Calibration(date, spec, usl, lsl, value, temp, humidity, result); // 單一物件(一個規格)注入建構元
	            	calibrationDataList.add(calibrationDataObj); // 多個物件(不同規格)放入一個List(一個日期)
	            }
	            calibrationDataByDateList.add(calibrationDataList); // 多個List(多個日期)放入一個List
	            
	        }
			
			instrumentMap.put("instrumentInfo", instrument); // 一個儀器資訊
			instrumentMap.put("calibrationResult", calibrationDataByDateList); // 一個校驗結果

			cablibrationResultList.add(instrumentMap); // 多個儀器資訊及校驗結果
		}
		
		return cablibrationResultList;	
	}*/
}
