//if we want to start this project in java due to its native thread controls
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Driver {

    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {

    String result;

    System.out.print("Run in auto? [Y/n]: ");
    result = in.nextLine();

    if (result.equalsIgnoreCase("") | result.equalsIgnoreCase("y") | result.equalsIgnoreCase("yes")) {
        Scheduler.auto = true;
    } else if (result.equalsIgnoreCase("n") | result.equalsIgnoreCase("no")){
        Scheduler.auto = false;
    } else {
        System.out.println("Invalid");
        System.exit(1);
    }

    System.out.print("Use round-robin? [Y/n]: ");
    result = in.nextLine();

    if (result.equalsIgnoreCase("") | result.equalsIgnoreCase("y") | result.equalsIgnoreCase("yes")) {
        Scheduler.rr = true;
    } else if (result.equalsIgnoreCase("n") | result.equalsIgnoreCase("no")){
        Scheduler.rr = false;
    } else {
        System.out.println("Invalid");
        System.exit(1);
    }

    File[] dir = new File("simulations/").listFiles();
    for(int i = 0; i < dir.length; i++){
        System.out.println((i+1) + ". " + dir[i].getName());
    }

    System.out.print("Choose a simulation: ");
    int res = 0;
    try {
        res = Integer.parseInt(in.nextLine());
    } catch(Exception e) {
        System.out.println("Invalid");
        System.exit(1);
    }

    Scanner file = null;
    try {
        file = new Scanner(dir[res-1]);
    } catch (FileNotFoundException e) {System.out.println("Could not find file."); e.printStackTrace(); System.exit(1);
    } catch (IndexOutOfBoundsException e) {System.out.println("Invalid"); System.exit(1);}

    while(file.hasNextLine()){
        String[] line = file.nextLine().split(" ");
        String name = line[0];
        int arrivalTime = Integer.parseInt(line[1]);
        int priority = Integer.parseInt(line[2]);
        List<Integer> bursts = new ArrayList<Integer>();
        for(int i = 3; i < line.length; i++) {
            bursts.add(Integer.parseInt(line[i]));
        }
        Scheduler.insert(new Process_Sim(name, arrivalTime, priority, bursts.toArray(new Integer[bursts.size()])));
    }

    Scheduler scheduler = new Scheduler();
    Thread thread = new Thread(scheduler);

    // Thread thread = new Thread(scheduler);

    // Scheduler.insert(new Process_Sim("P1", 0,  5, new Integer[] {8, 3, 5, 12}));
    // Scheduler.insert(new Process_Sim("P2", 0,  2, new Integer[] {7, 1, 2, 7}));
    // Scheduler.insert(new Process_Sim("P3", 13, 5, new Integer[] {13, 5, 5, 9}));

    thread.start();

    try {
        thread.join();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
}
