package tn.esprit.workflowApi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tn.esprit.workflowApi.Task.WorkflowTask;

public class Workflow {
	
	private List<WorkflowTask> tasks;
	private WorkflowTask startingTask;
	
	Workflow(){
		super();
		
		this.tasks = new ArrayList<WorkflowTask>();
	}
	
	public Iterator<WorkflowTask> createIterator(){
		return new WorkflowIterator(tasks,startingTask);
	}
	
	public Workflow addTask(WorkflowTask task) {
		if(tasks == null) tasks = new ArrayList<WorkflowTask>();
		
		tasks.add(task);
		return this;
	}
	
	public Workflow setStartingTask(WorkflowTask task) {
		this.startingTask = task;
		return this;
	}
	
	Workflow startWorkflow() {
		// TODO finish start workflow
		return this;
	}
	
}
