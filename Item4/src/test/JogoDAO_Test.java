package test;

import org.junit.*;
import locadora.*;

public class JogoDAO_Test {
	
	@Test
	public void testJogoDAOadd() {
		
		JogoDAO jdao = new JogoDAO();
		Plataforma pc = new Plataforma("PC", 1);
		Jogo rl = new Jogo ("Rocket League", 1.25, 4, pc);
		int sizeantes = jdao.getAll().size();
		jdao.add(rl);
		Assert.assertEquals("Houve um problema ao adicionar no banco de dados", sizeantes+1, jdao.getAll().size());
		
		jdao.remove(rl);
		jdao.close();
		
	}
	
	@Test
	public void testJogoDAOremove() {
		
		JogoDAO jdao = new JogoDAO();
		Plataforma pc = new Plataforma("PC", 1);
		Jogo rl = new Jogo ("Rocket League", 1.25, 4, pc);
		int sizeantes = jdao.getAll().size();
		jdao.add(rl);
		jdao.remove(rl);
		Assert.assertEquals("Houve um problema ao remover do banco de dados", sizeantes, jdao.getAll().size());
		
		jdao.close();
	}
	
	/*@Test
	public void testJogoDAOupdate() {
		
		JogoDAO jdao = new JogoDAO();
		Plataforma pc = new Plataforma("PC", 1);
		Jogo rl = new Jogo ("Rocket League", 1.25, 4, pc);
		jdao.add(rl);
		rl.setPrecoBase(1.10);
		Assert.assertEquals("Houve um problema ao atualizar o banco de dados", 1.10, jdao.get(rl.getID()).getPrecoBase(), 0.001);
		
		jdao.remove(rl);
		jdao.close();
	}*/
	
}
