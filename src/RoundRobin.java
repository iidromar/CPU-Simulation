public class RoundRobin{
	

	public void schedule(LinkedQueue processes, int quantum) {
		int count = 0; String id = "  "; String time = "0"; //Strings for displaying the "Gantt Chart"
		float waitingTime = 0; float total = 0; float completionTime = 0; //float values to keep track of times and the number of processes
		while (!processes.empty()){
			
			Process p = processes.serve(); //Serves the first process to perform operations.
			
			if (p.burstTime <= quantum) { //if the process' remaining burst time is less than or equal to the Quantum Time
				count += p.burstTime; //Add the remaining BT to the count
				waitingTime += count - p.originalBurstTime; total++; //Calculate waiting time and increment the total processes
				completionTime += count; //Calculate completion time
				p.burstTime = 0; //Zeroes the process' BT, since that is the minimum value.
				
			} else { //Otherwise
				p.burstTime -= quantum; //Decrease the burst time by the quantum time
				count += quantum; //increase count by adding quantum
				processes.enqueue(p); //Return the process to the queue.
				
			} time = CPUEmulation.formatTime(time, p.PID);
			id += p.PID; time += count; //If the new one's burst time is shorter, the current process is added to the Gantt Chart
			id = CPUEmulation.formatName(id, count);
		}			
		System.out.println("Processes: " + id + "\nBurst Time: " + time); //Prints out the Gantt Chart.
		System.out.println("Average Waiting Time: " + waitingTime/total); //Prints out the average waiting time.
		System.out.println("Average Completion Time: " + completionTime/total); //Prints out the average completion time.
		CPUEmulation.wait[1] = waitingTime/total; CPUEmulation.completion[1] = completionTime/total; //Stores the aforementioned average times in an array.
	}
}
