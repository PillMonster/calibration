package chien.myweb.calibration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.InstrumentPersonDao;
import chien.myweb.calibration.dao.InstrumentSpecDataDao;
import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.RequestData;

@Service
public class InstrumentPersonServiceImpl implements InstrumentPersonService{

	@Autowired
	InstrumentPersonDao instrumentPersonDao;


	@Override
	public List<Person> findPersonByInstrumentId(Long id) {
		
		return instrumentPersonDao.findPersonByInstrumentId(id);
	}

	

}
