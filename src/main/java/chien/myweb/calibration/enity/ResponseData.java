package chien.myweb.calibration.enity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.support.PersistableIsNewStrategy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="ResponseData")
public class ResponseData {
	
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
}
