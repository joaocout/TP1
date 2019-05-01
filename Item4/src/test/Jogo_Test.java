package test;


import org.junit.*;
import locadora.*;

public class Jogo_Test {
	
	@Test
	public void testAddQtd() {
		
		Plataforma ps4 = new Plataforma("Playstation 4", 0.8f);
		Jogo gt = new Jogo("Gran Turismo", 1.75f, 3, ps4);
		
		gt.addQtd();
		Assert.assertEquals("A quantidade nao foi incrementada conforme o esperado",
				4, gt.getQtd());
		
	}
	
	@Test
	public void testSubQtd() {
		
		Plataforma ps4 = new Plataforma("Playstation 4", 0.8f);
		Jogo gt = new Jogo("Gran Turismo", 1.75f, 3, ps4);
		
		gt.subQtd();
		Assert.assertEquals("A quantidade nao foi decrementada conforme o esperado",
				2, gt.getQtd());
		
	}
	
}
