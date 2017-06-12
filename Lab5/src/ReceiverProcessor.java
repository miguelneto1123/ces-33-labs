public class ReceiverProcessor extends Processor {
    public ReceiverProcessor(int id) {
        super(id);
    }

    @Override
    public void update(Manager m){
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
