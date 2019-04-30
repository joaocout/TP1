package test;


import org.junit.*;
import locadora.*;


public class Cliente_Test {
	
	@Test
	public void testDivida() {
		
		Cliente joao = new Cliente("João", "1234566", "999090080", "joao@unb.br", "999998888");
		
		Plataforma ps4 = new Plataforma("Playstation 4", 1.2f);
		
		Jogo rl = new Jogo("Rocket League", 5.00f, 12, ps4);
		ps4.addJogo(rl);
		
		
		Locacao loc = new Locacao(rl);
		joao.addLocacao(loc);
		loc.setDataAluguel("2018-04-30");
		loc.setDataDevolucao("2018-05-02");
		double atual = joao.Divida();
		Assert.assertEquals(12f, atual, 0.0001f);
	}
	
	@Test
	public void testAddLocacao() {
		
		Cliente joao = new Cliente("João", "1234566", "999090080", "joao@unb.br", "999998888");
		
		Plataforma ps4 = new Plataforma("Playstation 4", 1.2f);
		
		Jogo rl = new Jogo("Rocket League", 5.00f, 12, ps4);
		ps4.addJogo(rl);
		Locacao loc = new Locacao(rl);
		joao.addLocacao(loc);
		Locacao atual = joao.getLocacoes().get(0);
		Assert.assertEquals("O objeto que está na lista de locacoes é diferente do esperado",loc, atual);
	}
	
}