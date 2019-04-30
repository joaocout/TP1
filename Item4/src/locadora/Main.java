package locadora;

import exception.AlugarEx;
import exception.DevolverEx;

public class Main {
	public static void main(String[] args){
		Plataforma ps4 = new Plataforma("PS4",1.5f);
		Jogo gow = new Jogo("God of War", 2.0f, 10, ps4);
		Locacao loc0 = new Locacao(gow);
		Cliente joao = new Cliente("Joao","1234567","09123182","joao@mail.com","99883229");
		joao.addLocacao(loc0);
		try{
			loc0.alugar();
			System.out.println(loc0.toString());
			loc0.devolver();
			System.out.println(loc0.toString());
			loc0.devolver();
		} catch(AlugarEx ex){
			System.out.println("Erro ao alugar: " + ex.getMessage());
		} catch(DevolverEx ex){
			System.out.println("Erro ao devolver: " + ex.getMessage());
		}
		System.exit(0);
	}
}
