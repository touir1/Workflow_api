package tn.esprit.workflowApi.Operation;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tn.esprit.workflowApi.WorkflowObject;

public class WorkflowOperationTimer extends WorkflowOperation {

	private long time;
	private boolean finished;

	/**
	 * 
	 * @param time : time in milliseconds
	 */
	public WorkflowOperationTimer(WorkflowObject object, long time) {
		super();

		super.setFirst(object);
		this.time = time;
		this.finished = false;
		
	}
	
	protected WorkflowOperationTimer(WorkflowObject object, long time, Map<String, Object> parameters) {
		super(parameters);

		super.setFirst(object);
		this.time = time;
		this.finished = false;
		
	}

	public void startOperation() {
		TimerTask timer = new TimerTask() {

			@Override
			public void run() {
				finished = true;

			}

		};
		
		Timer tim = new Timer(true);
		tim.schedule(timer, this.time);

	}

	@Override
	public boolean finished() {
		return finished;
	}
	
	@Override
	public WorkflowOperationTimer clone() {
		return new WorkflowOperationTimer(this.getFirst(), this.time, this.parameters);
	}
	
	public void setPrevious(WorkflowObject object) {
		super.setFirst(object);
	}
	
	public WorkflowObject getPrevious() {
		return super.getFirst();
	}
	
	public boolean getPerviousFinished() {
		return super.getFirstFinished();
	}
	
	public void setPreviousFinished(boolean finished) {
		super.setFirstFinished(finished);
	}
	
	public long getTime() {
		return this.time;
	}

}
