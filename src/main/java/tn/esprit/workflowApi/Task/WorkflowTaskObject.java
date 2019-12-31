package tn.esprit.workflowApi.Task;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowTaskObject extends WorkflowObject {
	
	public abstract WorkflowTaskResult execute(WorkflowTaskResult lastResult) throws Exception;
	public abstract void onSuccess(WorkflowTaskResult result) throws Exception;
	public abstract void onFailure(WorkflowTaskResult result) throws Exception;
	
}
