import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class CPUEmulation {
	
	static float[] wait = new float[3]; static float[] completion = new float[3];
	
	public static void main(String[] args) throws FileNotFoundException {
		//This is the menu to choose which algorithm you want.
		Scanner kb = new Scanner(System.in);
		int input = 0;
		do {
			String file = null;
			System.out.println("\nChoose action:\n1) Shortest Remaining Time First\n2) Round Robin \n3) Priority\n4) Compare performance\n5) Quit");
			input = kb.nextInt();
			//Case where the user wants to exit (placed here so it doesn't prompt for the file name
			if (input == 5)
				break;
			//Case where the users wishes to perform an algorithm, so test data is necessary.
			if (input != 4) {
				System.out.print("Enter the name of the text file you want without the extension: "); 
				file = kb.next(); }
			
			switch (input) {
				case 1: ShortestRemainingTimeFirst(file); break; //SRTF
				case 2: RoundRobinMethod(file, kb); break; //RR
				case 3: PriorityCaller(file); break; //Priority
				case 4: comparePerformance(); break; //Compare Performance
				default: System.out.println("Unknown command."); }
			
		} while (input != 5);
		kb.close();
	}
	
	public static void ShortestRemainingTimeFirst(String text) {
		try {
			File file = new File(System.getProperty("user.home") + "/Desktop/" + text + ".txt"); 
			Scanner scan = new Scanner(file); //This is for scanning the file.
			ShortestRemainingTimeFirst SJF = new ShortestRemainingTimeFirst(); 
			LinkedPQ aux = new LinkedPQ(); //Auxiliary Priority Queue that orders based on arrival time, because the files may not be in order.
			while (scan.hasNextLine()) { //Goes through every line in the file.
				String x = scan.nextLine().replace(" ", ""); //Scans the line and removes any spaces.
				String[] test = x.split(","); //Splits the line into parts, separating by commas.
				if (x.charAt(0) == '/') //In case the file has a comment or a line that isn't information about the process.
					continue;
				Process p = new Process();
				p.PID = test[0];
				p.arrivalTime = Integer.parseInt(test[1]); p.priority = Integer.parseInt(test[1]); //Priority is equal to arrival time to sort the processes properly.
				p.burstTime = Integer.parseInt(test[2]); //Fills out the process attributes according to the file contents.
				p.originalBurstTime = p.burstTime;
			aux.enqueue(p, p.arrivalTime); } //Places the process in the queue.
			SJF.schedule(aux); //Performs the algorithm.
			scan.close(); }
		
		catch (FileNotFoundException e) { // to catch Not founding the file situation
			System.out.println("File not found. Try again."); }
		
		catch (Exception e) {
			System.out.println("File format incompatible. Try again."); }
		
		}
	
	public static void RoundRobinMethod(String text, Scanner kb) {
		try {
		File file = new File(System.getProperty("user.home") + "/Desktop/" + text + ".txt"); 
		Scanner scan = new Scanner(file); //This is for scanning the file.
		RoundRobin RR = new RoundRobin(); 
		LinkedQueue processes = new LinkedQueue(); //Linked queue to organize processes.
		while (scan.hasNextLine()) { //Goes through every line in the file.
			String Replacable = scan.nextLine().replace(" ", ""); // get the line without spaces
			while (Replacable.charAt(0) == '/') // check for comments
				Replacable = scan.nextLine().replace(" ", "");
			Process p = new Process(); // Initializing the process
			
			p.PID = Replacable;
			Replacable = scan.nextLine().replace(" ", "");
			while (Replacable.charAt(0) == '/') // check for comments
				Replacable = scan.nextLine().replace(" ", "");
			
			String bt = Replacable; //Get the burst time
			p.burstTime = Integer.parseInt(bt); //Fills out the process's burst time
			p.originalBurstTime = p.burstTime;
			processes.enqueue(p); } //Places the process in the queue.
		scan.close();
		System.out.print("Enter quantum: "); 
		int quantum = kb.nextInt();
		RR.schedule(processes, quantum); //Performs the algorithm.
		}
		
		catch (FileNotFoundException e) {
			System.out.println("File not found. Try again."); }
		
		catch (Exception e) {
			System.out.println("File format incompatible. Try again."); }
		
		}
	
	public static void PriorityCaller(String inputFile) {
		try { // try & catch formula
			File file = new File(System.getProperty("user.home") + "/Desktop/" + inputFile + ".txt"); // getting the file from the path
			Scanner scan = new Scanner(file); // Initializing the scanner
			LinkedPQ PLQ = new LinkedPQ(); // LinkedPQ to order the processes by their Priority
			
			while(scan.hasNextLine()) {
				String Replacable = scan.nextLine().replace(" ", ""); // get the line without spaces
				while (Replacable.charAt(0) == '/') // check for comments
					Replacable = scan.nextLine().replace(" ", "");
				String[] SplittedLineBtPP; // Split line for Burst Time and Priority
				Process pro = new Process(); // Initializing the process
				pro.PID = Replacable; // assign the PID 
				Replacable = scan.nextLine().replace(" ", ""); // go to the next line
				while (Replacable.charAt(0) == '/') // check for comments
					Replacable = scan.nextLine().replace(" ", "");
				SplittedLineBtPP = Replacable.split(","); // splitting the another line
				pro.burstTime = Integer.parseInt(SplittedLineBtPP[0]); // convert from string to int
				pro.priority = Integer.parseInt(SplittedLineBtPP[1]);
				pro.arrivalTime = Integer.parseInt(SplittedLineBtPP[0]);
				pro.originalBurstTime = pro.burstTime;
				
				PLQ.enqueue(pro, pro.priority); // putting each process in an Queue
			}
			CPUScheduler(PLQ); // calling the printing method
			scan.close(); // closing the scanner
		} catch(FileNotFoundException e) {
			
        System.out.println("File not found. Try again."); }
		
		catch (Exception e) {
			System.out.println("File format incompatible. Try again."); }
	}
	
	private static void CPUScheduler(LinkedPQ PLQ) {
		int count = 0; String id = "  "; String time = "0"; //Strings for displaying the "Gantt Chart"
		float waitingTime = 0; float total = 0; float completionTime = 0; //float values to keep track of times and the number of processes
		while(!PLQ.empty()) {
			Process temp = PLQ.serve(); // serve the highest priority to print it in Gantt Chart
			time = formatTime(time, temp.PID);
			count += temp.burstTime; // counts the BT
			time += "" + count;	// assigning the total time for each process until entering the CPU
			id += "" + temp.PID; // assigning the PID
			id = formatName(id, count);
			waitingTime += count - temp.originalBurstTime; total++; //Calculate waiting time and increment the total processes
			completionTime += count; } //Calculate completion time
		
		System.out.println("Processes: " + id + "\nBurst Time: " + time); // printing the results
		System.out.println("Average Waiting Time: " + waitingTime/total); //printing average waiting time
		System.out.println("Average Completion Time: " + completionTime/total); //printing average completion time
		wait[2] = waitingTime/total; completion[2] = completionTime/total; //storing most recent waiting and completion time
	}
	
	private static void comparePerformance() { //This method displays the statistics of each algorithm
		System.out.println("Shortest Remaining Time First:\nWaiting Time: " + wait[0] + " ----- Completion Time: " + completion[0]);
		System.out.println("Round Robin:\nWaiting Time: " + wait[1] + " ----- Completion Time: " + completion[1]);
		System.out.println("Priority:\nWaiting Time: " + wait[2] + " ----- Completion Time: " + completion[2]);	}
	
	static String formatTime(String time, String id) { //This method formats the times in the Gannt Chart
		for (int i = 0; i < id.length(); i++)
			time += "_";
		return time; }
	
	static String formatName(String name, int count) { //This method formats the process IDs in the Gannt Chart
		for (int i = 0; i < String.valueOf(count).length(); i++) 
			name += " ";
		return name; }
}
