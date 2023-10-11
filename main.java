//if we want to start this project in java due to its native thread controls
import java.lang.Thread;
public class main extends Thread{

    //runs the threads anything in here will deal with threads
    @Override
    public void run() {

        System.out.println("Im using thread #" + this.threadId() + ". Isn't that neats?");
    }
    public static void main(String[] args) {
        //sets up the threads in orders for the em to start
       // main thread = new main();
       // thread.start();

       //intilized threads
	bufferBound<Process_Sim> res = new bufferBound<Process_Sim>();
	Thread t1 = new Thread(new producer(res));
	Thread t2 = new Thread(new Scheduler(res));
	Thread t3 = new Thread(new Scheduler(res));
	t1.start();

	t2.start();
	t3.start();

    }
}
