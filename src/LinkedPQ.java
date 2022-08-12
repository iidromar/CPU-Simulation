
public class LinkedPQ {
	private int size;
	public Process head;
	
	public LinkedPQ() {
		head = null;
		size = 0; }
	
	public int length() {
		return size; }
	
	public boolean full() {
		return false; }
	
	public boolean empty() {
		return size == 0; }
	
	public void enqueue(Process p, int prior) {
		if (empty() || prior < head.priority) {
			p.next = head;
			head = p; }
		else {
			Process x = head;
			Process y = null;
			while ((x != null) && (prior >= x.priority)) {
				y = x;
				x = x.next; }
			p.next = x;
			y.next = p; }
			size++; }
	
	public Process serve() {
		if (empty())
			return null;
		Process p = head;
		head = head.next;
		size--;
		return p; }

}
