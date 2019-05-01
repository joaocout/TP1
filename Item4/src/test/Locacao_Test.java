package test;


import locadora.*;

import java.time.LocalDate;

import org.junit.*;

import exception.PrecoEx;

import java.time.temporal.ChronoUnit;

public class Locacao_Test {
	
	@Test
	public void testPrecoFinal() throws PrecoEx {
		
		Plataforma ps4 = new Plataforma("Playstation 4", 0.5f); 
		
		Jogo rl = new Jogo ("Rocket League", 3.00f, 5, ps4);
		ps4.addJogo(rl);
		
		Locacao loc = new Locacao(rl);
		loc.setDataAluguel("2019-04-28");
		loc.setDataDevolucao(LocalDate.now().toString());
		
		long dias = ChronoUnit.DAYS.between(LocalDate.of(2019, 04 ,28), LocalDate.now());
		double esperado = dias*ps4.getCoeficiente()*rl.getPrecoBase();
		
		Assert.assertEquals(esperado, loc.PrecoFinal(), 0.0001f);
	}
	
}
