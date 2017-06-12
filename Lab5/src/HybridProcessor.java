public class HybridProcessor extends Processor{
    public HybridProcessor(int id) {
        super(id);
    }

    @Override
    public void update(Manager m){
        if (this.totalCpuLoad() > MAXLIM)
            if (processes.size() > 1)
                if (m.tryPassProcess(processes.get(processes.size()-1)))
                    processes.remove(processes.get(processes.size()-1));

        if (this.totalCpuLoad() < MINLIM){
            m.tryReceiveProcess(id);
        }

        super.update(m);
    }

    @Override
    public Process retrieveLastProcess(){
        if (processes.size() == 0) return null;
        Process p = processes.get(processes.size() - 1);
        processes.remove(processes.size() - 1);

        return p;
    }

}
