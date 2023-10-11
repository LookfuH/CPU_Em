public class Scheduler implements Runnable {

	private static bufferBound queue = new bufferBound();

	public static void insert(Process_Sim x) {
		queue.insert(x);
		System.out.println(queue.printArr());
	}

	@Override
	public void run() {
		System.out.println("HI");
		Process_Sim val;
		while (true) {
			val = queue.remove();
			System.out.println("Grabbed process");
		}
	}
}
