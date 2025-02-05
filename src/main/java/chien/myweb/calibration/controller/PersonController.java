package chien.myweb.calibration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

import chien.myweb.calibration.enity.Person;
import chien.myweb.calibration.service.PersonService;

@RestController
@RequestMapping("/crud")
public class PersonController {
	
	@Autowired
	PersonService personService;

	@PostMapping("/person") // 新增
	public ResponseEntity<String> createPerson(@RequestBody Person person) {
		
		String job_number = person.getJob_number();
		String password = person.getPassword();
		String confirmPassword = person.getConfirmPassword();
		String identity = person.getIdentity();
		
		if( personService.validateAccount_number(job_number) == false){
			System.out.println("工號必須為英文或數字，不得包含任何符號。");
			return new ResponseEntity<String>("工號必須為英文或數字，不得包含任何符號。", HttpStatus.OK);
			
		} if (identity.equals("校驗人員") || identity.equals("校驗主管")) {
			
			if( personService.validatePassword(password) == false){
				System.out.println("密碼必須為英文或數字，不得包含任何符號，且長度介於8~16位。");
				return new ResponseEntity<String>("密碼必須要有英文或數字，不得包含任何符號，且長度介於8~16位。", HttpStatus.OK);
			} if (!password.equals(confirmPassword)) { 
				return new ResponseEntity<String>("密碼輸入不一致。", HttpStatus.OK);
			} 
		}
		
		Person savePerson = personService.addPerson(person); // 新增
		
		if (savePerson != null && savePerson.getId() != null) {
			System.out.println(person.toString());
			return new ResponseEntity<String>("新增人員成功，姓名: " + savePerson.getUsername(), HttpStatus.CREATED); 
		}
		else{
	        return new ResponseEntity<String>("新增失敗", HttpStatus.INTERNAL_SERVER_ERROR);
	    }	
	}
	
	@PutMapping("/person/{id}") // 修改
	public ResponseEntity<String> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
		
		String job_number = person.getJob_number();
		String password = person.getPassword();
		String confirmPassword = person.getConfirmPassword();
		String identity = person.getIdentity();
		
		System.out.println("取得id為: " + person.getId());
		System.out.println("取得姓名為: " + person.getUsername());
		System.out.println("取得工號為: " + person.getJob_number());
		if( personService.validateAccount_number(job_number) == false){
			System.out.println("工號必須為英文或數字，不得包含任何符號。");
			return new ResponseEntity<String>("工號必須為英文或數字，不得包含任何符號。", HttpStatus.OK);
			
		} if (identity.equals("校驗人員") || identity.equals("校驗主管")) {
			
			if( personService.validatePassword(password) == false){
				System.out.println("密碼必須為英文或數字，不得包含任何符號，且長度介於8~16位。");
				return new ResponseEntity<String>("密碼必須要有英文或數字，不得包含任何符號，且長度介於8~16位。", HttpStatus.OK);
			} if (!password.equals(confirmPassword)) { 
				return new ResponseEntity<String>("密碼輸入不一致。", HttpStatus.OK);
			} 
		}
		
		Person updatePerson = personService.updatePerson(id, person); // 更新
		
		if (updatePerson != null && updatePerson.getId() != null) {
			System.out.println(person.toString());
			return new ResponseEntity<>("更新人員資訊成功", HttpStatus.CREATED); 
		}
		else{
	        return new ResponseEntity<>("更新失敗", HttpStatus.NOT_FOUND);
	    }	
	}
	
	@DeleteMapping("/person/{id}") // 刪除
	public ResponseEntity<String> deletePerson(@PathVariable("id") Long id) {
		personService.deletePerson(id); // 刪除
		return new ResponseEntity<>("第 " + id + " 筆刪除成功", HttpStatus.CREATED); 
	}
	
	@GetMapping("/person/{id}")  
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
			System.out.println("查詢不到人員ID");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/personJobNumber/{jobnumber}")  // 查詢
	public ResponseEntity<Person> getPersonJobNumber(@PathVariable("jobnumber") String jobnumber){
		
		List<Person> personDB = personService.findJobnumber(jobnumber);	
		
		Optional<Person> personOp = personDB.stream()
				.filter(p -> p.getJob_number().equals(jobnumber))
				.findFirst();
		
		if(personOp.isPresent()){
			Person person = personOp.get();
			System.out.println("員工工號已被建立: " + person.toString());
			return ResponseEntity.ok().body(person); 
		}
		else{
			System.out.println("員工工號尚未建立，可以建立。");
			return ResponseEntity.ok(null);
	    }
	}
	
	@GetMapping("/personPassword/{email}")  
	public ResponseEntity<String> getPersonPassWoed(@PathVariable("email") String email){
		
		String password = personService.findPasswordByEmail(email);	

		if(password != null){
			
			System.out.println(password);
			return ResponseEntity.ok().body(password); 
		}
		else{
			System.out.println("查詢不到人員密碼");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/persons")  
	public ResponseEntity<List<Person>> getPersons() {
		
		List<Person> personDB = personService.findPersonAll();	
		
		Optional<Person> personOp = personDB.stream().findAny();
		
		if(personOp.isPresent()){
			
			//personDB.forEach(item -> System.out.println(item.toString()));
			
			return ResponseEntity.ok().body(personDB); 
		}
		else{
			System.out.println("資料庫沒有內容");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping("/searchPerson") // 前端條件查詢
	public ResponseEntity<List<Person>> getSerchResult(@RequestBody Person person){
		
		if (person.getUsername() != null && person.getDepartment() == null && person.getIdentity() == null) {
			//System.out.println(person.getUsername());
			List<Person> personDB = personService.findUsername(person.getUsername());
			return ResponseEntity.ok().body(personDB); 
		}
		
		else if (person.getUsername() == null && person.getDepartment() != null && person.getIdentity() == null) {
			//System.out.println(person.getDepartment());
			List<Person> personDB = personService.findByDepartmente(person.getDepartment());
			return ResponseEntity.ok().body(personDB); 
			
		}else if (person.getUsername() == null && person.getDepartment() == null && person.getIdentity() != null) {
			//System.out.println(person.getIdentity());
			List<Person> personDB = personService.findByidentity(person.getIdentity());
			return ResponseEntity.ok().body(personDB); 
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/personNoRepeat")  // 前端下拉式選單的內容
	public ResponseEntity<Map<String, Set<String>>> personNoRepeat() {
		
		Map<String, Set<String>> personMap = personService.findPersonAllNoRepeat();	

		if(!personMap.isEmpty()){		
			return ResponseEntity.ok().body(personMap); 
		}
		else{
			System.out.println("資料庫沒有內容");
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/person/checkLeader/{id}")  // 查詢該器具的校驗主管
	public ResponseEntity<Map<String, String>> getCheckerLeaderName(@PathVariable("id") Long id) {
		
		Map<String, String> response = new HashMap<>(); // 前端如ajax如有設置dataType:"json"，必須包在Map裡面才是JSON結構
		
		String checkerLeader = personService.findCheckerLeaderByInstrumentId(id);
		System.out.println("checkerLeader: " + checkerLeader);
		if(checkerLeader != null && !checkerLeader.trim().isEmpty()){
			response.put("checkerLeader", checkerLeader);
			return ResponseEntity.ok().body(response); 
		}
		else{
			System.out.println("沒有資料");
			response.put("error", "沒有資料");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // httpstatus=500
	    }
	}
	
}
