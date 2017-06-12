import java.util.Vector;

public class HybridManager extends Manager{
    public HybridManager(int nproc) {
        super(nproc);
        processors = new Vector<>();
        for (int i = 0; i < nproc; i++)
            processors.add(new HybridProcessor(i));
    }
}
