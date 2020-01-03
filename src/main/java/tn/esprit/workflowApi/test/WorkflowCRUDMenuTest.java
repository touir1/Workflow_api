package tn.esprit.workflowApi.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tn.esprit.workflowApi.Log;
import tn.esprit.workflowApi.Workflow;
import tn.esprit.workflowApi.WorkflowManager;
import tn.esprit.workflowApi.WorkflowStatus;
import tn.esprit.workflowApi.Operation.WorkflowOperationConditional;
import tn.esprit.workflowApi.Result.WorkflowTaskResult;
import tn.esprit.workflowApi.Task.WorkflowTask;
import tn.esprit.workflowApi.Task.WorkflowTaskObject;
import tn.esprit.workflowApi.test.entities.Client;

public class WorkflowCRUDMenuTest {

	private static final String MENU_INDEX_PARAMETER = "menuIndex";

	private static Scanner scan = null;

	public static void main(String[] args) {
		try {
			FileOutputStream fout = new FileOutputStream("workflow.log");
			// creating Printstream obj
			PrintStream out = new PrintStream(fout);
			Log.clearPrintStream();
			Log.addPrintStream(out);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		scan = new Scanner(System.in);

		WorkflowManager manager = WorkflowManager.getInstance();
		Workflow w = manager.createWorkflow();

		List<Client> clients = new ArrayList<Client>();

		/** MENU SCREEN TASK **/
		WorkflowTask menuTask = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				System.out.println("====== Gestion des clients ======");
				System.out.println("=== Choix:");
				System.out.println("1- Afficher la liste des clients");
				System.out.println("2- Ajouter un client");
				System.out.println("3- Modifier un client");
				System.out.println("4- Supprimer un client");
				System.out.println("5- Quitter");
				return new WorkflowTaskResult(WorkflowStatus.SUCCESS, choixLoop(1,5));
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}

		};
		w.setStartingTask(menuTask);

		/** MENU INDEXES (CONDITIONAL) **/
		WorkflowOperationConditional listCondition = new WorkflowOperationConditional(menuTask) {

			@Override
			public boolean condition(WorkflowTaskResult lastResult, WorkflowOperationConditional self) {
				if (lastResult != null && lastResult.getData() != null && lastResult.getData().size() > 0
						&& lastResult.getData().get(0) instanceof Integer
						&& ((int) lastResult.getData().get(0)) == (int) self.getParameter(MENU_INDEX_PARAMETER))
					return true;
				return false;
			}

		};
		listCondition.setParameter(MENU_INDEX_PARAMETER, 1);
		WorkflowOperationConditional addCondition = listCondition.clone();
		addCondition.setParameter(MENU_INDEX_PARAMETER, 2);
		WorkflowOperationConditional updateCondition = listCondition.clone();
		updateCondition.setParameter(MENU_INDEX_PARAMETER, 3);
		WorkflowOperationConditional deleteCondition = listCondition.clone();
		deleteCondition.setParameter(MENU_INDEX_PARAMETER, 4);

		/** LIST SCREEN TASK **/
		WorkflowTask listTask = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				System.out.println("====== Gestion des clients: liste ======");
				if(clients.size() <= 0) {
					System.out.println("Pas de clients à afficher.");
					return null;
				}
				for (Client c : clients) {
					System.out.println(c);
				}

				return null;
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}

		};
		listCondition.addNext(listTask);
		listTask.addNext(menuTask);
		
		/** ADD SCEEN TASK **/
		WorkflowTask addTask = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				Client client = new Client();
				System.out.println("====== Gestion des clients: ajout ======");
				boolean saisieDone = false;
				do {
					if(saisieDone && clients.contains(client)) System.out.println("Le cin "+client.getCin()+" existe déjà.");
					System.out.print("Saisir cin (obligatoire, 8 chiffres): ");
					client.setCin(scan.nextLine().trim());
					saisieDone = true;
				}
				while(client.getCin() == null || client.getCin().length() <= 0 || !client.getCin().matches("[0-9]{8}") || clients.contains(client));
				System.out.print("Saisir prenom: ");
				client.setFirstName(scan.nextLine());
				System.out.print("Saisir nom: ");
				client.setLastName(scan.nextLine());
				System.out.print("Saisir email: ");
				client.setEmail(scan.nextLine());
				System.out.print("saisir telephone: ");
				client.setPhoneNumber(scan.nextLine());
				System.out.println("Vous avez saisi "+client+ ".");
				System.out.println("=== Choix:");
				System.out.println("1- Valider");
				System.out.println("2- Annuler");
				int choix = choixLoop(1,2);
				if(choix == 1) clients.add(client);
				return null;
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}
			
		};
		addCondition.addNext(addTask);
		addTask.addNext(menuTask);
		
		/** UPDATE SCREEN TASK **/
		WorkflowTask updateTask = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				Client client = new Client();
				System.out.println("====== Gestion des clients: modification ======");
				if(clients.size() <= 0) {
					System.out.println("Pas de clients à modifier.");
					return null;
				}
				boolean saisieDone = false;
				do {
					if(saisieDone && !clients.contains(client)) System.out.println("Le cin "+client.getCin()+" n'existe pas.");
					System.out.print("Saisir cin (ou 1 pour revenir au menu): ");
					client.setCin(scan.nextLine().trim());
					if(client.getCin().equals("1")) return null;
					saisieDone = true;
				}
				while(!clients.contains(client));
				String saisie = "";
				Client oldClient = clients.get(clients.indexOf(client));
				System.out.print("Saisir prenom ["+oldClient.getFirstName()+"]: ");
				saisie = scan.nextLine();
				client.setFirstName(saisie.trim().equals("")?oldClient.getFirstName():saisie);
				System.out.print("Saisir nom ["+oldClient.getLastName()+"]: ");
				saisie = scan.nextLine();
				client.setLastName(saisie.trim().equals("")?oldClient.getLastName():saisie);
				System.out.print("Saisir email ["+oldClient.getEmail()+"]: ");
				saisie = scan.nextLine();
				client.setEmail(saisie.trim().equals("")?oldClient.getEmail():saisie);
				System.out.print("saisir telephone ["+oldClient.getPhoneNumber()+"]: ");
				saisie = scan.nextLine();
				client.setPhoneNumber(saisie.trim().equals("")?oldClient.getPhoneNumber():saisie);
				System.out.println("Ancienne donnée: "+oldClient+".");
				System.out.println("Remplacez par "+client+ "?");
				System.out.println("=== Choix:");
				System.out.println("1- Valider");
				System.out.println("2- Annuler");
				int choix = choixLoop(1,2);
				if(choix == 1) clients.set(clients.indexOf(oldClient),client);
				return null;
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}
			
		};
		updateCondition.addNext(updateTask);
		updateTask.addNext(menuTask);
		
		/** DELETE SCREEN TASK **/
		WorkflowTask deleteTask = new WorkflowTask() {

			@Override
			public WorkflowTaskResult execute(WorkflowTaskResult lastResult, WorkflowTaskObject self) throws Exception {
				Client client = new Client();
				System.out.println("====== Gestion des clients: suppression ======");
				if(clients.size() <= 0) {
					System.out.println("Pas de clients à supprimer.");
					return null;
				}
				boolean saisieDone = false;
				do {
					if(saisieDone && !clients.contains(client)) System.out.println("Le cin "+client.getCin()+" n'existe pas.");
					System.out.print("Saisir cin (ou 1 pour revenir au menu): ");
					client.setCin(scan.nextLine().trim());
					if(client.getCin().equals("1")) return null;
					saisieDone = true;
				}
				while(!clients.contains(client));
				Client oldClient = clients.get(clients.indexOf(client));
				System.out.println("Validez-vous la suppression du client: "+oldClient+"?");
				System.out.println("=== Choix:");
				System.out.println("1- Valider");
				System.out.println("2- Annuler");
				int choix = choixLoop(1,2);
				if(choix == 1) clients.remove(client);
				return null;
			}

			@Override
			public void onSuccess(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}

			@Override
			public void onFailure(WorkflowTaskResult result, WorkflowTaskObject self) throws Exception {
			}
			
		};
		deleteCondition.addNext(deleteTask);
		deleteTask.addNext(menuTask);
		

		/** START WORKFLOW **/
		try {
			manager.executeWorkflow(w);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int choixLoop(int min, int max) {
		int choix = 0;
		do {
			System.out.print("Votre choix: ");
			String input = scan.nextLine();
			try {
				choix = Integer.parseInt(input);
			} catch (Exception e) {
				continue;
			}
		} while (choix < min || choix > max);
		return choix;
	}
}
