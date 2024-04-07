package chien.myweb.calibration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.RequestChecked;
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.enity.Spec;
import chien.myweb.calibration.service.InstrumentPersonService;
import chien.myweb.calibration.service.InstrumentService;
import chien.myweb.calibration.service.PersonService;
import chien.myweb.calibration.service.SpecService;

@RestController
@RequestMapping("/crud")
public class InstrumentController {
	
	@Autowired
	InstrumentService instrumentService;
	@Autowired
	PersonService personService;
	@Autowired
	InstrumentPersonService instrumentPersonService;
	@Autowired
	SpecService specService;
	
	@PostMapping("/instrument") // 新增
	public ResponseEntity<Instrument> createInstrument(@RequestBody RequestData request){
			
		System.out.println(request.toString());
		Instrument instrument = instrumentService.addInstrument(request);

		return ResponseEntity.ok().body(instrument); 
	}
	
	@PutMapping("/instrument/{id}") // 修改
	public ResponseEntity<String> updateInstrument(@PathVariable("id") Long id, @RequestBody RequestData request){
		
		System.out.println(request.toString());
		instrumentService.updateInstrument(id, request);	

		return new ResponseEntity<String>("更新儀器資訊成功", HttpStatus.CREATED); 
	}	
	
	@GetMapping("/instrumen/{id}")  // 查詢
	public ResponseEntity<Instrument> getInstrumentById(@PathVariable("id") Long id){
		
		List<Instrument> instrumentDB = instrumentService.findInstrumentById(id);	
		
		Optional<Instrument> instrumentOp = instrumentDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();

		if(instrumentOp.isPresent()){
			Instrument instrument = instrumentOp.get();
			System.out.println(instrument.toString());
			return ResponseEntity.ok().body(instrument); 
		}
		else{
			System.out.println("沒有此儀器或量具");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@DeleteMapping("/instrument/{id}") // 刪除
	public ResponseEntity<String> deleteInstrumentById(@PathVariable("id") Long id){	
		instrumentService.deleteInstrumentById(id);
		return new ResponseEntity<>("第 " + id + " 筆刪除成功", HttpStatus.CREATED); 
	}
	
	@GetMapping("/instrumentInfo/{id}")  
	public ResponseEntity<Map<String, List<?>>> getInstrumentInfoById(@PathVariable("id") Long id){
		
		System.out.println("get edit's id: " + id);
		
		Map<String, List<?>> instrumentInfoMap = new HashMap<>();	
		
		List<Instrument> instrumentDB = instrumentService.findInstrumentById(id);	
		List<Person> personDB = instrumentPersonService.findPersonByInstrumentId(id);
		List<Spec> specDB = specService.findSpecByInstrumentId(id);
		

		if(!instrumentDB.isEmpty() && !personDB.isEmpty()){
			instrumentInfoMap.put("instrument", new ArrayList<>(instrumentDB) );
			instrumentInfoMap.put("persons", new ArrayList<>(personDB) );
			instrumentInfoMap.put("spec", new ArrayList<>(specDB) );
			
			return ResponseEntity.ok().body(instrumentInfoMap); 
		}
		
		else{
			System.out.println("找不到此儀器資訊");
	        return ResponseEntity.notFound().build();
	    }
		
		//instrumentInfoMap.put("instrument", new ArrayList<>(instrumentDB) );
		//return ResponseEntity.ok().body(instrumentInfoMap); 
	}
	
	@GetMapping("/instrumentNo/{number}")  
	public ResponseEntity<List<Instrument>> getInstrumentByNumberd(@PathVariable("number") String number){
		System.out.println("number: " + number);
		List<Instrument> instrumentDB = instrumentService.findInstrumentByNumber(number);	
		
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();
		
		if(instrumentOp.isPresent()){
			
			instrumentDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(instrumentDB); 
		}
		else{
			System.out.println("沒有此儀器或量具");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/instrumentName/{name}")  
	public ResponseEntity<List<Instrument>> getInstrumentById(@PathVariable("name") String name){
		System.out.println("name: " + name);
		List<Instrument> instrumentDB = instrumentService.findInstrumentByName(name);	
		
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();

		
		if(instrumentOp.isPresent()){
			
			instrumentDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(instrumentDB); 
		}
		else{
			System.out.println("沒有此儀器或量具");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	//@CrossOrigin(origins = "http://localhost:8080/instrument/show")
	@GetMapping("/instruments") 
	public ResponseEntity<?> getInstruments(){
		
		List<Instrument> instrumentDB = instrumentService.findInstruments();
 
		Optional<Instrument> instrumentOp = instrumentDB.stream().findAny();
		
		if (instrumentOp.isPresent()) {
			
			instrumentDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(instrumentDB);
		}
		else {
			System.out.println("資料庫沒有內容");
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/searchInstrument")
	public ResponseEntity<List<Instrument>> getSerchResult(@RequestBody List<RequestChecked> requestChecked){
		
		List<String> monthList = new ArrayList<>();
        List<String> cycleList = new ArrayList<>();
        List<String> typeList = new ArrayList<>();
        List<String> personList = new ArrayList<>();
        List<String> localationList = new ArrayList<>();
        
        for (RequestChecked jsonData : requestChecked) {
        	monthList = jsonData.getMonth();
            cycleList = jsonData.getCycle();
            typeList = jsonData.getType();
            personList = jsonData.getPerson();
            localationList = jsonData.getLocalation();
            
            System.out.println("====== From 前端 ======");
			System.out.println("校驗月份 from 前端: " + monthList);
	    	System.out.println("校驗週期 from 前端: " + cycleList);
	    	System.out.println("校驗類型 from 前端: " +  typeList);
	    	System.out.println("校驗人員 from 前端: " + personList);
	    	System.out.println("校驗地點or公司 from 前端: " + localationList);
	    	System.out.println("-----------------------");
        }
        
        List<Instrument> instrumentList = instrumentService.findByMultiple(monthList, cycleList, typeList, personList, localationList); 

		return ResponseEntity.ok().body(instrumentList);
	}
	
	@GetMapping("/instrumentNoRepeat")  // 前端下拉式選單的內容
	public ResponseEntity<Map<String, List<String>>> instrumentNoRepeat() {
		
		Map<String, List<String>> instrumentMap = new HashMap<>();	
		
		List<String> localation = instrumentService.findInstrumentByLocalation();	
		List<String> custos = personService.findPersonByCustos();
		List<String> custosLeader = personService.findPersonByCustosLeader();
		List<String> checker = personService.findPersonByChecker();
		List<String> checkerLeader = personService.findPersonByCheckerLeader();

		instrumentMap.put("localation", localation);
		instrumentMap.put("custos", custos);
		instrumentMap.put("custosLeader", custosLeader);
		instrumentMap.put("checker", checker);
		instrumentMap.put("checkerLeader", checkerLeader);
		
		if(!instrumentMap.isEmpty()){		
			return ResponseEntity.ok().body(instrumentMap); 
		}
		else{
			System.out.println("資料庫沒有內容");
	        return ResponseEntity.notFound().build();
	    }
	}
}
