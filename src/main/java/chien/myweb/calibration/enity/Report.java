package chien.myweb.calibration.enity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Report")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private Long id;
	
	@Column(name = "report_no")
	private String report_no;
	
	@Column(name = "report_name")
	private String report_name;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "calibrate_date")
	private String calibrate_date;
	
	@Column(name = "is_taf")
	private String is_taf;
	
	public Report() {
	}

	public Report(String report_no, String report_name, String result, String calibrate_date, String is_taf) {
		this.report_no = report_no;
		this.report_name = report_name;
		this.result = result;
		this.calibrate_date = calibrate_date;
		this.is_taf = is_taf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReport_no() {
		return report_no;
	}

	public void setReport_no(String report_no) {
		this.report_no = report_no;
	}

	public String getReport_name() {
		return report_name;
	}

	public void setReport_name(String report_name) {
		this.report_name = report_name;
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

	public String getIs_taf() {
		return is_taf;
	}

	public void setIs_taf(String is_taf) {
		this.is_taf = is_taf;
	}
	
	@ManyToMany(mappedBy = "report") //指定了反向關聯到 Instrument 類的 report 屬性
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
		return "report [id=" + id + ", report_no=" + report_no + ", report_name=" + report_name + ", result=" + result
				+ ", calibrate_date=" + calibrate_date + ", is_taf=" + is_taf + "]";
	}

}
