package Processadores;

import java.util.LinkedList;

import util.Processo;

public class Processador {
	static float LIMIT_MAX, LIMIT_MIN; // M�trica ser� de tempo de processamento necess�rio
	
	int nProcessador;	
	LinkedList<Processo> processos;
	
	public Processador (int numProcessador) {
		processos = new LinkedList<Processo>();
		nProcessador = numProcessador;
	}
	
	public int getTempoProcessamento () {
		int sum = 0;
		
		for (Processo processo : processos) {
			sum += processo.tempoNecessario_ - processo.tempoDeProcessamento_;
		}
		
		return sum;
	}
	
	public void addProcess (Processo p) {
		processos.add(p);
	}
	
	public void update (Gerenciador g) {
		if (processos.isEmpty()) return;
		
		if (processos.peek().finished()) processos.poll();
		
		if (processos.isEmpty()) return;
		processos.peek().update();
	}
	
	public void printStatus () {
		for (Processo processo : processos) {
			processo.printStatus();
		}
	}
	
	public int getNumProcessos() {
		return processos.size();
	}

	public Processo receiveLastProcess() {
		// TODO Auto-generated method stub
		return null;
	}
}
