import java.util.Random;
import java.util.Vector;

public class Manager {
    final protected static int RETRY = 2;
    protected int numProc, cpuChanges;

    Vector<Processor> processors;
    public int[] sentMessages, receivedMessages;

    public Manager(int numProc) {
        this.numProc = numProc;
        this.cpuChanges = 0;
        sentMessages = new int[numProc];
        receivedMessages = new int[numProc];
        Processor.setLimits(1.0f / numProc, 0.2f / numProc);
    }

    public float cpuLoad(){
        float sum = 0;

        for (Processor p : processors)
            sum += p.totalCpuLoad();

        return sum;
    }



    public int quantProcessors(){
        return processors.size();
    }

    public void addProcess(Process p) {
        processors.elementAt(p.cpu()).addProcess(p);
    }

    public void update() {
        for (Processor p : processors)
            p.update(this);
    }

    public int cpuChanges(){
        return cpuChanges;
    }

    /*
     * The two full implementations are here to avoid code duplication. HybridManager
     * inherits both and do nothing, while EmitterManager and ReceiverManager make
     * dummy overrides of these methods.
     */
    public boolean tryPassProcess(Process process) {
        Random rand = new Random();
        for (int i = 0; i < RETRY; i++){
            sentMessages[process.cpu()]++;
            int r = rand.nextInt(numProc);

            while (r == process.cpu()) r = rand.nextInt(numProc);
            receivedMessages[r]++;

            if (processors.get(r).totalCpuLoad() < Processor.MINLIM){
                //printCPUChange(process.cpu(), r);
                cpuChanges++;
                process.changeCpu(r);
                processors.get(r).addProcess(process);
                return true;
            }
        }
        return false;
    }

    public void tryReceiveProcess(int processorId) {
        Random rand = new Random();
        for (int i = 0; i < RETRY; i++) {
            sentMessages[processorId]++;
            int r = rand.nextInt(numProc);

            while (r == processorId) r = rand.nextInt(numProc);
            receivedMessages[r]++;

            if (/*processors.get(r).totalCpuLoad() > Processor.MAXLIM &&
                    */processors.get(r).quantProcesses() > 1) {
                Process p = processors.get(r).retrieveLastProcess();
                // Mudou a CPU
                //printCPUChange(r, processorId);
                p.changeCpu(processorId);
                processors.get(processorId).addProcess(p);
            }
        }
    }

    /*
    * These below are debug methods, just to be sure of what was happening
    */

    public void printStatus() {
        System.out.println("===============================================");
        for (int i = 0; i < numProc; i++) {
            System.out.println("Processor " + (i + 1));
            processors.get(i).printStatus();
            System.out.println("");
        }
        System.out.println("===============================================");
    }

    public void printSimpleStatus () {
        System.out.println("===============================================");
        System.out.format("Total CPU load: %.4f\n", cpuLoad());
        int numProc = 0;
        for (int i = 0; i < numProc; i++) {
            numProc += processors.get(i).quantProcesses();
        }
        System.out.println("Amount of processes in PROCESSING state: " + numProc);
        System.out.println("===============================================");
    }

    public void printCPUChange(int antCPU, int novaCPU) {
        System.out.println("CPU change:");
        System.out.println("From " + antCPU + " to " + novaCPU);
        System.out.format("CPU load of %d: %.4f\n", antCPU,
                processors.elementAt(antCPU).totalCpuLoad());
        System.out.format("CPU load of %d: %.4f\n", novaCPU,
                processors.elementAt(novaCPU).totalCpuLoad());
    }
}