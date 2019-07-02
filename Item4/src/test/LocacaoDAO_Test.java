package test;

import org.junit.*;
import locadora.*;

public class LocacaoDAO_Test {

	@Test
	public void testLocacaoDAOadd() {
		
		LocacaoDAO ldao = new LocacaoDAO();
		Cliente joao = new Cliente("Joao", "3555888", "09807605420", "joao@joao.com", "988888888");
		Plataforma pc = new Plataforma("PC", 1.1);
		Jogo rl = new Jogo ("Rocket League", 1.25, 4, pc);
		Locacao loc = new Locacao("2019-06-29", "2019-07-01", "12:30", "15:20", 25.50, true, rl, 2, joao);
		ldao.add(loc);
		Assert.assertEquals("Adicao incorreta ao banco de dados", "Rocket League", ldao.get(loc.getID()).getJogo().getTitulo());
		
		ldao.remove(loc);
		ldao.close();
	}
	
	@Test
	public void testLocacaoDAOremove() {
		
		LocacaoDAO ldao = new LocacaoDAO();
		Cliente joao = new Cliente("Joao", "3555888", "09807605420", "joao@joao.com", "988888888");
		Plataforma pc = new Plataforma("PC", 1.1);
		Jogo rl = new Jogo ("Rocket League", 1.25, 4, pc);
		Locacao loc = new Locacao("2019-06-29", "2019-07-01", "12:30", "15:20", 25.50, true, rl, 2, joao);
		int sizeantes = ldao.getAll().size();
		ldao.add(loc);
		ldao.remove(loc);
		Assert.assertEquals("Falha ao remover do banco de dados", sizeantes, ldao.getAll().size());
		
		ldao.close();
	}
	
	@Test
	public void testLocacaoDAOupdate() {
		
		LocacaoDAO ldao = new LocacaoDAO();
		Cliente joao = new Cliente("Joao", "3555888", "09807605420", "joao@joao.com", "988888888");
		Plataforma pc = new Plataforma("PC", 1.1);
		Jogo rl = new Jogo ("Rocket League", 1.25, 4, pc);
		Locacao loc = new Locacao("2019-06-29", "2019-07-01", "12:30", "15:20", 25.50, true, rl, 2, joao);
		ldao.add(loc);
		loc.setDataDevolucao("2019-07-02");
		loc.setDias(3);
		ldao.update(loc);
		Assert.assertEquals("Falha ao atualizar dados no banco de dados", 3, ldao.get(loc.getID()).getDias());
		
		ldao.remove(loc);
		ldao.close();
	}
	
}
