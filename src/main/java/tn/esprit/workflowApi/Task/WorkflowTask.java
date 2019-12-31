package tn.esprit.workflowApi.Task;

import java.util.HashMap;
import java.util.Map;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowTask extends WorkflowTaskObject {

	private WorkflowTask instance = this;
	private Map<String, Object> parameters;
	private WorkflowObject onSuccessObject;
	private WorkflowObject onFailureObject;

	public WorkflowTask() {
		super();

		this.parameters = new HashMap<String, Object>();
	}

	@Override
	public WorkflowTask clone() {
		WorkflowTask clone = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult) throws Exception {
				return instance.execute(lastResult);
			}

			@Override
			public void onSuccess(WorkflowTaskResult result) throws Exception {
				instance.onSuccess(result);

			}

			@Override
			public void onFailure(WorkflowTaskResult result) throws Exception {
				instance.onFailure(result);

			}

		};
		clone.setParameters(new HashMap<String,Object>(this.parameters));
		clone.setOnSuccessObject(this.onSuccessObject);
		clone.setOnFailureObject(this.onFailureObject);

		return clone;
	}

	public void setParameter(String key, Object value) {
		parameters.put(key, value);
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters.putAll(parameters);
	}

	public Object removeParameter(String key) {
		return parameters.remove(key);
	}

	public Object getParameter(String key) {
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
