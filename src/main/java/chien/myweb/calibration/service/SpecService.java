package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Spec;

public interface SpecService {
	
	// 單一查詢(透過ID)
	List<Spec> findSpecById(Long id);
		
	// 單一查詢(透過number)
	List<Spec> findSpecification(Double specification);
		
	// 查詢(全部)
	List<Spec> findSpecAll();
}
