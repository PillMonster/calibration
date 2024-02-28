package chien.myweb.calibration.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.PersonDao;
import chien.myweb.calibration.enity.Person;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	PersonDao personDao;
	
	Map<String, Set<String>> personMap = new HashMap<>();
	Set<String> usernameSet = new HashSet<>();
	Set<String> departmentSet = new HashSet<>();
	Set<String> identitySet = new HashSet<>();
	
	@Override
	public List<Person> findPersonById(Long id) {
		// TODO Auto-generated method stub
		return personDao.findByPersonId(id);
	}

	@Override
	public List<Person> findJobnumber(String jobnumber) {
		// TODO Auto-generated method stub
		return personDao.findByJobnumber(jobnumber);
	}

	@Override
	public List<Person> findUsername(String username) {
		// TODO Auto-generated method stub
		return personDao.findByUsername(username);
	}

	@Override
	public List<Person> findPassword(String password) {
		// TODO Auto-generated method stub
		return personDao.findByPassword(password);
	}
	

	@Override
	public List<Person> findByDepartmente(String department) {
		// TODO Auto-generated method stub
		return personDao.findByDepartmente(department);
	}

	@Override
	public List<Person> findByidentity(String identity) {
		// TODO Auto-generated method stub
		return personDao.findByidentity(identity);
	}

	@Override
	public List<Person> findPersonAll() {
		// TODO Auto-generated method stub
		return personDao.findPersons();
	}
	
	
	@Override
	public Map<String, Set<String>> findPersonAllNoRepeat() {
		
		List<Person> persons = personDao.findPersons();
		// 遍歷原始集合，提取姓名並存儲到新的 List 中
		for (Person person : persons) {
			usernameSet.add(person.getUsername());
			departmentSet.add(person.getDepartment());
			identitySet.add(person.getIdentity());
		}
		personMap.put("username", usernameSet);
		personMap.put("department", departmentSet);
		personMap.put("identity", identitySet);
		
		return personMap;
	}

}
