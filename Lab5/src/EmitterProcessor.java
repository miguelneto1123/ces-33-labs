public class EmitterProcessor extends Processor {
    public EmitterProcessor(int id) {
        super(id);
    }

    @Override
    public void update(Manager m) {
        if (this.totalCpuLoad() > MAXLIM)
            if (processes.size() > 1)
                if (m.tryPassProcess(processes.get(processes.size()-1)))
                    processes.remove(processes.get(processes.size()-1));

        super.update(m);
    }
}
