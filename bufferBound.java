import java.util.ArrayList;

public class bufferBound {

	private ArrayList<Process_Sim> buffer = new ArrayList<Process_Sim>();

	public synchronized void push(Process_Sim item) { // Works!
		int i = 0;

		while (true) {
			if (i >= buffer.size()){
				buffer.add(item);
				break;
			} else if (buffer.get(i).priority > item.priority){
				buffer.add(i, item);
				break;
			} else
				i++;
		}
		System.out.println(item.name + " --> Queued at index: " + i + " (Priority: " + item.priority + ").");


		notify();
	}

	public synchronized Process_Sim pop() {

		try {
			if (buffer.size() == 0){
				System.out.println("Queue empty --- Waiting on objects.");
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Process_Sim item;

		item = buffer.remove(0);

		//System.out.println("Removed from queue: " + item.name);

		return item;
	}

	public Process_Sim peek() {
		if (buffer.size() == 0){
			return null;
		}
		return buffer.get(0);
	}

	public String printArr(){
		String result = "";
		for (int i = 0; i < buffer.size(); i++){
			result += buffer.get(i).name + ", ";
		}

		return result;
	}

}
