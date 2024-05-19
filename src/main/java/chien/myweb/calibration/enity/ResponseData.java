package chien.myweb.calibration.enity;

public class ResponseData {
	
	private String calibrate_date;
	private Double specification;
	private Double USL;
	private Double LSL; 
	private Double value;
	private String temperature;
	private String humidity;
	private String result;
		
	public ResponseData() {
	}

	public ResponseData(String calibrate_date, Double specification, Double uSL, Double lSL,  Double value,
			String temperature, String humidity, String result) {
		this.calibrate_date = calibrate_date;
		this.specification = specification;
		USL = uSL;
		LSL = lSL;
		this.value = value;
		this.temperature = temperature;
		this.humidity = humidity;
		this.result = result;
	}
	
	public String getCalibrate_date() {
		return calibrate_date;
	}
	public void setCalibrate_date(String calibrate_date) {
		this.calibrate_date = calibrate_date;
	}
	public Double getSpecification() {
		return specification;
	}
	public void setSpecification(Double specification) {
		this.specification = specification;
	}
	public Double getUSL() {
		return USL;
	}
	public void setUSL(Double uSL) {
		USL = uSL;
	}
	public Double getLSL() {
		return LSL;
	}
	public void setLSL(Double lSL) {
		LSL = lSL;
	}

	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
