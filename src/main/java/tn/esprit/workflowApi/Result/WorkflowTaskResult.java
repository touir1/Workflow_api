package tn.esprit.workflowApi.Result;

import java.util.Arrays;
import java.util.List;

import tn.esprit.workflowApi.WorkflowStatus;

public class WorkflowTaskResult {
	
	private WorkflowStatus status;
	private List<Object> data;
	
	public WorkflowTaskResult() {
		super();
		
		this.status = WorkflowStatus.SUCCESS;
		this.data = null;
	}
	
	public WorkflowTaskResult(WorkflowStatus status, List<Object> data) {
		super();
		
		this.status = status;
		this.data = data;
	}
	
	public WorkflowTaskResult(WorkflowStatus status, Object data) {
		this(status, Arrays.asList(data));
	}
	
	public WorkflowTaskResult(List<Object> data) {
		this();
		
		this.data = data;
	}
	
	public WorkflowTaskResult(Object data) {
		this(Arrays.asList(data));
	}

	public WorkflowStatus getStatus() {
		return status;
	}

	public void setStatus(WorkflowStatus status) {
		this.status = status;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

}
