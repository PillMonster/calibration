package chien.myweb.calibration.enity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="instrument")
public class Instrument {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "instrument_id")
	private Long id;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "characteristic")
	private String characteristic;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "cycle")
	private String cycle;
	
	@Column(name = "calibrate_type")
	private String calibrate_type;
	
	@Column(name = "calibrate_localation")
	private String calibrate_localation;
	
	@Column(name = "calibrate_month")
	private String calibrate_month;
	
	@Column(name = "last_calibrate_date")
	private LocalDate last_calibrate_date;
	
	@Column(name = "mother_instrument_number")
	private String mother_instrument_number;
	
	@Column(name = "is_calibration")
	private String is_calibration;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getCalibrate_type() {
		return calibrate_type;
	}

	public void setCalibrate_type(String calibrate_type) {
		this.calibrate_type = calibrate_type;
	}

	public String getCalibrate_localation() {
		return calibrate_localation;
	}

	public void setCalibrate_localation(String calibrate_localation) {
		this.calibrate_localation = calibrate_localation;
	}

	public String getCalibrate_month() {
		return calibrate_month;
	}

	public void setCalibrate_month(String calibrate_month) {
		this.calibrate_month = calibrate_month;
	}

	public LocalDate getLast_calibrate_date() {
		return last_calibrate_date;
	}

	public void setLast_calibrate_date(LocalDate last_calibrate_date) {
		this.last_calibrate_date = last_calibrate_date;
	}

	public String getMother_instrument_number() {
		return mother_instrument_number;
	}

	public void setMother_instrument_number(String mother_instrument_number) {
		this.mother_instrument_number = mother_instrument_number;
	}

	public String getIs_calibration() {
		return is_calibration;
	}

	public void setIs_calibration(String is_calibration) {
		this.is_calibration = is_calibration;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore // 使用多對多要加上，阻止該方(person)的序列化
	@JoinTable(name = "instrument_person", //指示兩個實體之間的關聯表（junction table）的配置
		    joinColumns = @JoinColumn(name = "instrument_id"), // 指定中介表中與當前實體（instrument）關聯的外鍵列
		    inverseJoinColumns = @JoinColumn(name = "person_id")) // 指定了中介表中與另一實體（person）關聯的外鍵列*/
	private List<Person> persons = new ArrayList<>();

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore // 使用多對多要加上，阻止該方(spec)的序列化
	@JoinTable(name = "instrument_spec", //指示兩個實體之間的關聯表（junction table）的配置
		    joinColumns = @JoinColumn(name = "instrument_id"), // 指定中介表中與當前實體（instrument）關聯的外鍵列
		    inverseJoinColumns = @JoinColumn(name = "spec_id")) // 指定了中介表中與另一實體（spec）關聯的外鍵列
	private List<Spec> spec = new ArrayList<>();

	public List<Spec> getSpec() {
		return spec;
	}

	public void setSpec(List<Spec> spec) {
		this.spec = spec;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore // 使用多對多要加上，阻止該方(data)的序列化
	@JoinTable(name = "instrument_spec_data", //指示三個實體之間的關聯表（junction table）的配置
		    joinColumns = @JoinColumn(name = "instrument_id"), // 指定中介表中與當前實體（instrument）關聯的外鍵列
		    inverseJoinColumns = @JoinColumn(name = "data_id")) // 指定了中介表中與另一實體（data）關聯的外鍵列
	private List<Data> data = new ArrayList<>();

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
		
	@Override
	public String toString() {
		return "Instrument [id=" + id + ", 編號=" + number + ", 名稱=" + name + "]";
	}
	

}
