package tn.esprit.workflowApi;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import tn.esprit.workflowApi.Task.WorkflowTask;
import tn.esprit.workflowApi.Task.WorkflowTaskObject;

public class WorkflowIterator implements Iterator<WorkflowTaskObject> {

	private Queue<WorkflowTaskObject> objectQueue;
	
	WorkflowIterator(WorkflowTask startingTask){
		super();
		
		this.objectQueue = new LinkedList<WorkflowTaskObject>();
		
		initializeTaskStack(startingTask);
	}
	
	private void initializeTaskStack(WorkflowTask startingTask) {
		if(startingTask == null) return;
		
		Stack<WorkflowObject> objectStack = new Stack<WorkflowObject>();
		Set<Long> stackedKeys = new HashSet<Long>();
		
		objectStack.add(startingTask);
		while(!objectStack.isEmpty()) {
			WorkflowObject obj = objectStack.pop();
			if(obj == null || stackedKeys.contains(obj.getUniqueID())) continue;
			
			stackedKeys.add(obj.getUniqueID());
			
			if(obj instanceof WorkflowTaskObject) 
				this.objectQueue.add((WorkflowTaskObject)obj);
			
			for(WorkflowObject object : obj.getNextList()) {
				if(object == null) continue;
				if(!stackedKeys.contains(object.getUniqueID())) {
					objectStack.add(object);
				}
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return !this.objectQueue.isEmpty();
	}

	@Override
	public WorkflowTaskObject next() {
		return this.objectQueue.poll();
	}


}
