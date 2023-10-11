public class Scheduler implements Runnable {

	private static bufferBound queue = new bufferBound();

	public static void insert(Process_Sim x) {
		queue.insert(x);
	}

	@Override
	public void run() {
	
		Process_Sim val;
		while (true) {
			val = queue.remove();
			System.out.println(val.name + " ------------> Cpu");
		}
	}
}
