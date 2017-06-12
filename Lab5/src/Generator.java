import java.util.Random;

public class Generator {
    private Manager manager;

    /*
    - tmt: average process execution time
    - amount: quantity of tasks generated each tmt seconds
    - iterationsPerSec: iterations per second
     */
    private int amount, tmt, iterationsPerSec;

    public Generator(Manager m, int amount, int tmt){
        this.manager = m;
        this.amount = amount;
        this.tmt = tmt;
        this.iterationsPerSec = 605411;
    }

    public boolean generateProcess (int iteration, int taskCount){
        Random rand = new Random();
        float elapsedTime = (float) iteration / iterationsPerSec;
        float expectedTaskCount = amount * elapsedTime / tmt;
        if (expectedTaskCount > taskCount)
            return rand.nextBoolean();
        return false;
    }

    public boolean run(){
        int taskCount = 0;
        Random rand = new Random();

        // Simulate for the creation of 9 processes
        for (int i = 0; taskCount < 9; i++){
            //System.out.println("Iteration " + i);
            //manager.printSimpleStatus();
            //manager.printStatus();

            if (generateProcess(i, taskCount)){
                taskCount++;
                manager.addProcess(new Process(rand.nextInt(manager.quantProcessors()),
                        i,
                        rand.nextInt(tmt*2)* iterationsPerSec,
                        (rand.nextInt(100)/100.0f)/manager.quantProcessors()));
                // These generated processes last on average for tmt seconds,
                // and their CPU load is also random
            }

            manager.update();
            if (manager.cpuLoad() > 1.0f){
                System.out.println("Exceeded load");
                return false;
            }
        }
        /*
        There's no need to wait for all processes to end, as there will be no more
        cpu changes
         */
        return true;
    }
}
