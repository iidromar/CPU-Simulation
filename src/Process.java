
public class Process {
	public String PID;
	public int arrivalTime;
	public int burstTime;
	public int priority;
	public int originalBurstTime;
	public Process next;
	//FCFS
	public Process() {
		PID = "";
		arrivalTime = 0;
		burstTime = 0;
		priority = 0;
		originalBurstTime = 0;
		next = null;
	}

}
