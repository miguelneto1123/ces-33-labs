package Geradores;

import org.junit.Test;

import Processadores.*;

public class TestGerador {

	@Test
	public void test() {
		int nproc = 4;
		GerenciadorHEmissor gh = new GerenciadorHEmissor(nproc);
		Gerador g = new Gerador(gh, 30, 3);
		
		g.run();
		System.out.println("Quantidade de mensagens enviadas por processador:");
		for (int i = 0; i < nproc; i++){
			System.out.println(i + ": " + gh.sentMessages[i]);
		}
		
		System.out.println("\nQuantidade de mensagens recebidas por processador:");
		for (int i = 0; i < nproc; i++){
			System.out.println(i + ": " + gh.receivedMessages[i]);
		}
	}

}
