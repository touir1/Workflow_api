package tn.esprit.workflowApi.Operation;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;

public abstract class WorkflowOperationConditional extends WorkflowOperation {

	private WorkflowOperationConditional instance = this;
	
	public WorkflowOperationConditional(WorkflowObject previous) {
		super();
		
		this.setPrevious(previous);
	}
	
	public abstract boolean condition(WorkflowTaskResult lastResult);
	
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
		return new WorkflowOperationConditional(this.getPrevious()) {

			@Override
			public boolean condition(WorkflowTaskResult lastResult) {
				return instance.condition(lastResult);
			}
			
		};
	}

}
