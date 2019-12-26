package tn.esprit.workflowApi.test;

import tn.esprit.workflowApi.Operation.WorkflowOperationTimer;

public class WorkflowTest {
	
	public static void main(String[] args) {
		WorkflowOperationTimer t = new WorkflowOperationTimer(1000);
		
		System.out.println("hello1");
		t.startOperation();
		System.out.println("hello2");
		
		while(!t.finished()) {
			System.out.println("not yet");
		}
		System.out.println("finished");
		
	}
	
}
