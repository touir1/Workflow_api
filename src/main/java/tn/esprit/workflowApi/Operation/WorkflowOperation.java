package tn.esprit.workflowApi.Operation;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.WorkflowStatus;

public abstract class WorkflowOperation implements WorkflowObject {
	protected WorkflowObject first,second;
	protected WorkflowStatus status = WorkflowStatus.PENDING;
	
	public abstract boolean finished();
	
	public WorkflowStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(WorkflowStatus status) {
		this.status = status;
	}
}
