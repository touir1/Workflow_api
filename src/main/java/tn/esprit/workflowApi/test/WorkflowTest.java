package tn.esprit.workflowApi.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import tn.esprit.workflowApi.Log;
import tn.esprit.workflowApi.Workflow;
import tn.esprit.workflowApi.WorkflowManager;
import tn.esprit.workflowApi.WorkflowObject;
import tn.esprit.workflowApi.WorkflowStatus;
import tn.esprit.workflowApi.Operation.WorkflowOperation;
//import tn.esprit.workflowApi.Operation.WorkflowOperationEnd;
import tn.esprit.workflowApi.Operation.WorkflowOperationTimer;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;
import tn.esprit.workflowApi.Task.WorkflowDummyTask;
import tn.esprit.workflowApi.Task.WorkflowTask;
import tn.esprit.workflowApi.Task.WorkflowTaskObject;

public class WorkflowTest {
	
	public static void main(String[] args) {

		try {
			FileOutputStream fout = new FileOutputStream("workflow.log");
			//creating Printstream obj 
	        PrintStream out=new PrintStream(fout);
	        Log.addPrintStream(out);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        
        
		
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
				WorkflowTaskResult result = new WorkflowTaskResult();
				result.setStatus(WorkflowStatus.SUCCESS);
				result.setData("testing");
				return result;
			}
		};
		w.setStartingTask(task);
		
		WorkflowOperationTimer t = new WorkflowOperationTimer(task,1000);
		
		WorkflowDummyTask dummyTask = new WorkflowDummyTask();
		t.addNext(dummyTask);
		
		WorkflowTask task2 = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult) throws Exception {
				
				//System.out.println("last result");
				if(lastResult == null || lastResult.getData() == null) System.out.println("no data found");
				else {
					System.out.println("last result got data");
					for(Object o : lastResult.getData()) {
						System.out.println("data object: " + o);
					}
					System.out.println("lastResult status: "+lastResult.getStatus());
					
				}
				
				WorkflowTaskResult result = new WorkflowTaskResult(WorkflowStatus.SUCCESS,"testing result");
				
				return result;
			}

			@Override
			public void onSuccess(WorkflowTaskResult result) throws Exception {
				System.out.println("on success "+this.getUniqueID());
				System.out.println("data: " + result.getData().get(0));
				
			}

			@Override
			public void onFailure(WorkflowTaskResult result) throws Exception {
				System.out.println("on failure "+this.getUniqueID());
				
			}
			
		};
		t.addNext(task2);
		//WorkflowOperationEnd ending = new WorkflowOperationEnd(dummyTask);
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
		System.out.println("dummyTask: "+dummyTask.getUniqueID());
		//System.out.println("timer: "+t.getUniqueID());
		//System.out.println("ending: "+ending.getUniqueID());
		System.out.println("task2: "+task2.getUniqueID());
		
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
