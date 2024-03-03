package chien.myweb.calibration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Instrument;
import chien.myweb.calibration.enity.RequestChecked;
import chien.myweb.calibration.service.InstrumentService;
import chien.myweb.calibration.service.PersonService;

@RestController
@RequestMapping("/crud")
public class InstrumentController {
	
	@Autowired
	InstrumentService instrumentService;
	@Autowired
	PersonService personService;
	
	@GetMapping("/instrumentId/{id}")  
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
	public ResponseEntity<List<Instrument>> getInstruments(){
		
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
		
		List<String> calibrateLocation = instrumentService.findInstrumentByLocation();	
		List<String> calibratePerson = personService.findPersonByCheck();

		instrumentMap.put("calibrateLocation", calibrateLocation);
		instrumentMap.put("calibratePerson", calibratePerson);
		
		if(!instrumentMap.isEmpty()){		
			return ResponseEntity.ok().body(instrumentMap); 
		}
		else{
			System.out.println("資料庫沒有內容");
	        return ResponseEntity.notFound().build();
	    }
	}
}
