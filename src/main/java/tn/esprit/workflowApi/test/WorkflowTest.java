package tn.esprit.workflowApi.test;

import java.util.Iterator;

import tn.esprit.workflowApi.Workflow;
import tn.esprit.workflowApi.WorkflowManager;
import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.Operation.WorkflowOperation;
import tn.esprit.workflowApi.Operation.WorkflowOperationEnd;
import tn.esprit.workflowApi.Operation.WorkflowOperationTimer;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;
import tn.esprit.workflowApi.Task.WorkflowDummyTask;
import tn.esprit.workflowApi.Task.WorkflowTask;
import tn.esprit.workflowApi.Task.WorkflowTaskObject;

public class WorkflowTest {
	
	public static void main(String[] args) {
		WorkflowManager manager = WorkflowManager.getInstance();
		
		Workflow w = manager.createWorkflow();
		
		WorkflowTask task = new WorkflowTask() {
			
			@Override
			public void onSuccess(WorkflowTaskResult result) {
				System.out.println("on success");
				
			}
			
			@Override
			public void onFailure(WorkflowTaskResult result) {
				System.out.println("on failure");
				
			}
			
			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult) throws Exception {
				System.out.println("executing task: "+ this.getUniqueID());
				//throw new Exception();
				return null;
			}
		};
		w.setStartingTask(task);
		
		WorkflowOperationTimer t = new WorkflowOperationTimer(task,1000);
		
		WorkflowDummyTask dummyTask = new WorkflowDummyTask();
		t.addNext(dummyTask);
		
		WorkflowOperationEnd ending = new WorkflowOperationEnd(dummyTask);
		/*
		System.out.println("hello1");
		t.startOperation();
		System.out.println("hello2");
		
		while(!t.finished()) {
			System.out.println("not yet");
		}
		System.out.println("finished");
		WorkflowObject obj2 = t;
		System.out.println("workflowOperation? "+(obj2 instanceof WorkflowOperation));
		System.out.println("workflowTaskObject? "+(obj2 instanceof WorkflowTaskObject));
		*/
		
		System.out.println("task1: "+task.getUniqueID());
		System.out.println("task2: "+dummyTask.getUniqueID());
		System.out.println("timer: "+t.getUniqueID());
		System.out.println("ending: "+ending.getUniqueID());
		
		System.out.println("iterating over tasks in workflow");
		for(Iterator<WorkflowTaskObject> i = w.createTasksIterator(); i.hasNext(); ) {
			WorkflowTaskObject ts = i.next();
			System.out.println(ts.getUniqueID());
		}
		
		try {
			manager.executeWorkflow(w);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
