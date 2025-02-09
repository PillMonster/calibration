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

import org.springframework.aop.IntroductionAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentDao;
import chien.myweb.calibration.dao.CalibrationDao;
import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Report;
import chien.myweb.calibration.enity.Calibration;
import chien.myweb.calibration.enity.Spec;

@Service
public class CalibrationServiceImpl implements CalibrationService{
	
	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	CalibrationDao calibrationDao;
	
	
	// ========== 查詢單一儀器外校結果 ==========
	@Override
	public Map<String, Object> findOutsideCalibrationResult(Long id) {  
		
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

		List<Date> reponseDate = calibrationDao.findDistinctOutsideCalibrateDateByInstrumentId(id);
		Collections.sort(reponseDate); 
		
        for (Date d : reponseDate) {
        	
        	String calibrateDate  = df.format(d); // date轉換為String
        	
        	// 取出資料庫的內容，存放在Object類別的reponseObject
        	// Object[] -> 存放多個欄位
        	// List<> -> 存放多個物件
        	List<Object[]> reponseObject = calibrationDao.findDistinctOutsideByInstrumentIdAndCalibrateDate(id, calibrateDate);
        		
        	Report reportObj = new Report(); // 物件初始化，存放一個物件
        	ArrayList calibrationDataList = new ArrayList<>(); // List初始化，存放多個物件
        	
            for (Object[] object : reponseObject ) {
            	
            	Integer report_id = (Integer)object[0];
            	String report_no = (String)object[1];
            	String report_name = (String)object[2];
            	String calibrate_date  = df.format(object[3]);
            	String result  = (String)object[4];
            	String is_taf  = (String)object[5];
            	
            	Long Longid = Long.valueOf(report_id.longValue());
            	
            	reportObj = new Report(Longid, report_no, report_name, calibrate_date, result, is_taf);  // 注入建構元
            	calibrationDataList.add(reportObj); // 多個物件(多個外校資訊)放入一個List(一個日期)
            }
            calibrationDataByDateList.add(calibrationDataList); // 多個List(多個日期)放入一個List
	
			instrumentResultMap.put("instrumentInfo", instrument); // 一個儀器資訊(Object)
			instrumentResultMap.put("calibrationResult", calibrationDataByDateList); // 多個校驗結果
		}
		
		return instrumentResultMap;	
	}
		
	
	// ========== 查詢單一儀器校驗結果 ==========
	@Override
	public Map<String, Object> findCalibrationResult(Long id) {  
		
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
        	// Object[] -> 存放多個欄位
        	// List<> -> 存放多個物件
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
	
	// ========== 計算天數確認器具是否需要校驗 ==========
	@Override
	public List<Instrument> calCalibrateDate(List<Instrument> instrumentDB) { 
		
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
                //long monthsDifference = ChronoUnit.MONTHS.between(lastCalibrateDate, currentDate);
                //System.out.println("儀器 " + number + " 的上次校驗日期與現在日期相差 " + monthsDifference + " 個月");
                
                LocalDate today = LocalDate.now();
                long daysBetween = ChronoUnit.DAYS.between(lastCalibrateDate, today);
                long dayDifferenc = cycle - daysBetween ;
                
                System.out.println("編號: " + number + ", 週期: " + cycle + "天, 上次校驗日今日相差: " + daysBetween + "天, 兩者相減: " + dayDifferenc);
                
                // 檢查是否小於30天
                boolean isOverdue = dayDifferenc <= 30 ;
                
                // 如果小於30天就回傳 true，否則回傳 false
                if (isOverdue) {
                    return isOverdue;
                } else { 
                    return isOverdue;
                }	
            			
            })
            .collect(Collectors.toList());	// 如果器具有超過週期，將結果轉換成集合資料
		return prepInstrument;	
	}
	
	// ========== 查詢單一器具校驗天數是否小於30天，如果有代表需要校驗 ==========
	@Override
	public boolean is_calibrate(Instrument instrument) { 
		
		LocalDate currentDate = LocalDate.now(); // 獲取當前日期
        Month currentMonth = currentDate.getMonth();
        int currentMonthValue = currentMonth.getValue();
        System.out.println("當前月份為: " + currentMonthValue);

        	// ========== 方法2: 透過上次校驗日期與現在日期, 判斷相差是否大於週期 ==========	
        	String number = instrument.getNumber(); // 取得此儀器的編號
        	LocalDate lastCalibrateDate = instrument.getLast_calibrate_date(); // 取得此儀器的上次校驗日期
        	Long cycle =  Long.parseLong(instrument.getCycle()) * 30; // 取得此儀器的校驗週期
        	
            // 計算日期相差的月份
            //long monthsDifference = ChronoUnit.MONTHS.between(lastCalibrateDate, currentDate);
            //System.out.println("儀器 " + number + " 的上次校驗日期與現在日期相差 " + monthsDifference + " 個月");
            
            LocalDate today = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(lastCalibrateDate, today);
            long dayDifferenc = cycle - daysBetween ;
            
            //System.out.println("編號: " + number + ", 週期: " + cycle + "天, 上次校驗日今日相差: " + daysBetween + "天, 兩者相減: " + dayDifferenc);
            
            // 檢查是否小於30天
            boolean isOverdue = dayDifferenc <= 30 ;
            
            // 如果小於30天就回傳 true，否則回傳 false
            if (isOverdue) {	
                return isOverdue;
            } else { 
                return isOverdue;
            }	
	}
	
	// ========== 查詢待校驗器具 ==========
	@Override
	public List<Instrument> findPrepInstruments() {  
		
		List<Instrument> instrumentDB = instrumentDao.findInstruments();	
		return calCalibrateDate(instrumentDB);
	}
	
	// ========== 查詢待校驗器具 + 前端選擇條件查詢 ==========
	@Override
	public List<Instrument> selectPrepInstruments(List<Instrument> instrumentDB) {  

		return calCalibrateDate(instrumentDB);
	}
		
	// ========== 查詢待簽核器具 ==========
	@Override
	public List<Instrument> findPrepSignInstrument() {
		return instrumentDao.findSignByInstruments();
	}
	
}
