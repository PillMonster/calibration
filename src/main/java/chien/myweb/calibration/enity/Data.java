package chien.myweb.calibration.enity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Data")
public class Data {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_id")
	private Long id;
	
	@Column(name = "value")
	private Double value;
	
	@Transient //@Transient 會告訴 JPA 不將 confirmPassword 欄位映射到資料庫表
	private List<String> specIdList;
	
	@Transient //@Transient 會告訴 JPA 不將 confirmPassword 欄位映射到資料庫表
	private List<String> valueList;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "calibrate_date")
	private String calibrate_date;
	
	@Column(name = "temperature")
	private String temperature;
	
	@Column(name = "humidity")
	private String humidity;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	public List<String> getSpecIdList() {
		return specIdList;
	}
	public void setSpecIdList(List<String> specIdList) {
		this.specIdList = specIdList;
	}
	
	public List<String> getValueList() {
		return valueList;
	}
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCalibrate_date() {
		return calibrate_date;
	}
	public void setCalibrate_date(String calibrate_date) {
		this.calibrate_date = calibrate_date;
	}
	
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	@ManyToMany(mappedBy = "data") //指定了反向關聯到 Instrument 類的 data 屬性
	private Set<Instrument> instruments;
	
	@JsonIgnore
	public Set<Instrument> getInstruments() {
	    return instruments;
	}

	public void setInstruments(Set<Instrument> instruments) {
		this.instruments = instruments;
	}
	
	
	@Override
	public String toString() {
		return "Data [id=" + id + ", specIdList=" + specIdList + ", valueList=" + valueList + ", result=" + result
				+ ", calibrate_date=" + calibrate_date + ", temperature=" + temperature + ", humidity=" + humidity
				+ "]";
	}
	
	
	
	
}
