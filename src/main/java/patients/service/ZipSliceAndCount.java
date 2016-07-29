package patients.service;

import java.util.List;

import patients.model.Zip;

public class ZipSliceAndCount {
	
	private List<Zip> zips;
	
	private Long count;
	
	public ZipSliceAndCount(List<Zip> zips, Long count) {
		this.zips = zips;
		this.count = count;
	}
	public List<Zip> getZips() {
		return zips;
	}
	public Long getCount() {
		return count;
	}
	
}
