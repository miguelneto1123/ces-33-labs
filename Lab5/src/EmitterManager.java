import java.util.Vector;

public class EmitterManager extends Manager{
    public EmitterManager(int nproc) {
        super(nproc);
        processors = new Vector<>();
        for (int i = 0; i < nproc; i++)
            processors.add(new EmitterProcessor(i));
    }

    /*
     * This dummy implementation is to avoid code duplicates in HybridManager class
     */
    @Override
    public void tryReceiveProcess(int processId){ }
}
