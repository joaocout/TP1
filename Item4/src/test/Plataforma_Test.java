package test;


import org.junit.*;
import locadora.Plataforma;
import locadora.Jogo;

public class Plataforma_Test {

	@Test
	public void testAddJogo() {
		Plataforma ps4 = new Plataforma("Playstation 4", 0.95f);
		Jogo cod = new Jogo("Call Of Duty", 1.50f, 7,  ps4);
		ps4.addJogo(cod);
		Assert.assertEquals("O objeto contido na lista não corresponde com o esperado",
				cod, ps4.getJogos().get(0));
	}
	
	@Test
	public void testRemoveJogo() {
		Plataforma ps4 = new Plataforma("Playstation 4", 0.95f);
		Jogo cod = new Jogo("Call Of Duty", 1.50f, 7,  ps4);
		ps4.addJogo(cod);
		ps4.removeJogo("call of duty");
		Assert.assertEquals("O elemento da lista nao foi removido de forma adequada",
				0, ps4.getJogos().size());
	}
	
}
