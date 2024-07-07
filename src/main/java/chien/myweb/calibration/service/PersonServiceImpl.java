package chien.myweb.calibration.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.PersonDao;
import chien.myweb.calibration.enity.Person;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	PersonDao personDao;
	
	// ========== 新增 ==========
	@Override
	public Person addPerson(Person person) {
		return personDao.save(person);
	}
	
	
	// ========== 修改 ==========
	@Override
	public Person updatePerson(Long id, Person person) {
		
		List<Person> personDB = findPersonById(id);	
		
		Optional<Person> personOp = personDB.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst();
		
		if(personOp.isPresent()){

			Person updatePerson = personOp.get();
			updatePerson.setJob_number(person.getJob_number());
			updatePerson.setUsername(person.getUsername());
			updatePerson.setDepartment(person.getDepartment());
			updatePerson.setPosition(person.getPosition());
			updatePerson.setIdentity(person.getIdentity());
			updatePerson.setEmail(person.getEmail());
			updatePerson.setPassword(person.getPassword());

			return personDao.save(updatePerson); // 這裡使用 save 進行更新
		}
		else{
			return null;
	    }
	}
	
	// ========== 刪除 ==========
	@Override
	public void deletePerson(Long id) {
		personDao.deleteById(id);
	}
	
	
	// ========== 查詢 ==========
	@Override
	public List<Person> findPersonById(Long id) {
		return personDao.findByPersonId(id);
	}

	@Override
	public List<Person> findJobnumber(String jobnumber) {
		return personDao.findByJobnumber(jobnumber);
	}

	@Override
	public List<Person> findUsername(String username) {
		return personDao.findByUsername(username);
	}
	
	@Override
	public Long findPersonIdByUsername(String usernames){
		return personDao.findPersonIdByUsername(usernames);
	}

	@Override
	public List<Person> findPassword(String password) {
		return personDao.findByPassword(password);
	}
	

	@Override
	public List<Person> findByDepartmente(String department) {
		return personDao.findByDepartmente(department);
	}

	@Override
	public List<Person> findByidentity(String identity) {
		return personDao.findByidentity(identity);
	}

	@Override
	public List<Person> findPersonAll() {
		return personDao.findPersons();
	}
	
	@Override
	public List<String> findPersonByCustos() {
		List<String> custos = personDao.findPersonByCustos();
		Collections.sort(custos, (s1, s2) -> s1.compareTo(s2));
		return custos;
	}

	@Override
	public List<String> findPersonByCustosLeader() {
		List<String> custosLeader = personDao.findPersonByCustosLeader();
		Collections.sort(custosLeader, (s1, s2) -> s1.compareTo(s2));
		return custosLeader;
	}

	@Override
	public List<String> findPersonByChecker(){
		List<String> checker = personDao.findPersonByChecker();
		Collections.sort(checker, (s1, s2) -> s1.compareTo(s2));
		return checker;
	}
	
	@Override
	public List<String> findPersonByCheckerLeader() {
		List<String> checkerLeader = personDao.findPersonByCheckerLeader();
		Collections.sort(checkerLeader, (s1, s2) -> s1.compareTo(s2));
		return checkerLeader;
	}
	
	@Override
	public List<Person> findPersonByJobNumberAbdPassword(String job_number, String password) {
		return personDao.findPersonByJobNumberAbdPassword(job_number, password);
	}
	
	@Override
	public Map<String, Set<String>> findPersonAllNoRepeat() {
		
		Map<String, Set<String>> personMap = new HashMap<>();
		Set<String> usernameSet = new TreeSet<>(); // TreeSet: 不重複, 排序
		Set<String> departmentSet = new TreeSet<>();
		Set<String> identitySet = new HashSet<>(); // HasgSet: 不重複, 不排序
		
		List<Person> persons = findPersonAll();
		
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
	
	// 帳號驗證
    public boolean validateAccount_number(String account_number) {
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(account_number);
        return matcher.matches();
    }
    
    // 密碼驗證
	public boolean validatePassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z0-9]{8,16}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(password);
        return matcher.matches();
    }

	// email驗證
    public boolean validateEmail(String email) {
		String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
