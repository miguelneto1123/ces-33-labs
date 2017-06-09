package Processadores;

import java.util.Random;
import java.util.Vector;

import util.Processo;

public class GerenciadorHReceptor extends Gerenciador {
	public GerenciadorHReceptor (int numProc) {
		super (numProc);
		proc_ = new Vector<Processador>();
		for (int i = 0; i < nProc_; i++) {
			proc_.add(new ProcessadorReceptor(i));
		}
	}

	@Override
	public boolean tryReceiveProcess (int numProcessador) {
		Random rand = new Random();
		for (int i = 1; i <= RETRY; i++) {
			sentMessages[numProcessador]++;
			int r = rand.nextInt(nProc_);
			
			while (r == numProcessador) r = rand.nextInt(nProc_);
			receivedMessages[r]++;
			
			if (getTempoDeProcessamentoTotal() == 0) return false;
			
			float coef = proc_.elementAt(r).getTempoProcessamento() / getTempoDeProcessamentoTotal();
			if (coef > Processador.LIMIT_MAX && proc_.elementAt(r).getNumProcessos() > 1) {
				Processo p = proc_.elementAt(r).receiveLastProcess();
				// Mudou a cpu
				printCPUChange(r, numProcessador);
				p.cpu_ = numProcessador;
				proc_.elementAt(numProcessador).addProcess(p);
				return true;
			}
		}
		
		return false;
	}
}
