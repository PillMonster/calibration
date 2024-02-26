package chien.myweb.calibration.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import chien.myweb.calibration.enity.Person;


public interface PersonDao extends JpaRepository<Person, Long>{
	
	/*
	 *  1. @Query: 自定義SQL查詢
	 *  2. @Modifying: 用於新增、修改、刪除，若用於查詢則不需要此註解(JPA會以更新類語句來執行，而不再是以查詢語句執行)。　
	 *  3. @Transactional：持久化和刪除物件需要 JPA 中的事務。因此，我們需要確保事務正在運行，透過使用註解@Transactional方式來實現。
	 */
	
    @Query(value = "select * from persons where person_id = ?1", nativeQuery = true)  //單一查詢 (by ID)
    List<Person> findByPersonId(Long id);
    
    @Query(value = "select * from persons where job_number = ?1", nativeQuery = true) //單一查詢 (by job_number)
    List<Person> findByJobnumber(String jobnumber);
    
    @Query(value = "select * from persons where username = ?1", nativeQuery = true) //單一查詢 (by username)
    List<Person> findByUsername(String username);
    
    @Query(value = "select * from persons where password = ?1", nativeQuery = true) //單一查詢 (by password)
    List<Person> findByPassword(String password); 

    @Query(value = "select * from persons", nativeQuery = true) //查詢全部
    List<Person> findPersons();
}
