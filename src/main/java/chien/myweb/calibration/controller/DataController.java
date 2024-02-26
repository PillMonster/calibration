package chien.myweb.calibration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Data;
import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.RequestData;
import chien.myweb.calibration.service.DataService;

@RestController
@RequestMapping("/crud")
public class DataController {
	
	@Autowired
	DataService dataService;
	
	@GetMapping("/dataId/{id}")  
	public ResponseEntity<Data> getDataById(@PathVariable("id") Long id){
		
		List<Data> dataDB = dataService.findByDataId(id);	

		Optional<Data> dataOp = dataDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(dataOp.isPresent()){
			Data data = dataOp.get();
			System.out.println(data.toString());
			return ResponseEntity.ok().body(data); 
		}
		else{
			System.out.println("沒有數據");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/result/{result}")  
	public ResponseEntity<List<Data>> getResult(@PathVariable("result") String result){
		
		List<Data> dataDB = dataService.findByResult(result);	
		
		Optional<Data> dataOp = dataDB.stream().findAny();
		
		if(dataOp.isPresent()){
			
			dataDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(dataDB); 
		}
		else{
			System.out.println("沒有數據");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/calibrateDate")  
	public ResponseEntity<List<Data>> getCalibrateDate(@RequestBody Data request){
		
		List<Data> dataDB = dataService.findCalibrateDate(request);	
		
		Optional<Data> dataOp = dataDB.stream().findAny();
		
		if(dataOp.isPresent()){
			
			dataDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(dataDB); 
		}
		else{
			System.out.println("沒有數據");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/dataAll")  
	public ResponseEntity<List<Data>> getResult(){
		
		List<Data> dataDB = dataService.findDataAll();	
		
		Optional<Data> dataOp = dataDB.stream().findAny();
		
		if(dataOp.isPresent()){
			
			dataDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(dataDB); 
		}
		else{
			System.out.println("沒有數據");
	        return ResponseEntity.notFound().build();
	    }
	}
}
