public class Process_Sim {

    public String name;
    public int priority;
    public int[] cpuTime;
    public int burstTime;
    public int index = 0;

    public int arrivalTime;
    public int startTime;
    public int endTime;

    public Process_Sim(String name, int arrivalTime, int priority, int[] cpuTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.cpuTime = cpuTime;
        nextBurst();
    }

    public boolean nextBurst() { // returns true if there is another burst after this one, false if there is not
        try {
            burstTime = cpuTime[index];
            index++;
            return true;
        } catch (IndexOutOfBoundsException e) {
            burstTime = 0;
            return false;
        }
    }

    public void printInfo() {
        double finishTime = System.currentTimeMillis();
        System.out.println(name + ": process time: " + (finishTime - startTime));
        System.out.println(name + ": turn-around time: " + (arrivalTime - startTime));
    }
}
