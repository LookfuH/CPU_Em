public class CPU_Sim {

    // public ArrayList<Process_Sim> queue = new ArrayList<Process_Sim>();

    // public void addToQueue(Process_Sim p) {
    //     queue.add(p);
    //     System.out.println("Process " + p.name + " was added to the queue");
    // }

    private boolean run;

    public void stopProcess() {
        run = false;
    }

    public Process_Sim runProcesses(Process_Sim process, int runTime) {
        run = true;

        int time = 0;

        System.out.println("CPU recieved: " + process.name);;

        try {
            while (time < runTime && run) {
                Thread.sleep(500); // 1/2 sec unit time
                time++;
            }
        } catch (InterruptedException e) {
                System.out.println("CPU must have insomnia :(");
                e.printStackTrace();
            }

        process.burstTime -= time;

        System.out.println("CPU finished: " + process.name);

        return process;
    }
}
