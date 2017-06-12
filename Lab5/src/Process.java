public class Process {
    public enum proc_state {WAIT, PROCESSING, FINISHED}

    private int cpu;
    private int timeCreated;
    private int neededTime;
    private int elapsedTime;
    private float cpuLoad;
    private proc_state state;

    public Process(int creatorCPU, int time, int procTime, float cpuLoad){
        this.cpu = creatorCPU;
        this.timeCreated = time;
        this.neededTime = procTime;
        this.elapsedTime = 0;
        this.cpuLoad = cpuLoad;
        this.state = proc_state.WAIT;
    }

    public void update(){
        if (state == proc_state.WAIT) state = proc_state.PROCESSING;

        if (state == proc_state.PROCESSING) elapsedTime++;

        if (elapsedTime == neededTime) state = proc_state.FINISHED;
    }

    public boolean finished(){
        return state == proc_state.FINISHED;
    }

    public float cpuLoad() {
        return cpuLoad;
    }

    public int cpu(){
        return cpu;
    }

    public void changeCpu(int cpu) {
        this.cpu = cpu;
    }

    public void printStatus(){
        System.out.println("Current CPU: " + cpu);
        System.out.format("CPU load: %.4f\n", cpuLoad);
        System.out.println("Time of creation: " + timeCreated);
        System.out.println("Elapsed time/Time needed: " +
                            elapsedTime + "/" + neededTime);
        System.out.print("State: ");
        switch (state){
            case WAIT:
                System.out.println("Waiting"); break;
            case PROCESSING:
                System.out.println("Processing"); break;
            case FINISHED:
                System.out.println("Finished"); break;
        }
    }
}
