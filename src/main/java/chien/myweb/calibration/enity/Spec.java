package chien.myweb.calibration.enity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="spec")
public class Spec {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spec_id")
	private Long id;
	
	@Column(name = "specification")
	private Double specification;
	
	@Column(name = "USL")
	private Double USL;
	
	@Column(name = "LSL")
	private Double LSL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	@ManyToMany(mappedBy = "spec") //指定了反向關聯到 Instrument 類的 spec 屬性
	private Set<Instrument> instruments;
	
	public Set<Instrument> getInstruments() {
	    return instruments;
	}

	public void setInstruments(Set<Instrument> instruments) {
		this.instruments = instruments;
	}

	@Override
	public String toString() {
		return "Spec [id=" + id + ", specification=" + specification + ", USL=" + USL + ", LSL=" + LSL + "]";
	}
	
}
