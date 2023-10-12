public class CPU_Sim {

    private boolean run;

    public void stopProcess() {
        run = false;
    }

    public Process_Sim runProcesses(Process_Sim process, int runTime) { // TODO: CPU should wait for tick from Scheduler before continuing
        run = true;

        int time = 0;

        System.out.println("CPU recieved: " + process.name);;

        try {
            while (time < runTime && run) {
                Thread.sleep(500); // 1/2 sec unit time
                time++;
            }
        } catch (InterruptedException e) {
                System.err.println("CPU has insomnia :(");
                e.printStackTrace();
            }

        process.burstTime -= time;

        System.out.println("CPU finished: " + process.name);

        return process;
    }
}
