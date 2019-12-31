package tn.esprit.workflowApi.Task;

import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public class WorkflowDummyTask extends WorkflowTaskObject {
	
	public WorkflowDummyTask() {
		super();
		
	}
	
	@Override
	public WorkflowDummyTask clone() {
		return new WorkflowDummyTask();
	}
	
	@Override
	public WorkflowTaskResult execute(WorkflowTaskResult lastResult) {
		return null;
	}
	
	@Override
	public void onSuccess(WorkflowTaskResult result) {
		
	}

	@Override
	public void onFailure(WorkflowTaskResult result) {
		
	}
}
