package tn.esprit.workflowApi.Operation;

import java.util.Map;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowOperationConditional extends WorkflowOperation {

	private WorkflowOperationConditional instance = this;
	
	public WorkflowOperationConditional(WorkflowObject previous) {
		super();
		
		this.setPrevious(previous);
	}
	
	protected WorkflowOperationConditional(WorkflowObject previous, Map<String, Object> parameters) {
		super(parameters);
		
		this.setPrevious(previous);
	}
	
	public abstract boolean condition(WorkflowTaskResult lastResult, WorkflowOperationConditional self);
	
	@Override
	public boolean finished() {
		return true;
	}
	
	public void setPrevious(WorkflowObject object) {
		super.setFirst(object);
	}
	
	public WorkflowObject getPrevious() {
		return super.getFirst();
	}
	
	public boolean getPerviousFinished() {
		return super.getFirstFinished();
	}
	
	public void setPreviousFinished(boolean finished) {
		super.setFirstFinished(finished);
	}
	
	@Override
	public WorkflowOperationConditional clone() {
		return new WorkflowOperationConditional(this.getPrevious(), this.parameters) {

			@Override
			public boolean condition(WorkflowTaskResult lastResult, WorkflowOperationConditional self) {
				return instance.condition(lastResult, self);
			}
			
		};
	}

}
