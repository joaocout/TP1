package test;


import org.junit.*;

import exception.AlugarEx;
import locadora.*;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;

public class Cliente_Test {
	
	@Test
	public void testDivida() throws AlugarEx {
		
		Cliente joao = new Cliente("João", "1234566", "999090080", "joao@unb.br", "999998888");
		
		Plataforma ps4 = new Plataforma("Playstation 4", 0.5f);
		
		Jogo rl = new Jogo("Rocket League", 1.00f, 12, ps4);
		ps4.addJogo(rl);
		
		
		Locacao loc = new Locacao(rl, 28);
		joao.addLocacao(loc);
	
		loc.alugar();
		
		Assert.assertEquals(14, joao.Divida(), 0.001f);
	}
	
	@Test
	public void testAddLocacao() {
		
		Cliente joao = new Cliente("João", "1234566", "999090080", "joao@unb.br", "999998888");
		
		Plataforma ps4 = new Plataforma("Playstation 4", 1.2f);
		
		Jogo rl = new Jogo("Rocket League", 5.00f, 12, ps4);
		ps4.addJogo(rl);
		
		Locacao loc = new Locacao(rl, 28);
		joao.addLocacao(loc);
		
	Locacao atual = joao.getLocacoes().get(0);
	Assert.assertEquals("O objeto contido na lista de locacoes é diferente do que se esperava",loc, atual);
	}
	
}