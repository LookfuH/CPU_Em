import java.util.ArrayList;

public class bufferBound {

	private ArrayList<Process_Sim> buffer = new ArrayList<Process_Sim>();

	public synchronized void insert(Process_Sim item) { // Works!
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

		try {
			System.out.println("Waiting");
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Process_Sim item;

		item = buffer.removeFirst();
		notify();
		return item;

	}

	public String printArr(){
		String result = "";
		for (int i = 0; i < buffer.size(); i++){
			result += buffer.get(i).name + ", ";
		}

		return result;
	}

}
