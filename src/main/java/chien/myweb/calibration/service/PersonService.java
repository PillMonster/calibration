package chien.myweb.calibration.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import chien.myweb.calibration.enity.Person;


public interface PersonService {
	
	// 單一查詢(透過ID)
	List<Person> findPersonById(Long id);
		
	// 單一查詢(透過jobnumber)
	List<Person> findJobnumber(String jobnumber);
	
	// 單一查詢(透過username)
	List<Person> findUsername(String username);
	
	// 單一查詢(透過department)
	List<Person> findByDepartmente(String department);
	
	// 單一查詢(透過identity)
	List<Person> findByidentity(String identity);
		
	// 單一查詢(透過password)
	List<Person> findPassword(String password);
		
	// 查詢(全部)
	List<Person> findPersonAll();
	
	// 查詢(全部)
	Map<String, Set<String>>  findPersonAllNoRepeat();
	
}
