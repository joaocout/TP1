package test;

import org.junit.*;
import exception.*;
import locadora.*;

public class DevolverEx_Test {
	
	@Test(expected = exception.DevolverEx.class)
	public void testAlugarEx() throws DevolverEx {
		Plataforma p = new Plataforma("Playstation", 1.4f);
		Jogo j = new Jogo("Crash", 2.00f, 0, p);
		p.addJogo(j);
		Locacao l = new Locacao(j, 5);
		l.devolver();
	}
}
