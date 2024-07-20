package chien.myweb.calibration.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentDao;
import chien.myweb.calibration.dao.PersonDao;
import chien.myweb.calibration.dao.CalibrationDao;
import chien.myweb.calibration.dao.DataDao;
import chien.myweb.calibration.dao.SpecDao;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.enity.Calibration;
import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Spec;

@Service
public class InstrumentServiceImpl implements InstrumentService{

	@Autowired
	InstrumentDao instrumentDao;
	@Autowired
	PersonDao personDao;
	@Autowired
	SpecDao specDao;
	@Autowired
	SpecService specService;
	@Autowired
	DataDao dataDao;
	@Autowired
	CalibrationService calibrationService;
	
	// ========== 新增內校器具 ==========
	@Override
	public Instrument addInnerInstrument(RequestData request) { 
		
		String cycle = request.getCycle().replaceAll("[^0-9]", "");
		List<String> calibrate_month_list = request.getCalibrate_month();
		String calibrate_month = String.join(",", calibrate_month_list);
		
		// ===== 設定儀器資訊 ====
		Instrument instrument = new Instrument();
		
		instrument.setNumber(request.getNumber()); 
		instrument.setName(request.getName());
		instrument.setType(request.getType());
		instrument.setCharacteristic(request.getCharacteristic());
		instrument.setUnit(request.getUnit());
		instrument.setCycle(cycle);
		instrument.setCalibrate_type(request.getCalibrate_type());
		instrument.setCalibrate_localation(request.getCalibrate_localation());
		instrument.setCalibrate_month(calibrate_month);
		instrument.setLast_calibrate_date(request.getLast_calibrate_date());
		instrument.setMother_instrument_number(request.getMother_instrument_number());
		instrument.setIs_calibration("N");
		instrument.setIs_sign("N");
		
		// ===== 設定人員資訊與建立關聯 =====
		List<Person> custos = personDao.findByUsername(request.getCustos());
		List<Person> custosLeader = personDao.findByUsername(request.getCustosLeader());
		List<Person> checker = personDao.findByUsername(request.getChecker());
		List<Person> checkerLeader = personDao.findByUsername(request.getCheckerLeader());
		
		instrument.getPersons().add(custos.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(custosLeader.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(checker.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(checkerLeader.get(0)); // 將儀器與現有人員建立關聯
		
		
		// ===== 設定規格資訊與建立關聯 =====
		List<Double> requestSpec = request.getSpecification(); // 先取得前端輸入的規格值
		//Spec specObj = new Spec();
	
		for(int i=0; i < requestSpec.size(); i++) {
			
			Double specification = request.getSpecification().get(i);
			Double USL = request.getUSL().get(i);
			Double LSL = request.getLSL().get(i);
			
			List<Spec> spec = specDao.findBySpecAndUSLAndLSL(specification, USL, LSL); // 讀取資料庫是否有此項規格
			
			if (spec.isEmpty()) { // 如果沒有此項規格，就新增一項規格
				//System.out.println(specification + ", "+ USL + ", " + LSL + "此項規格資料庫沒有建檔");
				Spec specObj = new Spec();
				specObj.setSpecification(specification);
				specObj.setUSL(USL);
				specObj.setLSL(LSL);
				specService.addSpec(specObj);
				instrument.getSpec().add(specObj); // 將儀器與新建立的規格建立關聯
			}
			else { // 如果有此項規格，就將儀器與現有規格建立關聯
				//System.out.println(specification + ", "+ USL + ", " + LSL + "此項規格資料庫有建檔");
				instrument.getSpec().add(spec.get(0));	
			}
		}	
		return instrumentDao.save(instrument);
		//return null;
	}
	
	// ========== 新增遊外校器具 ==========
	@Override
	public Instrument addOutsideInstrument(RequestData request) { 
		
		String cycle = request.getCycle().replaceAll("[^0-9]", "");
		List<String> calibrate_month_list = request.getCalibrate_month();
		String calibrate_month = String.join(",", calibrate_month_list);

		Instrument instrument = new Instrument();
		
		instrument.setNumber(request.getNumber()); 
		instrument.setName(request.getName());
		instrument.setType(request.getType());
		instrument.setCharacteristic(request.getCharacteristic());
		instrument.setUnit(request.getUnit());
		instrument.setCycle(cycle);
		instrument.setCalibrate_type(request.getCalibrate_type());
		instrument.setCalibrate_localation(request.getCalibrate_localation());
		instrument.setCalibrate_month(calibrate_month);
		instrument.setLast_calibrate_date(request.getLast_calibrate_date());
		instrument.setMother_instrument_number(request.getMother_instrument_number());
		instrument.setIs_calibration("N");
		instrument.setIs_sign("N");
		
		List<Person> custos = personDao.findByUsername(request.getCustos());
		List<Person> custosLeader = personDao.findByUsername(request.getCustosLeader());
		List<Person> checker = personDao.findByUsername(request.getChecker());
		List<Person> checkerLeader = personDao.findByUsername(request.getCheckerLeader());
		
		instrument.getPersons().add(custos.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(custosLeader.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(checker.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(checkerLeader.get(0)); // 將儀器與現有人員建立關聯

		return instrumentDao.save(instrument);
	}
	
	// ========== 修改內校器具 ==========
	@Override
	public Instrument updateInnerInstrument(Long id, RequestData request) {

		Set<Long> newPersonIds = new TreeSet<>(); // 宣告Set集合: 存放更新後人員的id
		
		Set<Long> newSpecIds = new TreeSet<>(); // 宣告Set集合: 存放更新後規格的id

		List<Instrument> instrumentDB = findInstrumentById(id);	 // 透過前端得到儀器id, 並取得該儀器的物件
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		Optional<Instrument> instrumentOp = instrumentDB.stream() 
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(instrumentOp.isPresent()){

			Instrument updateInstrument = instrumentOp.get(); // 取得當前id的儀器
			
			// ===== instrument update service =====	
			// ===== 取得當前儀器對應的data id的進行日期更新 =====
			String befCalibrateDate = updateInstrument.getLast_calibrate_date().format(formatter); // 轉換字串格式
			String afferCalibrateDate = request.getLast_calibrate_date().format(formatter);
			
			System.out.println("befCalibrateDate: " + befCalibrateDate);
			System.out.println("afferCalibrateDate: " + afferCalibrateDate);
			
			List<Long> dataIdByCalibrateDate = dataDao.findDataIdForSomeDate(id, befCalibrateDate); // 取得修改前日期下的data_id
			
			for (Long dataId : dataIdByCalibrateDate) {
	
				List<Data> dataList = dataDao.findByDataId(dataId);
				Data dataObj = dataList.get(0);
				//System.out.println(dataObj.getId());
				dataObj.setCalibrate_date(afferCalibrateDate); // 設定修改後日期的data object
				dataDao.save(dataObj); // 這裡使用 save 進行更新
			}
			
			
			String cycle = request.getCycle().replaceAll("[^0-9]", "");
			List<String> calibrate_month_list = request.getCalibrate_month();
			String calibrate_month = String.join(",", calibrate_month_list);

			// ===== 取得當前儀器的物件進行更新 =====
			updateInstrument.setNumber(request.getNumber()); 
			updateInstrument.setName(request.getName());
			updateInstrument.setType(request.getType());
			updateInstrument.setCharacteristic(request.getCharacteristic());
			updateInstrument.setUnit(request.getUnit());
			updateInstrument.setCycle(cycle);
			updateInstrument.setCalibrate_type(request.getCalibrate_type());
			updateInstrument.setCalibrate_localation(request.getCalibrate_localation());
			updateInstrument.setCalibrate_month(calibrate_month);
			updateInstrument.setLast_calibrate_date(request.getLast_calibrate_date());
			updateInstrument.setMother_instrument_number(request.getMother_instrument_number());
		
			
			// ===== person update service =====
			// 取得該儀器當前的person id
			Set<Long> currentPersonIds = updateInstrument.getPersons().stream()
						                .map(Person::getId)
						                .collect(Collectors.toSet());
			
			// 取得該儀器更新後的person id
			newPersonIds.add(personDao.findPersonIdByUsername(request.getCustos()));
			newPersonIds.add(personDao.findPersonIdByUsername(request.getCustosLeader()));
			newPersonIds.add(personDao.findPersonIdByUsername(request.getChecker()));
			newPersonIds.add(personDao.findPersonIdByUsername(request.getCheckerLeader()));
			
			System.out.println("===== person service =====");
			System.out.println("currentPersonIds: " + currentPersonIds);
			System.out.println("newPersonIds: " + newPersonIds);
			
			// 該儀器要新增的person id
			Set<Long> personsToAdd = newPersonIds.stream()
				                    .filter(personId -> !currentPersonIds.contains(personId))
				                    .collect(Collectors.toSet());
			
			// 該儀器要刪除的person id
			Set<Long> personsToRemove = currentPersonIds.stream()
			                           .filter(personId -> !newPersonIds.contains(personId))
			                           .collect(Collectors.toSet());
			
			System.out.println("personsToAdd: " + personsToAdd);
			System.out.println("personToRemove: " + personsToRemove);
			
			// 更新儀器的人員關聯紀錄
	        for (Long personId : personsToAdd) { // 儀器與更新後的人員新增關聯紀錄
	        	
	            Optional<Person> addPerson = personDao.findById(personId);
	            
	            if(addPerson.isPresent()){
	            	updateInstrument.getPersons().add(addPerson.get());
	            }    
	        }

	        for (Long personId : personsToRemove) { // 儀器與更新前的人員移除關聯紀錄
	        	
	        	Optional<Person> deletePerson = personDao.findById(personId);
	        	
	        	if(deletePerson.isPresent()){
	        		updateInstrument.getPersons().remove(deletePerson.get());
	        	}
	        }
	        
	        // ===== spec service =====
			List<Double> requestSpec = request.getSpecification(); // 先取得前端輸入的規格值
			
			Double specification = 0.0; // 前端輸入規格初始值
			Double USL = 0.0; // 前端輸入USL初始值
			Double LSL = 0.0;  // 前端輸入LSL初始值
			
			for(int i=0; i < requestSpec.size(); i++) { // 遍歷所有規格值
				
				specification = request.getSpecification().get(i); // 重前端取得每一項規格
				USL = request.getUSL().get(i); // 重前端取得每一項USL
				LSL = request.getLSL().get(i); // 重前端取得每一項LSL
				
				List<Spec> spec = specDao.findBySpecAndUSLAndLSL(specification, USL, LSL); // 從資料庫尋找是否有此項規格
				
				if (spec.isEmpty()) { // 如果沒有此項規格，就新增一項規格
					System.out.println("沒有 " + specification + " 此項規格");
					
					Spec specObj = new Spec(); // 建立物件
					specObj.setSpecification(specification);
					specObj.setUSL(USL);
					specObj.setLSL(LSL);
					specService.addSpec(specObj); // 新增一項規格至資料庫
					// 從資料庫找出前端輸入的spec id，並存入newSpecIds集合中，即更新後的spec id
					//newSpecIds.add(specDao.findSpedIdBySpecAndUSLAndLSL(specification, USL, LSL));
					
				}
				else {	
					// 從資料庫找出前端輸入的spec id，並存入newSpecIds集合中，即更新後的spec id
					//newSpecIds.add(specDao.findSpedIdBySpecAndUSLAndLSL(specification, USL, LSL));		
				}
				
				// 從資料庫找出前端輸入的spec id，並存入newSpecIds集合中，即更新後的spec id
				newSpecIds.add(specDao.findSpedIdBySpecAndUSLAndLSL(specification, USL, LSL));
				
			}
			
			// 取得該儀器當前的spec id
			Set<Long> currentSpecIds = updateInstrument.getSpec().stream()
						                .map(Spec::getId)
						                .collect(Collectors.toSet());
			
			// 該儀器要新增的spec id
			Set<Long> specToAdd = newSpecIds.stream()
				                    .filter(specId -> !currentSpecIds.contains(specId))
				                    .collect(Collectors.toSet());
			
			// 該儀器要刪除的spec id
			Set<Long> specToRemove = currentSpecIds.stream()
			                           .filter(specId -> !newSpecIds.contains(specId))
			                           .collect(Collectors.toSet());
			
			System.out.println("===== spec service =====");
			System.out.println("currentSpecIds: " + currentSpecIds);
			System.out.println("newSpecIds: " + newSpecIds);
			System.out.println("specToAdd: " + specToAdd);
			System.out.println("specToRemove: " + specToRemove);
	
			// 更新儀器的規格關聯紀錄
	        for (Long specId : specToAdd) { // 儀器與更新後的規格新增關聯紀錄
	        	
	            Optional<Spec> addSpec = specDao.findById(specId);
	            
	            if(addSpec.isPresent()){
	            	updateInstrument.getSpec().add(addSpec.get());
	            }    
	        }

	        for (Long specId : specToRemove) { // 儀器與更新前的規格移除關聯紀錄
	        	
	        	Optional<Spec> deleteSpec = specDao.findById(specId);
	        	
	        	if(deleteSpec.isPresent()){
	        		updateInstrument.getSpec().remove(deleteSpec.get());
	        	}
	        }
	
			//return null;
			return instrumentDao.save(updateInstrument); // 這裡使用 save 進行更新
		}
		else{
			return null;
	    }
	}
	
	// ========== 修改遊外校器具 ==========
	@Override
	public Instrument updateOutsideInstrument(Long id, RequestData request) {

		Set<Long> newPersonIds = new TreeSet<>(); // 宣告Set集合: 存放更新後人員的id
		List<Instrument> instrumentDB = findInstrumentById(id);	 // 透過前端得到儀器id, 並取得該儀器的物件
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		Optional<Instrument> instrumentOp = instrumentDB.stream() 
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(instrumentOp.isPresent()){

			Instrument updateInstrument = instrumentOp.get(); // 取得當前id的儀器
			
			// ===== instrument update service =====
			String cycle = request.getCycle().replaceAll("[^0-9]", "");
			List<String> calibrate_month_list = request.getCalibrate_month();
			String calibrate_month = String.join(",", calibrate_month_list);

			// ===== 取得當前儀器的物件進行更新 =====
			updateInstrument.setNumber(request.getNumber()); 
			updateInstrument.setName(request.getName());
			updateInstrument.setType(request.getType());
			updateInstrument.setCharacteristic(request.getCharacteristic());
			updateInstrument.setUnit(request.getUnit());
			updateInstrument.setCycle(cycle);
			updateInstrument.setCalibrate_type(request.getCalibrate_type());
			updateInstrument.setCalibrate_localation(request.getCalibrate_localation());
			updateInstrument.setCalibrate_month(calibrate_month);
			updateInstrument.setLast_calibrate_date(request.getLast_calibrate_date());
			updateInstrument.setMother_instrument_number(request.getMother_instrument_number());
	
			// ===== person update service =====
			// 取得該儀器當前的person id
			Set<Long> currentPersonIds = updateInstrument.getPersons().stream()
						                .map(Person::getId)
						                .collect(Collectors.toSet());
			
			// 取得該儀器更新後的person id
			newPersonIds.add(personDao.findPersonIdByUsername(request.getCustos()));
			newPersonIds.add(personDao.findPersonIdByUsername(request.getCustosLeader()));
			newPersonIds.add(personDao.findPersonIdByUsername(request.getChecker()));
			newPersonIds.add(personDao.findPersonIdByUsername(request.getCheckerLeader()));
			
			System.out.println("===== person service =====");
			System.out.println("currentPersonIds: " + currentPersonIds);
			System.out.println("newPersonIds: " + newPersonIds);
			
			// 該儀器要新增的person id
			Set<Long> personsToAdd = newPersonIds.stream()
				                    .filter(personId -> !currentPersonIds.contains(personId))
				                    .collect(Collectors.toSet());
			
			// 該儀器要刪除的person id
			Set<Long> personsToRemove = currentPersonIds.stream()
			                           .filter(personId -> !newPersonIds.contains(personId))
			                           .collect(Collectors.toSet());
			
			System.out.println("personsToAdd: " + personsToAdd);
			System.out.println("personToRemove: " + personsToRemove);
			
			// 更新儀器的人員關聯紀錄
	        for (Long personId : personsToAdd) { // 儀器與更新後的人員新增關聯紀錄
	        	
	            Optional<Person> addPerson = personDao.findById(personId);
	            
	            if(addPerson.isPresent()){
	            	updateInstrument.getPersons().add(addPerson.get());
	            }    
	        }

	        for (Long personId : personsToRemove) { // 儀器與更新前的人員移除關聯紀錄
	        	
	        	Optional<Person> deletePerson = personDao.findById(personId);
	        	
	        	if(deletePerson.isPresent()){
	        		updateInstrument.getPersons().remove(deletePerson.get());
	        	}
	        } 
			//return null;
			return instrumentDao.save(updateInstrument); // 這裡使用 save 進行更新
		}
		else{
			return null;
	    }
	}
	

	// ========== 更新簽核 ==========
	@Override
	public Instrument updateInstrumentBySign(Instrument instrument) {
		return instrumentDao.save(instrument); // 這裡使用 save 進行更新
	}
	
	// ========== 刪除 ==========
	@Override
	public void deleteInstrumentById(Long id) {
		instrumentDao.deleteById(id);
	}
	
	// ========== 查詢 ==========
	@Override
	public List<Instrument> findInstrumentById(Long id) {
		
		return instrumentDao.findByInstrumentId(id);
	}
		
	@Override
	public List<Instrument> findInstrumentByNumber(String number) {
		// TODO Auto-generated method stub
		return instrumentDao.findByInstrumentNumber(number);
	}

	@Override
	public List<Instrument> findInstrumentByName(String name) {
		// TODO Auto-generated method stub
		return instrumentDao.findByInstrumentName(name);
	}

	@Override
	public List<Instrument> findInstruments() {
		// TODO Auto-generated method stub
		return instrumentDao.findInstruments();
	}
	
	@Override
	public List<String> findInstrumentByLocalation(){
		// TODO Auto-generated method stub
		return instrumentDao.findInstrumentByLocalation();
	}

	@Override
	public List<Instrument> findByMultiple(List<String> monthList, List<String> cycleList, List<String> typeList, List<String> personList, List<String> localationList) {
		
		String monthRegex = "(" + String.join("|", monthList) + ")";
		//System.out.println(monthRegex);
		return instrumentDao.findByMultiple(monthRegex, cycleList, typeList, personList, localationList);
	}
	

}
