package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Spec;

public interface SpecService {
	
	// 新增
	Spec addSpec(Spec spec);
	
	// 單一查詢(透過ID)
	List<Spec> findSpecById(Long id);
		
	// 單一查詢(透過number)
	List<Spec> findSpecification(Double specification);
	
	// 多項條件查詢
	List<Spec> findBySpecAndUSLAndLSL(Double specification, Double USL, Double LSL);
	
	// 透過多項條件查詢ID (單一欄位)
	Long findSpedIdBySpecAndUSLAndLSL(Double specification, Double USL, Double LSL);

	// 查詢(全部)
	List<Spec> findSpecAll();
	
	// 透過instrument ID下查詢該儀器的規格(spec, USL, LSL)
	List<Spec> findSpecByInstrumentId(Long id);
}
