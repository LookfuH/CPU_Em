public class Scheduler implements Runnable {

	final int quantumTime = 3;

	private static final CPU_Sim cpu = new CPU_Sim();
	private static final CPU_Sim io = new CPU_Sim(); // Reusing CPU_Sim as it is comparable
	private static final bufferBound cpu_queue = new bufferBound();
	private static final bufferBound io_queue = new bufferBound();
	private static int currentTime = 0;

	public static void insert(Process_Sim process) {
		// if (process.priority < currentPriority) {
		// 	cpu.stopProcess();
		// }
		if (process.burstTime > 0) {
			System.out.println(process.name + " --> Waiting queue (System time: " + currentTime + ")");
			cpu_queue.push(process);
		} else if (process.nextBurst()) {
			// TODO: Put IO handling here!
			System.out.println(process.name + " --> IO");
			insert(process);
		}
	}

	@Override
	public void run() {

		Thread cpuThread = new Thread(cpu);
		Thread ioThread = new Thread(io);
		cpuThread.start();
		ioThread.start();

		/*
			TODO:
			We are supposed to simulate time here, not do it actually. Each loop should tick the CPU to advance it one unit time.
			Each loop should also check the current processes in the queue, and determine what the scheduling should be (cpu.stopProcess should be handled down here instead)
		 	Each loop should check what the next queued process is. If no process is aprocessible, do CPU Idle.
		*/
		while (true) {

			if(cpu.process == null && cpu_queue.peek() != null) {
				cpu.process = cpu_queue.pop();
				System.out.println(cpu.process.name + " --> CPU (System time: " + currentTime + ")");
			}
			if(io.process == null && io_queue.peek() != null) {
				io.process = io_queue.pop();
				System.out.println(io.process.name + " --> IO (System time: " + currentTime + ")");
			}

			try {
				if (cpu.process.burstTime <= 0) {
					if (cpu.process.nextBurst()) {
						if (io.process == null)
							io.process = cpu.process;
						else
							io_queue.push(cpu.process);
						System.out.println(cpu.process.name + " --> IO queue (System time: " + currentTime + ")");
					}
					cpu.process = cpu_queue.pop();
					System.out.println(cpu.process.name + " --> CPU (System time: " + currentTime + ")");
					cpu.quantumTime = 0;
				} else if (cpu_queue.peek().priority == cpu.process.priority && cpu.quantumTime >= quantumTime) {
					cpu_queue.push(cpu_queue.pop());
					cpu.process = cpu_queue.pop();
					cpu.quantumTime = 0;
					System.out.println(cpu.process.name + " --> CPU (System time: " + currentTime + ")");
				}
			} catch (NullPointerException e) {}

			try {
				if (io.process.burstTime <= 0) {
					if (io.process.nextBurst()) {
						cpu_queue.push(io.process);
						System.out.println(io.process.name + " --> CPU queue (System time: " + currentTime + ")");
					}
					io.process = io_queue.pop();
					io.quantumTime = 0;
				}
			} catch (NullPointerException e) {}

			try {
				if (cpu_queue.peek().priority < cpu.process.priority) {
					System.out.println(cpu.process.name + " --> CPU queue (System time: " + currentTime + ")");
					cpu_queue.push(cpu.process);
					cpu.process = cpu_queue.pop();
					System.out.println(cpu.process.name + " --> CPU (System time: " + currentTime + ")");
					cpu.quantumTime = 0;
				}
			} catch (NullPointerException e) {}

			currentTime++;
			cpu.tick();
			io.tick();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("Scheduler has insomnia.");
				e.printStackTrace();
			}
		}
	}
}
