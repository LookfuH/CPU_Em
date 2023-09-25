// Way 1
// Creating thread By Extending To Thread class
import java.lang.Thread;
class myThread extends Thread {

	// Method 1
	// Run() method for our thread
    @Override
	public void run()
	{

		// Print statement
		System.out.println(
			"Thread is running created by extending to parent Thread class");
	}

	// Method 2
	// Main driver method
	public static void main(String[] args)
	{

		// Creating object of our thread class inside main()
		// method
		myThread myThread = new myThread();
	
		// Starting the thread
		myThread.start();
	}
}
