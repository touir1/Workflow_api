package tn.esprit.workflowApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.esprit.workflowApi.Operation.*;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;
import tn.esprit.workflowApi.Task.*;

public class WorkflowManager {
	private static WorkflowManager instance;

	private static long lastUniqueID = 0;

	private WorkflowManager() {
		super();
	}

	public static WorkflowManager getInstance() {
		if (instance == null) {
			return instance = new WorkflowManager();
		}

		return instance;
	}

	static synchronized long getNextUniqueID() {
		return ++WorkflowManager.lastUniqueID;
	}

	public void executeWorkflow(Workflow w) throws Exception {
		// w.startWorkflow();

		WorkflowTaskObject task = w.getStartingTask();
		Log.info("The workflow "+w.getUniqueID() + " has started");
		executeTask(task, null);
		Log.info("The workflow "+w.getUniqueID() + " has ended");
	}

	private synchronized WorkflowTaskResult executeTask(WorkflowTaskObject task, WorkflowTaskResult lastResult) throws Exception {
		Log.info("starting task: " + task.getUniqueID());

		Map<String, WorkflowTaskResult> data = new HashMap<String, WorkflowTaskResult>();
		WorkflowTaskResult result = null;
		if (task != null) {
			try {
				task.setStatus(WorkflowStatus.IN_PROGRESS);

				Thread t = new Thread() {

					@Override
					public void run() {
						WorkflowTaskResult res = null;
						try {
							res = task.execute(lastResult);
						} catch (Exception e) {
							res = new WorkflowTaskResult(WorkflowStatus.FAILURE, null);
							res.setMessage(e.getMessage());
						}
						
						if(res == null) {
							res = new WorkflowTaskResult();
						}
						data.put("lastResult", res);
						if(res != null)
							task.setStatus(res.getStatus());
						else
							task.setStatus(WorkflowStatus.SUCCESS);
					}
				};
				t.start();

				while (task.getStatus().equals(WorkflowStatus.IN_PROGRESS)) {
					Thread.sleep(1);
					//Log.info("waiting for "+task.getUniqueID()+" ...");
				}
				result = data.get("lastResult");
				List<WorkflowObject> next = task.getNextList();

				if (result.getStatus().equals(WorkflowStatus.SUCCESS)) {
					Log.info("task " + task.getUniqueID() + " finished with status: " + result.getStatus());
					task.onSuccess(result);
					for (WorkflowObject n : next) {
						if (n instanceof WorkflowOperation) {
							executeOperation((WorkflowOperation) n, task, result);
						} else if (n instanceof WorkflowTaskObject) {
							executeTask((WorkflowTaskObject) n, result);
						}
					}
				} else {
					Log.error("task " + task.getUniqueID() + " finished with status: " + result.getStatus());
					if(result.getMessage() != null) Log.error("error message: "+result.getMessage());
					task.onFailure(result);

					for (WorkflowObject n : next) {
						if (n instanceof WorkflowOperationOR) {
							executeOperation((WorkflowOperation) n, task, result);
						}
					}
				}

			} catch (Exception e) {
				task.setStatus(WorkflowStatus.FAILURE);
				List<WorkflowObject> next = task.getNextList();
				WorkflowTaskResult errorResult = new WorkflowTaskResult(WorkflowStatus.FAILURE, null);
				errorResult.setMessage(e.getMessage());
				Log.error("task " + task.getUniqueID() + " encountered a problem while running.");
				Log.error("error message: "+e.getMessage());
				
				task.onFailure(errorResult);
			}

		}

		return result;
		// return task.execute(lastResult);
	}

	private synchronized WorkflowTaskResult executeOperation(WorkflowOperation operation, WorkflowObject lastObject,
			WorkflowTaskResult lastResult) {
		WorkflowTaskResult result = lastResult;

		try {
			if (!result.getStatus().equals(WorkflowStatus.SUCCESS)) {
				if (operation instanceof WorkflowOperationOR) {
					WorkflowOperationOR op = (WorkflowOperationOR) operation;

					if (op.getFirst().getUniqueID() == lastObject.getUniqueID()) {
					} else {
						op.setSecondFinished(true);
					}

					if (op.getResult() == null) {
						op.setResult(new WorkflowTaskResult(WorkflowStatus.IN_PROGRESS, result));
					} else {
						WorkflowTaskResult res = op.getResult();
						res.getData().add(lastResult);
						res.setStatus(WorkflowStatus.FAILURE);

						for (WorkflowObject n : op.getNextList()) {
							if (n instanceof WorkflowOperationOR) {
								executeOperation((WorkflowOperation) n, op, op.getResult());
							}
						}

					}

				} else {
					// WorkflowOperationAND op = (WorkflowOperationAND) operation;
					operation.setStatus(WorkflowStatus.FAILURE);

					if (operation.getResult() == null) {
						operation.setResult(new WorkflowTaskResult(WorkflowStatus.FAILURE, result));
					} else {
						WorkflowTaskResult res = operation.getResult();
						res.getData().add(lastResult);
						res.setStatus(WorkflowStatus.FAILURE);
					}

					for (WorkflowObject n : operation.getNextList()) {
						if (n instanceof WorkflowOperationOR) {
							executeOperation((WorkflowOperation) n, operation, operation.getResult());
						}
					}
				}
			} /*else if (operation instanceof WorkflowOperationEnd) {
				Log.info("the workflow ended");
			}*/ else if (operation instanceof WorkflowOperationTimer) {
				WorkflowOperationTimer timer = (WorkflowOperationTimer) operation;
				Log.info("timerOperation(" + timer.getTime() + ") starting");
				timer.startOperation();
				while (!timer.finished()) {
					Thread.sleep(1);
					//Log.info("waiting for "+timer.getUniqueID()+" ...");
				}
				Log.info("timerOperation(" + timer.getTime() + ") ended");
				for (WorkflowObject n : timer.getNextList()) {
					if (n instanceof WorkflowOperation) {
						executeOperation((WorkflowOperation) n, timer, result);
					} else if (n instanceof WorkflowTaskObject) {
						executeTask((WorkflowTaskObject) n, result);
					}
				}
			} else if (operation instanceof WorkflowOperationAND) {
				WorkflowOperationAND op = (WorkflowOperationAND) operation;
				if (op.getFirst().getUniqueID() == lastObject.getUniqueID()) {
					op.setFirstFinished(true);
				} else {
					op.setSecondFinished(true);
				}
				if (op.getFirstFinished() && op.getSecondFinished()) {
					operation.setStatus(WorkflowStatus.SUCCESS);
				} else {
					operation.setStatus(WorkflowStatus.IN_PROGRESS);
				}

				if (operation.getResult() == null) {
					operation.setResult(new WorkflowTaskResult(WorkflowStatus.IN_PROGRESS, result));
				} else {
					WorkflowTaskResult res = operation.getResult();
					res.getData().add(lastResult);
					res.setStatus(WorkflowStatus.SUCCESS);

					for (WorkflowObject n : operation.getNextList()) {
						if (n instanceof WorkflowOperation) {
							executeOperation((WorkflowOperation) n, op, op.getResult());
						} else if (n instanceof WorkflowTaskObject) {
							executeTask((WorkflowTaskObject) n, op.getResult());
						}
					}
				}

			} else if (operation instanceof WorkflowOperationOR) {
				WorkflowOperationOR op = (WorkflowOperationOR) operation;
				if (op.getFirst().getUniqueID() == lastObject.getUniqueID()) {
					op.setFirstFinished(true);
				} else {
					op.setSecondFinished(true);
				}
				operation.setStatus(WorkflowStatus.SUCCESS);

				if (operation.getResult() == null) {
					operation.setResult(new WorkflowTaskResult(WorkflowStatus.SUCCESS, result));
				} else {
					WorkflowTaskResult res = operation.getResult();
					res.getData().add(lastResult);
					res.setStatus(WorkflowStatus.SUCCESS);
				}

				for (WorkflowObject n : operation.getNextList()) {
					if (n instanceof WorkflowOperation) {
						executeOperation((WorkflowOperation) n, op, op.getResult());
					} else if (n instanceof WorkflowTaskObject) {
						executeTask((WorkflowTaskObject) n, op.getResult());
					}
				}
			}
			else if(operation instanceof WorkflowOperationConditional) {
				WorkflowOperationConditional op = (WorkflowOperationConditional) operation;
				op.setPreviousFinished(true);
				op.setStatus(WorkflowStatus.SUCCESS);
				op.setResult(result);

				if(op.condition(result)) {
					for (WorkflowObject n : operation.getNextList()) {
						if (n instanceof WorkflowOperation) {
							executeOperation((WorkflowOperation) n, op, op.getResult());
						} else if (n instanceof WorkflowTaskObject) {
							executeTask((WorkflowTaskObject) n, op.getResult());
						}
					}
				}
			}
		} catch (Exception e) {
			// operation.setStatus(WorkflowStatus.FAILURE);
			Log.error("operation " + operation.getUniqueID() + " encountered a problem while running.");
			Log.error(e.getMessage());
		}
		return result;
	}

	public Workflow createWorkflow() {
		return new Workflow();
	}
}
