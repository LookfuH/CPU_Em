public class Scheduler implements Runnable {

	final int quantumTime = 3;

	private static final CPU_Sim cpu = new CPU_Sim();
	private static final bufferBound queue = new bufferBound();
	private static int currentPriority = -1;

	public static void insert(Process_Sim x) {
		queue.push(x);
		if (x.priority < currentPriority) {
			cpu.stopProcess();
		}
	}

	@Override
	public void run() {

		/*
			TODO:
			We are supposed to simulate time here, not do it actually. Each loop should tick the CPU to advance it one unit time.
			Each loop should also check the current processes in the queue, and determine what the scheduling should be (cpu.stopProcess should be handled down here instead)
		 	Each loop should check what the next queued process is. If no process is avalible, do CPU Idle.
		*/

		Process_Sim val;
		while (true) {
			val = queue.pop();
			System.out.println(val.name + " --> CPU");

			currentPriority = val.priority;
			int nextPriority;
			try {
				nextPriority = queue.peek().priority;
			} catch (NullPointerException e) {
				nextPriority = -1;
			}

			int runTime;
			if (nextPriority == val.priority) {
				// Do round-robin //
				runTime = val.burstTime > quantumTime ? quantumTime : val.burstTime; // Check if burst left is less than quantum time, and set accordingly
			} else {
				runTime = val.burstTime;
			}

			val = cpu.runProcesses(val, runTime);

			if (val.burstTime > 0) {
				insert(val);
			} else if (val.nextBurst()) {
				// TODO: Put IO handling here!
				System.out.println("Next CPU Burst NOW"); // TODO: REMOVE ME!
				insert(val);
			}
		}
	}
}
