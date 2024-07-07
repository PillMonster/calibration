package chien.myweb.calibration.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import chien.myweb.calibration.enity.Person;


public interface PersonService {
	
	// 新增
	Person addPerson(Person person);
	
	// 修改
	Person updatePerson(Long id, Person person);
	
	// 刪除
	void deletePerson(Long id);
	
	// 單一查詢(透過ID)
	List<Person> findPersonById(Long id);
		
	// 單一查詢(透過jobnumber)
	List<Person> findJobnumber(String jobnumber);
	
	// 單一查詢(透過username)
	List<Person> findUsername(String username);
	
	// 透過username查詢ID (單一欄位)
	Long findPersonIdByUsername(String usernames);
	
	// 單一查詢(透過department)
	List<Person> findByDepartmente(String department);
	
	// 單一查詢(透過identity)
	List<Person> findByidentity(String identity);
		
	// 單一查詢(透過password)
	List<Person> findPassword(String password);
		
	// 查詢(全部)
	List<Person> findPersonAll();
	
	// 查詢不重複的保管人員
	List<String> findPersonByCustos();
	
	// 查詢不重複的保管主管
	List<String> findPersonByCustosLeader();
	
	// 查詢不重複的校驗人員
	List<String> findPersonByChecker();
	
	// 查詢不重複的校驗主管
	List<String> findPersonByCheckerLeader();
	
	// 查詢(透過工號和密碼)
	List<Person> findPersonByJobNumberAbdPassword(String job_number, String password);
	
	// 查詢不重複的內容
	Map<String, Set<String>>  findPersonAllNoRepeat();
	
	// 帳號驗證
	boolean validateAccount_number(String account_number);
	
	// 密碼驗證
	boolean validatePassword(String password);

	// email驗證
    boolean validateEmail(String email);

	

	
	
}
