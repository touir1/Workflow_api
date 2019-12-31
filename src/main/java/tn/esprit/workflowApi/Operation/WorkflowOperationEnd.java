package tn.esprit.workflowApi.Operation;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.WorkflowStatus;

public class WorkflowOperationEnd extends WorkflowOperation {

	@Override
	public boolean finished() {
		return this.getStatus().equals(WorkflowStatus.SUCCESS);
	}
	
	public WorkflowOperationEnd(WorkflowObject object) {
		super();
		
		super.setStatus(WorkflowStatus.PENDING);
		super.setFirst(object);
	}
}
