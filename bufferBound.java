import java.util.ArrayList;

public class bufferBound {

	private ArrayList<Process_Sim> buffer = new ArrayList<Process_Sim>(null);

	public synchronized void insert(Process_Sim item) {
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

		notify();
	}

	public synchronized Process_Sim remove() {

		Process_Sim item;

		item = buffer.removeFirst();
		notify();
		return item;

	}


}
