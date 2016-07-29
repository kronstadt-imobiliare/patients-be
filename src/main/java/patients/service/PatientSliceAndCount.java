package patients.service;

import java.util.List;

import patients.model.Patient;

public class PatientSliceAndCount {
	
	private List<Patient> patients;
	
	private Long count;
	
	public PatientSliceAndCount(List<Patient> patients, Long count) {
		this.patients = patients;
		this.count = count;
	}
	public List<Patient> getPatients() {
		return patients;
	}
	public Long getCount() {
		return count;
	}
	
}
