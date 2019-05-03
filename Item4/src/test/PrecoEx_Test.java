package test;

import locadora.*;
import exception.*;
import org.junit.*;

public class PrecoEx_Test {
	
	@Test(expected = exception.PrecoEx.class)
	public void testPrecoEx() throws PrecoEx {
		
		Plataforma p = new Plataforma("Playstation", 1.4f);
		Jogo j = new Jogo("Crash", 2.00f, 0, p);
		p.addJogo(j);
		Locacao l = new Locacao(j, 5);
		l.PrecoFinal();
	}
	
}
