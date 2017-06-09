package Processadores;

import java.util.Vector;

import util.Processo;

public class Gerenciador {
	final int RETRY = 2;
	public int nProc_ = 3;
	
	Vector<Processador> proc_;
	public int[] sentMessages, receivedMessages;
	
	public Gerenciador(int nProc){
		nProc_ = nProc;
		sentMessages = new int[nProc];
		receivedMessages = new int[nProc];
		Processador.LIMIT_MAX = nProc == 1 ? 1.0f : 1.05f / nProc;
		Processador.LIMIT_MIN = nProc == 1 ? .0f : .9f / nProc;
	}
	
	int getTempoDeProcessamentoTotal () {
		int sum = 0;
		
		for (Processador processador : proc_) {
			sum += processador.getTempoProcessamento();
		}
		
		return sum;
	}

	public boolean tryPassProcess(Processo p) {
		return false;
	}
	
	public void printStatus() {
		System.out.println("===============================================");
		for (int i = 0; i < nProc_; i++) {
			System.out.println("Processador " + (i + 1));
			proc_.get(i).printStatus();
			System.out.println("");
		}
		System.out.println("===============================================");
	}
	
	public void printSimpleStatus () {
		System.out.println("===============================================");
		System.out.println("Tempo de processamento total = " + getTempoDeProcessamentoTotal());
		int numProc = 0;
		for (int i = 0; i < nProc_; i++) {
			numProc += proc_.get(i).getNumProcessos();
		}
		System.out.println("N�mero total de processos = " + numProc);
		System.out.println("===============================================");
	}
	
	void printCPUChange(int antCPU, int novaCPU) {
		System.out.println("Mudan�a de CPU:");
		System.out.println(antCPU + " para " + novaCPU);
		System.out.println("Tempo de Processamento CPU " + antCPU + " = "
							+ proc_.elementAt(antCPU).getTempoProcessamento());
		System.out.println("Tempo de Processamento CPU " + novaCPU + " = "
				+ proc_.elementAt(novaCPU).getTempoProcessamento());
	}
	
	public void addProcess (Processo p) {
		proc_.elementAt(p.cpu_).addProcess(p);
	}
	
	public void update() {
		for (int i = 0; i < nProc_; i++)
			proc_.elementAt(i).update(this);
	}

	public boolean tryReceiveProcess(int numProcessador) {
		// TODO Auto-generated method stub
		return false;
	}
}
