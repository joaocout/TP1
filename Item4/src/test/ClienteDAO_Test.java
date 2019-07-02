package test;

import org.junit.*;
import locadora.*;

public class ClienteDAO_Test {
	
	@Test
	public void testClienteDAOadd() {
		
		ClienteDAO cdao = new ClienteDAO();
		Cliente joao = new Cliente("Joao", "3555555", "08808808811", "joao@joao.com", "933334444");
		cdao.add(joao);
		Assert.assertEquals("Houve uma falha na adicao ao banco de dados", "Joao", cdao.get("3555555").getNome());
		
		cdao.remove(joao);
		cdao.close();
		
	}
	
	@Test
	public void testClienteDAOremove() {
		
		ClienteDAO cdao = new ClienteDAO();
		Cliente joao = new Cliente("Joao", "3555555", "08808808811", "joao@joao.com", "933334444");
		int sizeantes = cdao.getAll().size();
		cdao.add(joao);
		cdao.remove(joao);
		Assert.assertEquals("Houve uma falha ao remover um elemento do banco de dados", sizeantes, cdao.getAll().size());
		
		cdao.close();
		
	}
	
	@Test
	public void testClienteDAOupdate() {
		
		ClienteDAO cdao = new ClienteDAO();
		Cliente joao = new Cliente("Joao", "3555555", "08808808811", "joao@joao.com", "933334444");
		cdao.add(joao);
		joao.setTelefone("900001212");
		cdao.update(joao);
		Assert.assertEquals("Houve uma falha no update do banco de dados", "900001212", cdao.get("35555555").getTelefone());
		
		cdao.remove(joao);
		cdao.close();
		
	}
	
}
