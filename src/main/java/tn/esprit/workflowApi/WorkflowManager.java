package tn.esprit.workflowApi;

public class WorkflowManager {
	private static WorkflowManager instance;
	
	private WorkflowManager() {
		super();
	}
	
	public WorkflowManager getInstance() {
		if(instance == null) {
			return instance = new WorkflowManager();
		}
		
		return instance;
	}
	
	public void executeWorkflow(Workflow w) {
		// TODO execute workflow
	}
	
	public Workflow createWorkflow() {
		return new Workflow();
	}
}
