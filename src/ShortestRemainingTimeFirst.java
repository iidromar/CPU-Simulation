
	public class ShortestRemainingTimeFirst {
		LinkedPQ processes = new LinkedPQ(); //This priority queue is ordered by burst time.
	
	
	public void schedule(LinkedPQ aux) {
		int count = 0; String id = " "; String time = ""; //Strings for displaying the "Gantt Chart"
		float waitingTime = 0; float total = 0; float completionTime = 0; //float values to keep track of times and the number of processes
		int prevBT = 0; //an integer to keep track of a process' burst time when it was first served out of the queue.
		while (processes.empty()) { //Checks if queue is empty
			if (count == aux.head.arrivalTime) { //If the counter is equal to the arrival time of the head process.
				Process t = aux.serve(); //serve the process,
				t.priority = t.burstTime; //change the priority.
				processes.enqueue(t, t.burstTime); } //enqueue it.}
			else count++; } //Otherwise, the counter will increase to reflect the actual time the process began.
		time += count;
		id = CPUEmulation.formatName(id, count);
		while (!processes.empty()) { 
			Process p = processes.serve(); //Serves the first process to perform operations.
			prevBT = p.burstTime; //Saves the initial burst time
			while (!aux.empty() && (aux.head.arrivalTime == p.arrivalTime)) { //This is to check whether there are multiple processes with the same arrival time.
				Process tmp = aux.serve(); //In case there is, it is served out of the auxiliary queue.
				tmp.priority = tmp.burstTime; //Like usual, the priority will change.
				System.out.println(p.PID + "-"+ tmp.PID);
				if (p.burstTime > tmp.burstTime) { //If the "new" process" has a shorter burst time: 
					processes.enqueue(p, p.burstTime);  //It will place the old process in the queue
					p = tmp; } //And then the new process will take its place.
				else {
					processes.enqueue(tmp, tmp.burstTime); } //Otherwise, we place the new process in the queue.
				}
			
			while (p.burstTime > 0) { //Checks if the process is done or not.
				
				while (!aux.empty() && count >= aux.head.arrivalTime) { //If a new process arrives while the CPU is still working on one
					Process q = aux.serve(); //It will be served out of the auxiliary queue.
					q.priority = q.burstTime; //And its priority will be changed.
					
					while (!aux.empty() && (aux.head.arrivalTime == q.arrivalTime)) { //This is to check if the newly arrived process has any other processes with the same arrival time.
						Process tmp = aux.serve(); //If there is, it serves it from the auxiliary queue.
						tmp.priority = tmp.burstTime; //Changes the priority.
						if (q.burstTime > tmp.burstTime) { //if process A has a longer burst time than process B
							processes.enqueue(q, q.burstTime); //It enqueues process A
							q = tmp; } //and process B takes its place.
						else {
								processes.enqueue(tmp, tmp.burstTime); } //Otherwise, process B is enqueued.
						}
					
					if (q.burstTime < p.burstTime) { //Finally, the current process's burst time is compared with the new one's.
						if (p.burstTime != prevBT) { //This is a check to prevent redundant additions to the Gannt Chart
							time = CPUEmulation.formatTime(time, p.PID);
							id += p.PID; time += count; //If the new one's burst time is shorter, the current process is added to the Gantt Chart
							id = CPUEmulation.formatName(id, count); }
						p.priority = p.burstTime; //Change the current process's priority, just in case.
						processes.enqueue(p, p.burstTime); //Puts the current process back into the queue.
						p = q; } //replaces the current process with the new one.
					else {
						q.priority = q.burstTime; //Otherwise, we change the new process's priority.
						processes.enqueue(q, q.burstTime); } //And enqueue it.
					}
				
				p.burstTime--; count++; } //Reduce the burst time and increase the counter, as if it's an actual CPU.
			
			time = CPUEmulation.formatTime(time, p.PID);
			id += p.PID;; time += count; //Once the CPU is done, it adds the process and counter to the Gantt Chart
			id = CPUEmulation.formatName(id, count);
			waitingTime += count - p.originalBurstTime - p.arrivalTime; total++; //Calculate waiting time and increment the total processes
			completionTime += count - p.arrivalTime; //Calculate completion time
			if (processes.empty() && !aux.empty()) { //Checks if the queue is empty and if the auxiliary queue isn't empty.
				p = aux.serve(); //Serves the head of the auxiliary queue.
				count = p.arrivalTime; //Change the counter to reflect the arrival time.
				time = CPUEmulation.formatTime(time, "="); //Formats the Gantt Chart to reflect the idle time.
				id += "="; time += count; //Adds id and time to gantt chart
				id = CPUEmulation.formatName(id, count); //Formats the Gannt Chart to reflect the lack of an active process.
				p.priority = p.burstTime; //Changes the priority.
				processes.enqueue(p, p.burstTime); } //Places the process in the queue.
			}
		System.out.println("Processes: " + id + "\nBurst Time: " + time); //Prints out the Gantt Chart.
		System.out.println("Average Waiting Time: " + waitingTime/total); //Prints average waiting time.
		System.out.println("Average Completion Time: " + completionTime/total); //Prints average completion time
		CPUEmulation.wait[0] = waitingTime/total; CPUEmulation.completion[0] = completionTime/total; //Stores the aforementioned times in an array
	}

}


