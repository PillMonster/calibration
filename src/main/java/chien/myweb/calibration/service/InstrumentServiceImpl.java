package chien.myweb.calibration.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentDao;
import chien.myweb.calibration.dao.PersonDao;
import chien.myweb.calibration.dao.SpecDao;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.RequestData;
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

	@Override
	public Instrument addInstrument(RequestData request) {
		
		String cycle = request.getCycle().replaceAll("[^0-9]", "");
		String calibrate_month = request.getCalibrate_month().replaceAll("[^0-9]", "");
		
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
		
		List<Person> custos = personDao.findByUsername(request.getCustos());
		List<Person> custosLeader = personDao.findByUsername(request.getCustosLeader());
		List<Person> checker = personDao.findByUsername(request.getChecker());
		List<Person> checkerLeader = personDao.findByUsername(request.getCheckerLeader());
		
		instrument.getPersons().add(custos.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(custosLeader.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(checker.get(0)); // 將儀器與現有人員建立關聯
		instrument.getPersons().add(checkerLeader.get(0)); // 將儀器與現有人員建立關聯
		
		
		List<Double> requestSpec = request.getSpecification(); // 先取得前端輸入的規格值
	
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
			}
			else { // 如果有此項規格，就將儀器與現有規格建立關聯
				//System.out.println(specification + ", "+ USL + ", " + LSL + "此項規格資料庫有建檔");
				instrument.getSpec().add(spec.get(0));	
			}
		}
		
		return instrumentDao.save(instrument);
		//return null;
	}
	
	@Override
	public List<Instrument> findByMultiple(List<String> monthList, List<String> cycleList, List<String> typeList, List<String> personList, List<String> localationList) {
		return instrumentDao.findByMultiple(monthList, cycleList, typeList, personList, localationList);
	}
	
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

}
