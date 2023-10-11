public class Scheduler implements Runnable {

	CPU_Sim cpu = new CPU_Sim();
	int quantumTime = 3;

	private static bufferBound queue = new bufferBound();

	public static void insert(Process_Sim x) {
		queue.insert(x);
	}

	@Override
	public void run() {

		Process_Sim val;
		while (true) {
			val = queue.remove();
			System.out.println(val.name + " ------------> CPU");

			int runTime = val.bufferTime > quantumTime ? quantumTime : val.bufferTime;

			val = cpu.runProcesses(val, runTime);

			if (val.bufferTime > 0) {
				insert(val);
			}
		}
	}
}
