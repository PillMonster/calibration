package chien.myweb.calibration.enity;

import java.util.List;

public class SpecInfo {
	
	private List<Long> id;
	private List<Double> specification;
	private List<Double> USL;
	private List<Double> LSL;
	
	public List<Long> getId() {
		return id;
	}
	public void setId(List<Long> id) {
		this.id = id;
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
	
}
