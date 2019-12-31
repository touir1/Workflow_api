package tn.esprit.workflowApi.Operation;

import java.util.Arrays;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.WorkflowStatus;

public class WorkflowOperationOR extends WorkflowOperation {

	public WorkflowOperationOR(WorkflowObject object1, WorkflowObject object2) {
		super();
		
		this.setFirst(object1);
		this.setSecond(object2);
	}

	@Override
	public boolean finished() {
		return Arrays.asList(
				WorkflowStatus.FAILURE,
				WorkflowStatus.SUCCESS, 
				WorkflowStatus.INTERRUPTED
			).contains(this.getStatus());
	}
	
	@Override
	public WorkflowOperationAND clone() {
		return new WorkflowOperationAND(this.getFirst(), this.getSecond());
	}
	
	@Override
	public void setFirst(WorkflowObject object) {
		super.setFirst(object);
	}
	
	@Override
	public WorkflowObject getFirst() {
		return super.getFirst();
	}
	
	@Override
	public void setSecond(WorkflowObject object) {
		super.setSecond(object);
	}
	
	@Override
	public WorkflowObject getSecond() {
		return super.getSecond();
	}
	
	@Override
	public boolean getFirstFinished() {
		return super.getFirstFinished();
	}
	
	@Override
	public boolean getSecondFinished() {
		return super.getSecondFinished();
	}
	
	@Override
	public void setFirstFinished(boolean finished) {
		super.setFirstFinished(finished);
	}
	
	@Override
	public void setSecondFinished(boolean finished) {
		super.setSecondFinished(finished);
	}
}
