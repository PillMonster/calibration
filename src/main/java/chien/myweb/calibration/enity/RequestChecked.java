package chien.myweb.calibration.enity;

import java.util.List;

public class RequestChecked {
	/*
	 * 1.此javabean為接收前端的json array之用途
	 * 2.屬性名稱要與前端設定的屬性名稱一致
	 * 3.若接收的屬性有多個，型態要改用List作接收
	 */
	private List<String> month;
	private List<String> cycle;
	private List<String> type;
	private List<String> person;
	private List<String> localation;
	public List<String> getMonth() {
		return month;
	}
	public void setMonth(List<String> month) {
		this.month = month;
	}
	public List<String> getCycle() {
		return cycle;
	}
	public void setCycle(List<String> cycle) {
		this.cycle = cycle;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public List<String> getPerson() {
		return person;
	}
	public void setPerson(List<String> person) {
		this.person = person;
	}
	public List<String> getLocalation() {
		return localation;
	}
	public void setLocalation(List<String> localation) {
		this.localation = localation;
	}
	
	
	
	
	
}
