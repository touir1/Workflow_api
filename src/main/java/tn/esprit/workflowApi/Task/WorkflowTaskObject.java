package tn.esprit.workflowApi.Task;

import java.util.Map;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowTaskObject extends WorkflowObject {
	
	protected WorkflowTaskObject() {
		super();
	}
	
	protected WorkflowTaskObject(Map<String, Object> parameters) {
		super(parameters);
		
	}
	
	public abstract WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception;
	public abstract void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception;
	public abstract void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception;
	
}
