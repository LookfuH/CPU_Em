public class Scheduler implements Runnable {

	final int quantumTime = 3;

	private final CPU_Sim cpu = new CPU_Sim();
	private static final bufferBound queue = new bufferBound();
	private static int currentPriority = -1;

	public void insert(Process_Sim x) {
		queue.push(x);
		if (x.priority < currentPriority) {
			cpu.stopProcess();
		}
	}

	@Override
	public void run() {

		Process_Sim val;
		while (true) {
			val = queue.pop();
			System.out.println(val.name + " --> CPU");

			int nextPriority;
			try {
				nextPriority = queue.peek().priority;
			} catch (NullPointerException e) {
				nextPriority = -1;
			}

			int runTime;
			if (nextPriority == val.priority) {
				// Do round-robin
				runTime = val.burstTime > quantumTime ? quantumTime : val.burstTime; // Check if burst left is less than quantum time, and set accordingly
			} else {
				runTime = val.burstTime;
			}

			val = cpu.runProcesses(val, runTime);

			if (val.burstTime > 0) {
				insert(val);
			} else if (val.nextBurst()) {
				// TODO: Put IO handling here!
				System.err.println("Next CPU Burst NOW");
				insert(val);
			}
		}
	}
}
