package chien.myweb.calibration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chien.myweb.calibration.dao.PersonDao;
import chien.myweb.calibration.enity.Person;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	PersonDao personDao;
	
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
	public List<Person> findSpecAll() {
		// TODO Auto-generated method stub
		return personDao.findPersons();
	}

}
