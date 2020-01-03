package tn.esprit.workflowApi;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Log {
	private Log() {}
	
	private static List<PrintStream> streams = new ArrayList<PrintStream>(Arrays.asList(System.out));
	private static SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy 'at' hh:mm:ss.SSS");
	
	public static void addPrintStream(PrintStream printStream) {
		streams.add(printStream);
	}
	
	public static void clearPrintStream() {
		streams.clear();
	}
	
	public static void info(String text) {
		String header = "[INFO] " + ft.format(new Date())+" : ";
		for(PrintStream stream : streams) {
			stream.println(header + text);
		}
	}
	
	public static void error(String text) {
		String header = "[ERROR] " + ft.format(new Date())+" : ";
		for(PrintStream stream : streams) {
			stream.println(header + text);
		}
	}
}
