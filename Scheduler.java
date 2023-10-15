import java.util.ArrayList;

public class Scheduler implements Runnable {

	final int quantumTime = 3;

	private static final CPU_Sim cpu = new CPU_Sim();
	private static final CPU_Sim io = new CPU_Sim(); // Reusing CPU_Sim as it is comparable
	private static final bufferBound cpu_queue = new bufferBound();
	private static final bufferBound io_queue = new bufferBound();
	private static int currentTime = 0;
	private static ArrayList<Process_Sim> processQueue = new ArrayList<Process_Sim>();
	private static ArrayList<Process_Sim> finishedQueue = new ArrayList<Process_Sim>();
	private int added = 0;
	private boolean hasRun = false;
	private int cpuWaitingTime = 0;
	private int ioWaitingTime = 0;
	public static boolean auto;
	public static boolean rr;

	public static void insert(Process_Sim process) {
		processQueue.add(process);
	}

	public void checkArrivals() {
        for(int i = 0; i < processQueue.size(); i++) {
			if(processQueue.get(i).arrivalTime == currentTime) {
				added++;
    			cpu_queue.push(processQueue.get(i));
				System.out.println(processQueue.get(i).name + ": Arrival --> CPU queue (Queue: " + cpu_queue.printArr() + "System time: " + currentTime + ")");
			}
        }
    }

	private void printInfo() {
		String cpuProcess = cpu.process != null ? cpu.process.name : "Idle";
		String ioProcess = io.process != null ? io.process.name : "Idle";
		String cpuQueue = cpu_queue.hasData() ? cpu_queue.printArr() : "Empty";
		String ioQueue = io_queue.hasData() ? io_queue.printArr() : "Empty";
		System.out.println("---------------------------");
		System.out.println("System time: " + currentTime);
		System.out.println("CPU: " + cpuProcess);
		System.out.println("IO: " + ioProcess);
		System.out.println("CPU queue: " + cpuQueue);
		System.out.println("IO queue: " + ioQueue);
		System.out.println("---------------------------");

		if (!auto){
			System.out.print("System time: " + currentTime + " [Press ENTER to continue]");
			Driver.in.nextLine();
			System.out.println();
		}
		System.out.println();
	}

	private void pause(){
		if (auto){
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				System.out.println("Scheduler has insomnia.");
				e.printStackTrace();
			}
			return;
		}
	}

	@Override
	public void run() {

		Thread cpuThread = new Thread(cpu);
		Thread ioThread = new Thread(io);
		cpuThread.start();
		ioThread.start();

		while (!hasRun | cpu_queue.hasData() || io_queue.hasData() || cpu.process != null || io.process != null || processQueue.size() < added) {
			checkArrivals();
			hasRun = true;

			if(cpu.process == null && cpu_queue.peek() != null) {
				cpu.process = cpu_queue.pop();
				System.out.println(cpu.process.name + ": CPU queue --> CPU ");
				printInfo();
			}
			if(io.process == null && io_queue.peek() != null) {
				io.process = io_queue.pop();
				System.out.println(io.process.name + ": IO queue --> IO ");
				printInfo();
			}

			try {
				if (cpu.process.burstTime <= 0) {
					if (cpu.process.nextBurst()) {
						if (io.process == null) {
							io.process = cpu.process;
							Process_Sim temp = cpu.process;
							cpu.process = null;
							System.out.println(temp.name + ": CPU --> IO ");
							printInfo();
						} else {
							io_queue.push(cpu.process);
							Process_Sim temp = cpu.process;
							cpu.process = null;
							System.out.println(temp.name + ": CPU --> IO queue ");
							printInfo();
						}
					} else {
						Process_Sim temp = cpu.process;
						cpu.process = null;
						System.out.println(temp.name + ": Process finished ");
						printInfo();
						temp.endTime = currentTime;
						finishedQueue.add(temp);
					}
					cpu.process = cpu_queue.pop();
					System.out.println(cpu.process.name + ": CPU queue --> CPU ");
					printInfo();
					cpu.quantumTime = 0;
				} else if (rr && cpu_queue.peek().priority == cpu.process.priority && cpu.quantumTime >= quantumTime) {
					cpu_queue.push(cpu.process);
					Process_Sim temp = cpu.process;
					cpu.process = null;
					System.out.println(temp.name + ": CPU --> CPU queue ");
					printInfo();
					cpu.process = cpu_queue.pop();
					cpu.quantumTime = 0;
					System.out.println(cpu.process.name + ": CPU queue --> CPU ");
					printInfo();
				}
			} catch (NullPointerException e) {}

			try {
				if (io.process.burstTime <= 0) {
					if (io.process.nextBurst()) {
						cpu_queue.push(io.process);
						Process_Sim temp = io.process;
						io.process = null;
						System.out.println(temp.name + ": IO --> CPU queue ");
						printInfo();
					} else {
						Process_Sim temp = io.process;
						io.process = null;
						System.out.println(temp.name + ": Process finished ");
						printInfo();
						temp.endTime = currentTime;
						finishedQueue.add(temp);
					}
					io.process = io_queue.pop();
					System.out.println(io.process.name + ": IO queue --> IO ");
					printInfo();
					io.quantumTime = 0;
				}
			} catch (NullPointerException e) {}

			try {
				if (cpu_queue.peek().priority < cpu.process.priority) {
					cpu_queue.push(cpu.process);
					Process_Sim temp = cpu.process;
					cpu.process = null;
					System.out.println(temp.name + ": CPU --> CPU queue");
					printInfo();
					cpu.process = cpu_queue.pop();
					System.out.println(cpu.process.name + ": CPU queue --> CPU ");
					printInfo();
					cpu.quantumTime = 0;
				}
			} catch (NullPointerException e) {}

			pause();
			cpuWaitingTime += cpu_queue.count();
			ioWaitingTime += io_queue.count();
			currentTime++;
			cpu.tick();
			io.tick();
		}
		System.out.println("\nDone!\n");
		System.out.println("Total time: " + (currentTime-1));
		System.out.println("CPU Utilization: " + ((cpu.usedTime-cpu.idleTime)/(double)cpu.usedTime));
		System.out.println("Throughput: " + finishedQueue.size()/(double)currentTime);
		System.out.println("CPU Waiting time: " + cpuWaitingTime);
		System.out.println("IO Waiting time: " + ioWaitingTime);
		double turnAroundTime = 0.0;
		for (Process_Sim x : finishedQueue) {
			turnAroundTime += x.endTime - x.arrivalTime;
		}
		turnAroundTime = turnAroundTime/finishedQueue.size();
		System.out.println("Average turnaround time: " + turnAroundTime);
		cpu.stopProcess();
		io.stopProcess();
		try {
			cpuThread.join();
			ioThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
