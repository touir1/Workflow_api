package tn.esprit.workflowApi;

public enum WorkflowStatus {
	SUCCESS("SUCCESS"), FAILURE("FAILURE"), IN_PROGRESS("IN_PROGRESS"), 
	PENDING("PENDING"), INTERRUPTED("INTERRUPTED");
	
	private final String description;
	
	private WorkflowStatus(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.description;
	}
}
