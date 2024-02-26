package chien.myweb.calibration.enity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="persons")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id") 
	private Long id;
	
	@Column(name = "job_number")
	private String job_number;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "identity")
	private String identity;
	
	@Column(name = "create_date")
	private LocalDateTime create_date;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJob_number() {
		return job_number;
	}
	public void setJob_number(String job_number) {
		this.job_number = job_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public LocalDateTime getCreate_date() {
		return create_date;
	}
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	
	@ManyToMany(mappedBy = "persons") //指定了反向關聯到 Instrument 類的 person 屬性
	private Set<Instrument> instruments;
	
	public Set<Instrument> getInstruments() {
	    return instruments;
	}

	public void setInstruments(Set<Instrument> instruments) {
		this.instruments = instruments;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", job_number=" + job_number + ", password=" + password + ", username=" + username + "]";
	}
	/*public String toString() {
		return "Person [id=" + id + ", job_number=" + job_number + ", password=" + password + ", username=" + username
				+ ", email=" + email + ", department=" + department + ", position=" + position + ", identity="
				+ identity + ", create_date=" + create_date + "]";
	}*/  
	
}
