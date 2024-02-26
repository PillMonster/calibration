package chien.myweb.calibration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Spec;
import chien.myweb.calibration.service.SpecService;

@RestController
@RequestMapping("/crud")
public class SpecController {
	
	@Autowired
	SpecService specService;
	
	@GetMapping("/specId/{id}")  
	public ResponseEntity<Spec> getSpectById(@PathVariable("id") Long id){
		
		List<Spec> specDB = specService.findSpecById(id);	

		Optional<Spec> specOp = specDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(specOp.isPresent()){
			Spec spec = specOp.get();
			System.out.println(spec.toString());
			return ResponseEntity.ok().body(spec); 
		}
		else{
			System.out.println("找不到此規格");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/spec/{spec}")  
	public ResponseEntity<List<Spec>> getSpecification(@PathVariable("spec") Double spec){
		
		List<Spec> specDB = specService.findSpecification(spec);	
		
		Optional<Spec> specOp = specDB.stream().findAny();
		
		if(specOp.isPresent()){
			
			specDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(specDB); 
		}
		else{
			System.out.println("找不到此規格");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/specAll")  
	public ResponseEntity<List<Spec>> getSpecAll() {
		
		List<Spec> specDB = specService.findSpecAll();	
		
		Optional<Spec> specOp = specDB.stream().findAny();
		
		if(specOp.isPresent()){
			
			specDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(specDB); 
		}
		else{
			System.out.println("找不到此規格");
	        return ResponseEntity.notFound().build();
	    }
	}
}
