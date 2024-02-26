package chien.myweb.calibration.enity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Data")
public class Data {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_id")
	private Long id;
	
	@Column(name = "value")
	private Double value;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "calibrate_date")
	private String calibrate_date;
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
	
	@ManyToMany(mappedBy = "data") //指定了反向關聯到 Instrument 類的 spec 屬性
	private Set<Instrument> instruments;
	
	public Set<Instrument> getInstruments() {
	    return instruments;
	}

	public void setInstruments(Set<Instrument> instruments) {
		this.instruments = instruments;
	}
	
	@Override
	public String toString() {
		return "Data [data_id=" + id + ", value=" + value + ", result=" + result + ", calibrate_date="
				+ calibrate_date + "]";
	}
	
	
}
