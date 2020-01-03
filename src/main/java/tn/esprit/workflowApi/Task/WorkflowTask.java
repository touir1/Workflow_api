package tn.esprit.workflowApi.Task;

import java.util.Map;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowTask extends WorkflowTaskObject {

	private WorkflowTask instance = this;
	private WorkflowObject onSuccessObject;
	private WorkflowObject onFailureObject;

	public WorkflowTask() {
		super();

	}
	
	protected WorkflowTask(Map<String, Object> parameters) {
		super(parameters);
		
	}

	@Override
	public WorkflowTask clone() {
		WorkflowTask clone = new WorkflowTask(this.parameters) {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				return instance.execute(lastResult, self);
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				instance.onSuccess(result, self);

			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				instance.onFailure(result, self);

			}

		};
		clone.setOnSuccessObject(this.onSuccessObject);
		clone.setOnFailureObject(this.onFailureObject);

		return clone;
	}

	public WorkflowObject getOnSuccessObject() {
		return onSuccessObject;
	}

	public void setOnSuccessObject(WorkflowObject onSuccessObject) {
		this.onSuccessObject = onSuccessObject;
	}

	public WorkflowObject getOnFailureObject() {
		return onFailureObject;
	}

	public void setOnFailureObject(WorkflowObject onFailureObject) {
		this.onFailureObject = onFailureObject;
	}

}
