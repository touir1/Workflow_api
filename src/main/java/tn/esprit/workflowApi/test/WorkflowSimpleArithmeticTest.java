package tn.esprit.workflowApi.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tn.esprit.workflowApi.Log;
import tn.esprit.workflowApi.Workflow;
import tn.esprit.workflowApi.WorkflowManager;
import tn.esprit.workflowApi.WorkflowStatus;
import tn.esprit.workflowApi.Operation.WorkflowOperationConditional;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;
import tn.esprit.workflowApi.Task.WorkflowTask;
import tn.esprit.workflowApi.Task.WorkflowTaskObject;

public class WorkflowSimpleArithmeticTest {
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
		
		// multiplication n times
		Workflow w = manager.createWorkflow();
		String toTest = "1*2*3*4*5*0.5";
		WorkflowTaskResult calculusResult = new WorkflowTaskResult();
		
		WorkflowTask splitterTask = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				WorkflowTaskResult result = new WorkflowTaskResult();
				List<Double> numbers = new ArrayList<Double>(); 
				for(String s : toTest.split("\\*")) {
					numbers.add(Double.valueOf(s));
				}
				List<Object> data = new ArrayList<Object>();
				data.add(numbers);
				data.add(0); // index
				data.add((double)0); // result
				result.setData(data);
				return result;
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
		};
		w.setStartingTask(splitterTask);
		
		WorkflowTask calculator = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				List<Double> numbers = (List<Double>) lastResult.getData().get(0);
				int idx = (int) lastResult.getData().get(1);
				double result = (double) lastResult.getData().get(2);
				
				if(numbers.size() <= idx) return null;
				
				if(idx == 0) {
					result = numbers.get(idx);
					idx++;
				}
				else {
					result *= numbers.get(idx);
					idx++;
				}

				List<Object> data = Arrays.asList(numbers,idx,result);
				
				return new WorkflowTaskResult(WorkflowStatus.SUCCESS,data);
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
		};
		splitterTask.addNext(calculator);
		
		WorkflowOperationConditional startCondition = new WorkflowOperationConditional(calculator) {
			
			@Override
			public boolean condition(WorkflowTaskResult lastResult, WorkflowOperationConditional self) {
				List<Double> numbers = (List<Double>) lastResult.getData().get(0);
				int idx = (int) lastResult.getData().get(1);
				return numbers.size() > idx;
			}
		};
		startCondition.addNext(calculator);
		
		WorkflowTask endCalculus = new WorkflowTask() {
			
			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				calculusResult.setData(lastResult.getData());
				
				return null;
			}
			
			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
		};
		WorkflowOperationConditional endCondition = new WorkflowOperationConditional(calculator) {
			
			@Override
			public boolean condition(WorkflowTaskResult lastResult, WorkflowOperationConditional self) {
				List<Double> numbers = (List<Double>) lastResult.getData().get(0);
				int idx = (int) lastResult.getData().get(1);
				return numbers.size() <= idx;
			}
		};
		endCondition.addNext(endCalculus);
		
		try {
			manager.executeWorkflow(w);
			if(calculusResult != null) System.out.println("the result: "+calculusResult.getData().get(2));
			else System.out.println("there was an error in execution");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
