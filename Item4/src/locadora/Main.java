package locadora;


import exception.AlugarEx;
import exception.DevolverEx;

import java.util.ArrayList;

import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws SQLException{
		DBConn mysql_conn = new DBConn();
		mysql_conn.connect();
		
		// Plataformas
		ArrayList<Plataforma> plats = new ArrayList<Plataforma>();
		Plataforma ps4 = new Plataforma("PlayStation 4", 1.3f);
		Plataforma xbone = new Plataforma("Xbox One", 1.2f);
		Plataforma swit = new Plataforma("Nintendo Switch", 1.2f);
		Plataforma ps3 = new Plataforma("PlayStation 3", 1.1f);
		Plataforma x360 = new Plataforma("Xbox 360", 1.0f);
		plats.add(ps4);
		plats.add(xbone);
		plats.add(swit);
		plats.add(ps3);
		plats.add(x360);
		
		// Jogos
		Jogo fifa19p4 = new Jogo("FIFA 19", 2.0f, 10, ps4);
		Jogo fifa19x1 = new Jogo("FIFA 19", 2.0f, 10, xbone);
		Jogo fifa19p3 = new Jogo("FIFA 19", 2.0f, 10, ps3);
		Jogo fifa19x3 = new Jogo("FIFA 19", 2.0f, 10, x360);
		Jogo fifa19sw = new Jogo("FIFA 18", 2.0f, 10, swit);
		Jogo rlp = new Jogo("Rocket League", 1.0f, 40, ps4);
		Jogo rlx = new Jogo("Rocket League", 1.0f, 40, xbone);
		Jogo gow = new Jogo("God of War", 2.5f, 12, ps4);
		Jogo tlou = new Jogo("The Last of Us", 2.5f, 15, ps4);
		
		//mysql_conn.addJogo(fifa19p4);
		//System.out.println(mysql_conn.getPlat("PS4").toString());
		
		// Add jogos aas plataformas
		ps4.addJogo(fifa19p4);
		ps4.addJogo(rlp);
		ps4.addJogo(gow);
		ps4.addJogo(tlou);
		xbone.addJogo(fifa19x1);
		xbone.addJogo(rlx);
		x360.addJogo(fifa19x3);
		ps3.addJogo(fifa19p3);
		swit.addJogo(fifa19sw);
		
		// Clientes
		Cliente joao = new Cliente("Joao","1234567","09123182","joao@mail.com","992883229");
		Cliente victor = new Cliente("Victor", "2345678", "08123741", "victor@mail.com","999872318");
		Cliente pedro = new Cliente("Pedro", "1247821", "01239878", "pedro@mail.com", "992738212");
		
		
		// Locacoes
		try {
			Locacao loc0 = new Locacao(fifa19p4, 14);
			joao.addLocacao(loc0);
			loc0.alugar();
			System.out.println("Locacao realizada. Protocolo: " + loc0.getID());
			System.out.println(loc0.toString());
			
			Locacao loc1 = new Locacao(gow, 7);
			loc1.alugar();
			joao.addLocacao(loc1);
			System.out.println("Locacao realizada. Protocolo: " + loc1.getID());
			System.out.println(loc1.toString());
			
			Locacao loc2 = new Locacao(rlx, 7);
			loc2.alugar();
			pedro.addLocacao(loc1);
			System.out.println("Locacao realizada. Protocolo: " + loc2.getID());
			System.out.println(loc2.toString());
			
			// Mostrar clientes
			System.out.println(joao.toString());
			System.out.println(victor.toString());
			System.out.println(pedro.toString());
			
			// Devolver jogos
			loc0.devolver();
			System.out.println("Locacao #" + loc0.getID() + " finalizada.");
			loc1.devolver();
			System.out.println("Locacao #" + loc1.getID() + " finalizada.");
			loc2.devolver();
			System.out.println("Locacao #" + loc2.getID() + " finalizada.");
			

			// Mostrar clientes novamente
			System.out.println(joao.toString());
			System.out.println(victor.toString());
			System.out.println(pedro.toString());
		} catch(AlugarEx ex){
			System.out.println("Erro ao alugar: " + ex.getMessage());
		} catch(DevolverEx ex){
			System.out.println("Erro ao devolver: " + ex.getMessage());
		}
		
		mysql_conn.close();
		System.exit(0);
	}
}
