public class Process_Sim {

    public String name;
    public int priority;
    public int bufferTime;

    public double arrivalTime;
    public double startTime;

    public Process_Sim(String n, int p) {
        name = n;
        priority = p;
    }

    public void setArrival() {
        arrivalTime = System.currentTimeMillis();
    }

    public void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    public void printInfo() {
        double finishTime = System.currentTimeMillis();
        System.out.println(name + ": process time: " + (finishTime - startTime));
        System.out.println(name + ": turn-around time: " + (arrivalTime - startTime));
    }
}
