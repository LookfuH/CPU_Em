public class CPU_Sim implements Runnable {

    Process_Sim process;
    int quantumTime = 0;

    public void stopProcess() {
        process = null;
        try{
            System.out.println("CPU quit: " + process.name);
            Scheduler.insert(process); // Send back to scheduler
        } catch (NullPointerException e) {}
    }

    public void setProcess(Process_Sim process) {
        stopProcess();
        this.process = process;
        tick();
    }

    public synchronized void tick() {
        notify();
    }

    public synchronized void run() { // TODO: CPU should wait for tick from Scheduler before continuing
        while (true) {
            try {
                wait();
                try {
                    process.burstTime--;
                    quantumTime++;
                } catch (NullPointerException e) {}
            } catch (InterruptedException e) {
                    System.err.println("CPU broke :(");
                    e.printStackTrace();
            }
        }
    }
}
