package patients.service;

import java.util.List;

import patients.model.Insurance;

public class InsuranceSliceAndCount {
	
	private List<Insurance> insurances;
	
	private Long count;
	
	public InsuranceSliceAndCount(List<Insurance> insurances, Long count) {
		this.insurances = insurances;
		this.count = count;
	}
	public List<Insurance> getInsurances() {
		return insurances;
	}
	public Long getCount() {
		return count;
	}
	
}
