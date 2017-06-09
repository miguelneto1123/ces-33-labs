package Processadores;

import java.util.Random;

import org.junit.Test;

import util.Processo;

public class TestGHE {
	GerenciadorHEmissor ghe_ = new GerenciadorHEmissor(3);

	@Test
	public void test() {
		int NUMITER = 100;
		Random rand = new Random();
		
		for (int i = 0; i < NUMITER; i++) {
			System.out.println("Iteração " + i);
			//ghe_.printStatus();
			ghe_.printSimpleStatus();
			
			if (i%2 == 0)
				ghe_.addProcess(new Processo(rand.nextInt(ghe_.nProc_),
											 i,
											 rand.nextInt(10) + 1));
			
			ghe_.update();
		}
		
		System.out.println("Quantidade de mensagens enviadas por processador:");
		for (int i = 0; i < 3; i++){
			System.out.println(i + ": " + ghe_.sentMessages[i]);
		}
		
		System.out.println("\nQuantidade de mensagens recebidas por processador:");
		for (int i = 0; i < 3; i++){
			System.out.println(i + ": " + ghe_.receivedMessages[i]);
		}
	}

}
