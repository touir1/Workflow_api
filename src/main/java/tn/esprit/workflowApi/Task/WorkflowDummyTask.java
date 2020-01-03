package tn.esprit.workflowApi.Task;

import java.util.Map;

import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public class WorkflowDummyTask extends WorkflowTaskObject {
	
	public WorkflowDummyTask() {
		super();
		
	}
	
	protected WorkflowDummyTask(Map<String, Object> parameters) {
		super(parameters);
	}
	
	@Override
	public WorkflowDummyTask clone() {
		return new WorkflowDummyTask(this.parameters);
	}
	
	@Override
	public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) {
		return null;
	}
	
	@Override
	public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) {
		
	}

	@Override
	public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) {
		
	}
}
