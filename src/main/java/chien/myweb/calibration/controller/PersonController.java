package chien.myweb.calibration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.enity.Spec;
import chien.myweb.calibration.service.PersonService;

@RestController
@RequestMapping("/crud")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	@GetMapping("/personId/{id}")  
	public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id){
		
		List<Person> personDB = personService.findPersonById(id);	

		Optional<Person> personOp = personDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(personOp.isPresent()){
			Person person = personOp.get();
			System.out.println(person.toString());
			return ResponseEntity.ok().body(person); 
		}
		else{
			System.out.println("找不到此規格");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/personJobNumber/{jobnumber}")  
	public ResponseEntity<Person> getPersonJobNumber(@PathVariable("jobnumber") String jobnumber){
		
		List<Person> personDB = personService.findJobnumber(jobnumber);	

		Optional<Person> personOp = personDB.stream()
				.filter(p -> p.getJob_number().equals(jobnumber))
				.findFirst();
		
		if(personOp.isPresent()){
			Person person = personOp.get();
			System.out.println(person.toString());
			return ResponseEntity.ok().body(person); 
		}
		else{
			System.out.println("找不到此規格");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/personPassword/{password}")  
	public ResponseEntity<Person> getPersonPassWoed(@PathVariable("password") String password){
		
		List<Person> personDB = personService.findPassword(password);	

		Optional<Person> personOp = personDB.stream()
				.filter(p -> p.getPassword().equals(password))
				.findFirst();
		
		if(personOp.isPresent()){
			Person person = personOp.get();
			System.out.println(person.toString());
			return ResponseEntity.ok().body(person); 
		}
		else{
			System.out.println("找不到此規格");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/persons")  
	public ResponseEntity<List<Person>> getPersons() {
		
		List<Person> personDB = personService.findSpecAll();	
		
		Optional<Person> personOp = personDB.stream().findAny();
		
		if(personOp.isPresent()){
			
			personDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(personDB); 
		}
		else{
			System.out.println("找不到此規格");
	        return ResponseEntity.notFound().build();
	    }
	}
}
