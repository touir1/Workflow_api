package tn.esprit.workflowApi.Result;

import tn.esprit.workflowApi.WorkflowStatus;

public class WorkflowTaskResult {
	private WorkflowStatus status;
	private Object data;

	public WorkflowStatus getStatus() {
		return status;
	}

	public void setStatus(WorkflowStatus status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
