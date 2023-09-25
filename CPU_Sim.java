import java.util.ArrayList;

public class CPU_Sim {

    public ArrayList<Process_Sim> queue = new ArrayList<Process_Sim>();

    public void addToQueue(Process_Sim p) {
        queue.add(p);
        System.out.println("Process " + p.name + " was added to the queue");
    }

    public void runProcesses() {

    }

}
