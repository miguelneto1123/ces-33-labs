import java.util.LinkedList;

public class Processor {
    protected int id;
    protected LinkedList<Process> processes;
    protected static float MAXLIM, MINLIM;

    public Processor(int id){
        this.id = id;
        this.processes = new LinkedList<Process>();
    }

    public float totalCpuLoad(){
        float total = 0;

        for (Process p : processes){
            total += p.cpuLoad();
        }

        return total;
    }

    public void addProcess(Process p){
        processes.add(p);
    }

    public void update(Manager m){
        if (processes.isEmpty()) return;

        if (processes.peek().finished()) processes.poll();

        if (processes.isEmpty()) return;

        processes.peek().update();
    }

    public int quantProcesses(){
        return processes.size();
    }

    public static void setLimits(float MAXLIM, float MINLIM) {
        Processor.MAXLIM = MAXLIM;
        Processor.MINLIM = MINLIM;
    }

    public Process retrieveLastProcess(){
        return null;
    }

    public void printStatus(){
        for (Process p : processes) {
            p.printStatus();
        }
    }
}
