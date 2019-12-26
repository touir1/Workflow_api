package tn.esprit.workflowApi.Operation;

import tn.esprit.workflowApi.WorkflowStatus;

public class WorkflowOperationEnd extends WorkflowOperation {

	protected WorkflowStatus status = WorkflowStatus.PENDING;

	@Override
	public boolean finished() {
		return this.status.equals(WorkflowStatus.SUCCESS);
	}
}
