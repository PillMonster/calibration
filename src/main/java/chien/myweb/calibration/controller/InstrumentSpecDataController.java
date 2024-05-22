package chien.myweb.calibration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.service.InstrumentSpecDataService;

@RestController
@RequestMapping("/crud")
public class InstrumentSpecDataController {
	
	@Autowired
	InstrumentSpecDataService instrumentSpecDataService;
	
	@PostMapping("/instrument/{instrumentId}/spec/{specId}/data/{dataId}")
    public ResponseEntity<String> createInstrumentSpecData(@PathVariable Long instrumentId, @PathVariable Long specId,
    														@PathVariable Long dataId) {
		
		int createResult;
		String result = "";
		
		try {
			
			createResult = instrumentSpecDataService.addInstrumentAndSpecAndData(instrumentId, specId, dataId);
			result = createResult == 1 ? "插入成功" : "插入失敗";
			return new ResponseEntity<String>(result, HttpStatus.OK);
			
		}catch (DataIntegrityViolationException e){
			
			result = "找不到對應的資料" ;
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
			
		} finally {
			System.out.println(result);
		}

    }
}
