//if we want to start this project in java due to its native thread controls
import java.lang.Thread;
import java.time.*;
public class main extends Thread{
    
    //runs the threads anything in here will deal with threads
    @Override
    public void run() {

        System.out.println("Im using thread #" + this.threadId() + ". Isn't that neats?");
    }
    public static void main(String[] args) {
        //sets up the threads in orders for the em to start
        main thread = new main();
        thread.start();
    }
}


    
