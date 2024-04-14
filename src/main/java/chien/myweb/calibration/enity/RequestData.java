package chien.myweb.calibration.enity;

import java.time.LocalDate;
import java.util.List;

public class RequestData {
	
	private String number;
	private String name;
	private String type;
	private String characteristic;
	private String unit;
	private String cycle;
	private String calibrate_type;
	private String calibrate_localation;
	private List<String> calibrate_month;
	private String mother_instrument_number;
	private LocalDate last_calibrate_date;
	
	private List<Double> specification;
	private List<Double> USL;
	private List<Double> LSL;
	
	private String custos;
	private String custosLeader;
	private String checker;
	private String checkerLeader;
	
	private Long personId;
	private Long personSupervisorId;
	private Long calibrationPersonId;
	private Long calibrationPersonSupervisorId;
	private Long dataId;

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

	public List<String> getCalibrate_month() {
		return calibrate_month;
	}
	public void setCalibrate_month(List<String> calibrate_month) {
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
	public List<Double> getSpecification() {
		return specification;
	}
	public void setSpecification(List<Double> specification) {
		this.specification = specification;
	}
	public List<Double> getUSL() {
		return USL;
	}
	public void setUSL(List<Double> uSL) {
		USL = uSL;
	}
	public List<Double> getLSL() {
		return LSL;
	}
	public void setLSL(List<Double> lSL) {
		LSL = lSL;
	}
	public String getCustos() {
		return custos;
	}
	public void setCustos(String custos) {
		this.custos = custos;
	}
	public String getCustosLeader() {
		return custosLeader;
	}
	public void setCustosLeader(String custosLeader) {
		this.custosLeader = custosLeader;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getCheckerLeader() {
		return checkerLeader;
	}
	public void setCheckerLeader(String checkerLeader) {
		this.checkerLeader = checkerLeader;
	}
	
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getPersonSupervisorId() {
		return personSupervisorId;
	}
	public void setPersonSupervisorId(Long personSupervisorId) {
		this.personSupervisorId = personSupervisorId;
	}
	public Long getCalibrationPersonId() {
		return calibrationPersonId;
	}
	public void setCalibrationPersonId(Long calibrationPersonId) {
		this.calibrationPersonId = calibrationPersonId;
	}
	public Long getCalibrationPersonSupervisorId() {
		return calibrationPersonSupervisorId;
	}
	public void setCalibrationPersonSupervisorId(Long calibrationPersonSupervisorId) {
		this.calibrationPersonSupervisorId = calibrationPersonSupervisorId;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	@Override
	public String toString() {
		return "RequestData [number=" + number + ", name=" + name + ", type=" + type + ", characteristic="
				+ characteristic + ", unit=" + unit + ", cycle=" + cycle + ", calibrate_type=" + calibrate_type
				+ ", calibrate_localation=" + calibrate_localation + ", calibrate_month=" + calibrate_month
				+ ", mother_instrument_number=" + mother_instrument_number + ", last_calibrate_date="
				+ last_calibrate_date + ", specification=" + specification + ", USL=" + USL + ", LSL=" + LSL
				+ ", custos=" + custos + ", custosLeader=" + custosLeader + ", checker=" + checker + ", checkerLeader="
				+ checkerLeader + "]";
	}
	
	
}
