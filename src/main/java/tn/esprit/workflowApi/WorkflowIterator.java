package tn.esprit.workflowApi;

import java.util.Iterator;
import java.util.List;

import tn.esprit.workflowApi.Task.WorkflowTask;

public class WorkflowIterator implements Iterator<WorkflowTask> {

	private List<WorkflowTask> tasks;
	private WorkflowTask startingTask;
	
	private int idx; 
	
	WorkflowIterator(List<WorkflowTask> tasks, WorkflowTask startingTask){
		super();
		
		this.idx = -1;
		this.startingTask = startingTask;
		this.tasks = tasks;
	}
	
	@Override
	public boolean hasNext() {
		if(idx == -1 && startingTask == null) return false;
		if(idx != -1 && (tasks == null || tasks.size() <= idx || tasks.get(idx) == null )) return false;
		return true;
	}

	@Override
	public WorkflowTask next() {
		if(idx == -1) {
			if(startingTask == null)
				return null;
			idx++;
			return startingTask;
		}
		else {
			if(tasks == null || tasks.size() <= idx || tasks.get(idx) == null )
				return null;
			return tasks.get(idx++);
		}
	}


}
