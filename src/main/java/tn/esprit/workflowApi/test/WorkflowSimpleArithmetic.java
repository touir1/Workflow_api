package tn.esprit.workflowApi.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import tn.esprit.workflowApi.Log;
import tn.esprit.workflowApi.Workflow;
import tn.esprit.workflowApi.WorkflowManager;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;
import tn.esprit.workflowApi.Task.WorkflowTask;

public class WorkflowSimpleArithmetic {
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
		
		String toSolve = "(1+2)*3";
		
		WorkflowTask task1 = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void onSuccess(WorkflowTaskResult result) throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFailure(WorkflowTaskResult result) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
}
