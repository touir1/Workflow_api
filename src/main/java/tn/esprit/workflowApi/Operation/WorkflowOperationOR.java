package tn.esprit.workflowApi.Operation;

import java.util.Arrays;

import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.WorkflowStatus;

public class WorkflowOperationOR extends WorkflowOperation {

	public WorkflowOperationOR(WorkflowObject object1, WorkflowObject object2) {
		super();
		
		this.first = object1;
		this.second = object2;
	}

	@Override
	public boolean finished() {
		return Arrays.asList(
				WorkflowStatus.FAILURE,
				WorkflowStatus.SUCCESS, 
				WorkflowStatus.INTERRUPTED
			).contains(this.status);
	}
}
