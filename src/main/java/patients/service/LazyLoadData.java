package patients.service;

public class LazyLoadData {
	
	private Integer first;
	private Integer rows;
	private String sortField;
	private Integer sortOrder;
	private String searchStringParam;
	
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSearchStringParam() {
		return searchStringParam;
	}
	public void setSearchStringParam(String searchStringParam) {
		this.searchStringParam = searchStringParam;
	}

}
