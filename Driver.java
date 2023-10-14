//if we want to start this project in java due to its native thread controls
import java.lang.Thread;
public class Driver {

    public static void main(String[] args) {

    Scheduler scheduler = new Scheduler();

    Thread thread = new Thread(scheduler);

    Scheduler.insert(new Process_Sim("P1", 5, new int[] {8, 3, 5, 7}));
    Scheduler.insert(new Process_Sim("P2", 2, new int[] {7, 1, 2, 6}));
    Scheduler.insert(new Process_Sim("P3", 5, new int[] {13, 5, 5, 8}));

    thread.start();

    try {
        thread.join();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
}
