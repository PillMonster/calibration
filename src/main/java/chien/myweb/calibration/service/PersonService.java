package chien.myweb.calibration.service;

import java.util.List;

import chien.myweb.calibration.enity.Person;


public interface PersonService {
	
	// 單一查詢(透過ID)
	List<Person> findPersonById(Long id);
		
	// 單一查詢(透過jobnumber)
	List<Person> findJobnumber(String jobnumber);
	
	// 單一查詢(透過username)
	List<Person> findUsername(String username);
		
	// 單一查詢(透過password)
	List<Person> findPassword(String password);
		
	// 查詢(全部)
	List<Person> findSpecAll();
	
}
