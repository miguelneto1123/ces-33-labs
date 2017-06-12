import java.util.Vector;

public class ReceiverManager extends Manager {
    public ReceiverManager(int nproc) {
        super(nproc);
        processors = new Vector<>();
        for (int i = 0; i < nproc; i++)
            processors.add(new ReceiverProcessor(i));
    }

    /*
     * This dummy implementation is to avoid code duplicates in HybridManager class
     */
    @Override
    public boolean tryPassProcess(Process process){
        return false;
    }
}
