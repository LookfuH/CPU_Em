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

        try {
            while (time < runTime && run) {
                Thread.sleep(60);
                time++;
            }
        } catch (InterruptedException e) {
                System.out.println("I must have insomnia :(");
                e.printStackTrace();
            }

        process.bufferTime -= time;
        return process;
    }
}
