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
	}

	public synchronized Process_Sim pop() {

		if (buffer.size() == 0){
			return null;
		}

		return buffer.remove(0);
	}

	public Process_Sim peek() {
		if (buffer.size() == 0){
			return null;
		}
		return buffer.get(0);
	}

	public String printArr(){
		String result = "";
		int i = 0;
		for (; i < buffer.size() - 1; i++){
			result += buffer.get(i).name + ", ";
		}

		try {
			result += buffer.get(i).name + " ";
		} catch (IndexOutOfBoundsException e) {}

		return result;
	}

}
