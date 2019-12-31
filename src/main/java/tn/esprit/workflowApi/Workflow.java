package tn.esprit.workflowApi;

import java.util.Iterator;

import tn.esprit.workflowApi.Task.WorkflowTask;
import tn.esprit.workflowApi.Task.WorkflowTaskObject;

public class Workflow {

	private WorkflowTask startingTask;
	protected final long uniqueID;

	Workflow() {
		super();
		
		this.uniqueID = WorkflowManager.getNextUniqueID();
	}

	public Iterator<WorkflowTaskObject> createTasksIterator() {
		return new WorkflowIterator(startingTask);
	}

	public Workflow setStartingTask(WorkflowTask task) {
		this.startingTask = task;
		return this;
	}

	/*
	Workflow startWorkflow() {
		if (startingTask != null)
			startingTask.setStatus(WorkflowStatus.IN_PROGRESS);
		
		return this;
	}
	*/
	
	WorkflowTask getStartingTask() {
		return this.startingTask;
	}
	
	public long getUniqueID() {
		return this.uniqueID;
	}

}
