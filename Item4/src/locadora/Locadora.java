package locadora;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Locadora {

	private ArrayList<Jogo> jogos;
	private ArrayList<Plataforma> plataformas;
	private ArrayList<Cliente> clientes;
	private ArrayList<Locacao> locacoes;
	
	public Locadora() {
		// Ao gerar objeto principal, recuperar todos os dados do database
		// Usa apenas uma conexao para todos, nao necessitanto abrir varias
		ConnFactory cf = new ConnFactory();
		Connection conn = cf.getConn();
		PlataformaDAO pdao = new PlataformaDAO(conn);
		this.plataformas = pdao.getAll();
		ClienteDAO cdao = new ClienteDAO(conn);
		this.clientes = cdao.getAll();
		JogoDAO jdao = new JogoDAO(conn);
		this.jogos = jdao.getAll();
		LocacaoDAO ldao = new LocacaoDAO(conn);
		this.locacoes = ldao.getAll();
		cf.close(conn);
	}
	
	public void cadastrar_plat() {
		Scanner reader = new Scanner(System.in);
		String nome = getstr("Nome da plataforma:", reader);
		if(search_plat(nome) == -1) {
			double coef = getdbl("Coeficiente:", reader);
			Plataforma plat = new Plataforma(nome, coef);
			// Salva localmente
			this.plataformas.add(plat);
			// Salva no banco de dados
			PlataformaDAO pdao = new PlataformaDAO();
			pdao.add(plat);
			pdao.close();
		} else {
			System.out.println("Plataforma ja cadastrada");
		}
	}
	
	public void cadastrar_jogo() {
		Scanner reader = new Scanner(System.in);
		String titulo = getstr("Titulo:", reader);
		double pbase = getdbl("Preco base:", reader);
		int qtd = getint("Quantidade:", reader);
		
		int platpos = search_plat("euro truck simulator");
		// Pede plataforma ate ter uma existente
		while(platpos == -1) {
			String plat = getstr("Plataforma:", reader);
			platpos = search_plat(plat);
		}
		Jogo gm = new Jogo(titulo,pbase,qtd,plataformas.get(platpos));
		// Salva no banco de dados
		JogoDAO jdao = new JogoDAO();
		jdao.add(gm);
		
		// Salva localmente (com o id)
		this.jogos.clear();
		this.jogos = jdao.getAll();
		
		jdao.close();
	}
	
	public void cadastrar_cliente() {
		Scanner reader = new Scanner(System.in);

		String nome = getstr("Nome: ", reader);
		String rg = "0";
		int rgc = 0;
		while(rgc != -1) {
			rg = getstr("RG: ", reader);
			rgc = search_client(rg);
		}
		String cpf = getstr("CPF: ", reader);
		String email = getstr("E-mail:", reader);
		String tel = getstr("Telefone:", reader);
		
		// Salva localmente
		Cliente cli = new Cliente(nome,rg,cpf,email,tel);
		this.clientes.add(cli);
		// Salva no banco de dados
		ClienteDAO cdao = new ClienteDAO();
		cdao.add(cli);
		cdao.close();
	}
	
	public int search_plat(String nome) {
		if(!this.plataformas.isEmpty()) {
			for(int i=0;i<this.plataformas.size();i++) {
				if(this.plataformas.get(i).getNome().equals(nome)){
					return i;
				}
			}
		}
		return -1;
	}
	
	public int search_client(String rg) {
		if(!this.clientes.isEmpty()) {
			for(int i=0;i<this.clientes.size();i++) {
				if(this.clientes.get(i).getRG().equals(rg)){
					return i;
				}
			}
		}
		return -1;
	}
	
	public String getstr(String txtin, Scanner reader) {
		System.out.println(txtin);
		return reader.nextLine();
	}
	
	public int getint(String txtin, Scanner reader) {
		System.out.println(txtin);
		return reader.nextInt();
	}
	
	public double getdbl(String txtin, Scanner reader) {
		System.out.println(txtin);
		return reader.nextDouble();
	}

}
