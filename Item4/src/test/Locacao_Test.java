package test;


import locadora.*;

import java.time.LocalDate;
import java.time.LocalTime;

//import java.time.LocalDate;

import org.junit.*;

import exception.AlugarEx;
import exception.DevolverEx;
import exception.PrecoEx;

//import java.time.temporal.ChronoUnit;

public class Locacao_Test {
	
	@Test
	public void testPrecoFinal() throws PrecoEx, AlugarEx {
		
		Plataforma ps4 = new Plataforma("Playstation 4", 0.5f); 
		
		Jogo rl = new Jogo ("Rocket League", 1.50f, 5, ps4);
		ps4.addJogo(rl);
		
		Locacao loc = new Locacao(rl, 28);
		loc.alugar();
		
		Assert.assertEquals(21, loc.PrecoFinal(), 0.001f);
	}
	
	@Test
	public void testAlugar() throws AlugarEx {
		Plataforma ps4 = new Plataforma("Playstation 4", 0.5f); 
		
		Jogo rl = new Jogo ("Rocket League", 1.50f, 5, ps4);
		ps4.addJogo(rl);
		
		Locacao loc = new Locacao(rl, 28);
		loc.alugar();
		Assert.assertEquals("Um ou mais atributos do obj. nao correspondem com o esperado",
				LocalDate.now().toString(),loc.getDataAluguel());
	}
	
	@Test
	public void testDevolver() throws AlugarEx, DevolverEx {
		Plataforma ps4 = new Plataforma("Playstation 4", 0.5f); 
		
		Jogo rl = new Jogo ("Rocket League", 1.50f, 5, ps4);
		ps4.addJogo(rl);
		
		Locacao loc = new Locacao(rl, 28);
		loc.alugar();
		loc.devolver();
		Assert.assertEquals("Um ou mais atributos do obj. nao correspondem com o esperado",
				LocalTime.now().withNano(0).withSecond(0).toString(), loc.getHoraDevolucao());
	}
	
}
