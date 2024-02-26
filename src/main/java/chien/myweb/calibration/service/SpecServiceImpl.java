package chien.myweb.calibration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.SpecDao;
import chien.myweb.calibration.enity.Spec;

@Service
public class SpecServiceImpl implements SpecService{

	@Autowired
	SpecDao specDao;
	
	@Override
	public List<Spec> findSpecById(Long id) {
		// TODO Auto-generated method stub
		return specDao.findBySpecId(id);
	}

	@Override
	public List<Spec> findSpecification(Double specification) {
		// TODO Auto-generated method stub
		return specDao.findBySpecification(specification);
	}

	@Override
	public List<Spec> findSpecAll() {
		// TODO Auto-generated method stub
		return specDao.findSpecAll();
	}
	
}
