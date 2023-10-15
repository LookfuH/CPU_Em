public class CPU_Sim implements Runnable {

    Process_Sim process;
    int quantumTime = 0;
    int usedTime = 0;
    int idleTime = 0;
    private boolean run = true;

    public synchronized void stopProcess() {
        run = false;
        notify();
    }

    public synchronized void tick() {
        notify();
    }

    public synchronized void run() {
        while (run) {
            try {
                wait();
                try {
                    usedTime++;
                    process.burstTime--;
                    quantumTime++;
                } catch (NullPointerException e) {idleTime++;}
            } catch (InterruptedException e) {
                    System.err.println("CPU broke :(");
                    e.printStackTrace();
            }

        }
    }
}
