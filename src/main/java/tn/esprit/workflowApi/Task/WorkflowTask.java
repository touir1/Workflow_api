package tn.esprit.workflowApi.Task;

import java.util.HashMap;
import java.util.Map;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Operation.WorkflowOperationEnd;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowTask implements WorkflowObject {

	private WorkflowTask instance = this;
	private Map<String, Object> parameters;
	private WorkflowObject onSuccessObject;
	private WorkflowObject onFailureObject;

	public WorkflowTask() {
		super();

		this.onSuccessObject = new WorkflowOperationEnd();
		this.onFailureObject = new WorkflowOperationEnd();
	}

	public WorkflowTask clone() {
		WorkflowTask clone = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute() {
				return instance.execute();
			}

			@Override
			public void onSuccess() {
				// TODO on success

			}

			@Override
			public void onFailure() {
				// TODO on failure

			}

		};
		clone.setParameters(this.parameters);

		return clone;
	}

	public abstract void onSuccess();

	public abstract void onFailure();

	public abstract WorkflowTaskResult execute();

	public void setParameter(String key, Object value) {
		if (parameters == null)
			parameters = new HashMap<String, Object>();

		parameters.put(key, value);
	}

	public void setParameters(Map<String, Object> parameters) {
		if (parameters == null)
			parameters = new HashMap<String, Object>();

		this.parameters.putAll(parameters);
	}

	public Object removeParameter(String key) {
		if (parameters == null)
			parameters = new HashMap<String, Object>();

		return parameters.remove(key);
	}

	public Object getParameter(String key) {
		if (parameters == null)
			parameters = new HashMap<String, Object>();

		return parameters.get(key);
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
