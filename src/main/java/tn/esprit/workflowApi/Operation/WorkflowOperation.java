package tn.esprit.workflowApi.Operation;

import java.util.Map;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowOperation extends WorkflowObject {
	
	private WorkflowObject first,second;
	private boolean firstFinished, secondFinished;
	private WorkflowTaskResult result;
	
	protected WorkflowOperation() {
		super();
		
	}
	
	protected WorkflowOperation(Map<String, Object> parameters) {
		super(parameters);
		
	}
	
	public abstract boolean finished();
	
	protected void setFirst(WorkflowObject object) {
		if(this.first != null) this.first.removeNext(this.getUniqueID());
		this.first = object;
		if(object != null) object.addNext(this);
	}
	
	protected void setSecond(WorkflowObject object) {
		if(this.second != null) this.second.removeNext(this.getUniqueID());
		this.second = object;
		if(object != null) object.addNext(this);
	}
	
	protected WorkflowObject getFirst() {
		return this.first;
	}
	
	protected WorkflowObject getSecond() {
		return this.second;
	}
	
	protected boolean getFirstFinished() {
		return this.firstFinished;
	}
	
	protected void setFirstFinished(boolean finished) {
		this.firstFinished = finished;
	}
	
	protected boolean getSecondFinished() {
		return this.secondFinished;
	}
	
	protected void setSecondFinished(boolean finished) {
		this.secondFinished = finished;
	}
	
	public void setResult(WorkflowTaskResult result) {
		this.result = result;
	}
	
	public WorkflowTaskResult getResult() {
		return this.result;
	}
	
}
