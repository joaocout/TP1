package test;


import org.junit.*;
import locadora.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Cliente_Test {
	
	@Test
	public void testDivida() {
		
		Cliente joao = new Cliente("João", "1234566", "999090080", "joao@unb.br", "999998888");
		
		Plataforma ps4 = new Plataforma("Playstation 4", 1.2f);
		
		Jogo rl = new Jogo("Rocket League", 5.00f, 12, ps4);
		ps4.addJogo(rl);
		
		
		Locacao loc = new Locacao(rl);
		joao.addLocacao(loc);
		loc.setDataAluguel("2019-04-28");
		loc.setDataDevolucao(LocalDate.now().toString());
		long dias = ChronoUnit.DAYS.between(LocalDate.of(2019,04,28), LocalDate.now());
		double atual = joao.Divida();
		double esperado = dias * ps4.getCoeficiente() * rl.getPrecoBase();
		Assert.assertEquals(esperado, atual, 0.001f);
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
	Assert.assertEquals("O objeto contido na lista de locacoes é diferente do que se esperava",loc, atual);
	}
	
}