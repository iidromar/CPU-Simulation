
public class LinkedQueue {

	public Process head, tail;
	public int size;
	
	public LinkedQueue() {
		head = tail = null;
		size = 0; }
	
	public void enqueue(Process p) {
		if (full())
			return;
		if (tail == null)
			tail = head = p;
		else {
			tail.next = p;
			tail = tail.next; }
		size++;
	}

	public Process serve() {
		Process p = head;
		head = head.next; size--;
		if (size == 0)
			tail = null;
		return p;
	}

	public int length() {
		return size;
	}

	public boolean full() {
		return false;
	}
	
	public boolean empty() {
		return length() == 0;
	}
	


}
