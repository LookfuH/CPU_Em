//if we want to start this project in java due to its native thread controls
import java.lang.Thread;
public class Driver {

    public static void main(String[] args) {

    Scheduler scheduler = new Scheduler();

    Thread thread = new Thread(scheduler);
    thread.start();


    scheduler.insert(new Process_Sim("P1", 2, new int[] {2, 3, 5, 7}));
    scheduler.insert(new Process_Sim("P2", 5, new int[] {7, 1, 2, 6}));
    scheduler.insert(new Process_Sim("P3", 5, new int[] {13, 5, 5, 8}));

    try {
        thread.join();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
}
