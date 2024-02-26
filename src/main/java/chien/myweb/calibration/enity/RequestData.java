package chien.myweb.calibration.enity;

public class RequestData {
	private Long personId;
	private Long personSupervisorId;
	private Long calibrationPersonId;
	private Long calibrationPersonSupervisorId;
	private Long dataId;
	
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

}
