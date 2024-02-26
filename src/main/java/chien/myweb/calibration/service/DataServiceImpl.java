package chien.myweb.calibration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.DataDao;
import chien.myweb.calibration.enity.Data;

@Service
public class DataServiceImpl implements DataService{
	
	@Autowired DataDao dataDao;

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
}
