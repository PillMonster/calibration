package chien.myweb.calibration.enity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

public class DataInfo {
	
	private List<Long> id;
	private List<Double> value;
	private List<String> result;
	private List<String> temperature;
	private List<String> humidity;
	private List<String> calibrate_date;
	
	public List<Long> getId() {
		return id;
	}
	public void setId(List<Long> id) {
		this.id = id;
	}
	public List<Double> getValue() {
		return value;
	}
	public void setValue(List<Double> value) {
		this.value = value;
	}
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
		this.result = result;
	}
	public List<String> getTemperature() {
		return temperature;
	}
	public void setTemperature(List<String> temperature) {
		this.temperature = temperature;
	}
	public List<String> getHumidity() {
		return humidity;
	}
	public void setHumidity(List<String> humidity) {
		this.humidity = humidity;
	}
	public List<String> getCalibrate_date() {
		return calibrate_date;
	}
	public void setCalibrate_date(List<String> calibrate_date) {
		this.calibrate_date = calibrate_date;
	}
	
}
