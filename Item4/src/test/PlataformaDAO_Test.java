package test;

import org.junit.*;
import locadora.*;

public class PlataformaDAO_Test {

	@Test
	public void testPlataformaDAOadd() {
		
		PlataformaDAO pdao = new PlataformaDAO();
		Plataforma pc = new Plataforma("PC", 1.1);
		pdao.add(pc);
		Assert.assertEquals("Ocorreu um erro na adicao ao banco de dados", "PC", pdao.get("PC").getNome());
		
		pdao.remove(pc);
		pdao.close();
		
	}
	
	@Test
	public void testPlataformaDAOremove() {
		
		PlataformaDAO pdao = new PlataformaDAO();
		Plataforma pc = new Plataforma("PC", 1.1);
		int sizeantes = pdao.getAll().size();
		pdao.add(pc);
		pdao.remove(pc);
		Assert.assertEquals("Houve um erro na remocao do banco de dados", sizeantes, pdao.getAll().size());
		
		pdao.close();
	}
	
	@Test
	public void testPlataformaDAOupdate() {
		
		PlataformaDAO pdao = new PlataformaDAO();
		Plataforma pc = new Plataforma("PC", 1.1);
		pdao.add(pc);
		pc.setCoeficiente(1.5);
		pdao.update(pc);
		Assert.assertEquals("Houve um problema no update no banco de dados", 1.5, pdao.get("PC").getCoeficiente(), 0.001);
	
		pdao.remove(pc);
		pdao.close();
	}

}
