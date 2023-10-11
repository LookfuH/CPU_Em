//if we want to start this project in java due to its native thread controls
import java.lang.Thread;
public class main {

    public static void main(String[] args) {

    Scheduler scheduler = new Scheduler();

    Thread thread = new Thread(scheduler);
    thread.start();

    Scheduler.insert(new Process_Sim("P1", 2, 6));
    Scheduler.insert(new Process_Sim("P2", 9, 6));
    Scheduler.insert(new Process_Sim("P3", 7, 6));

    try {
        thread.join();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
}
