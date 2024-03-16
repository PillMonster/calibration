package chien.myweb.calibration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.SpecDao;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.Spec;

@Service
public class SpecServiceImpl implements SpecService{

	@Autowired
	SpecDao specDao;
	
	@Override
	public Spec addSpec(Spec spec) {
		return specDao.save(spec);
	}
	
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
	public List<Spec> findBySpecAndUSLAndLSL(Double specification, Double USL, Double LSL){
		return specDao.findBySpecAndUSLAndLSL(specification, USL, LSL);
	}

	@Override
	public List<Spec> findSpecAll() {
		// TODO Auto-generated method stub
		return specDao.findSpecAll();
	}
	
	@Override
	public List<Spec> findSpecByInstrumentId(Long id){
		return specDao.findSpecByInstrumentId(id);
	}
	
}
