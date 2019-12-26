package tn.esprit.workflowApi.Operation;

import java.util.Timer;
import java.util.TimerTask;

public class WorkflowOperationTimer extends WorkflowOperation {

	private long time;
	private boolean finished;

	/**
	 * 
	 * @param time : time in milliseconds
	 */
	public WorkflowOperationTimer(long time) {
		super();

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

}
