package chien.myweb.calibration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentDao;
import chien.myweb.calibration.enity.Instrument;

@Service
public class InstrumentServiceImpl implements InstrumentService{

	@Autowired
	InstrumentDao instrumentDao;
	
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
	public List<String> findInstrumentByLocation(){
		// TODO Auto-generated method stub
		return instrumentDao.findInstrumentByLocation();
	}
}
