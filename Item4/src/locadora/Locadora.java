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
	private Scanner reader;
	
	public Locadora(Scanner reader) {
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
		this.reader = reader;
	}
	
	public void cadastrar_plat() {
		String nome = getstr("Nome da plataforma:");
		if(search_plat(nome) == -1) {
			double coef = getdbl("Coeficiente:");
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
		String titulo = getstr("Titulo:");
		double pbase = getdbl("Preco base:");
		int qtd = getint("Quantidade:");
		
		int platpos = search_plat("euro truck simulator");
		// Pede plataforma ate ter uma existente
		while(platpos == -1) {
			String plat = getstr("Plataforma:");
			platpos = search_plat(plat);
		}
		if(search_gameplat(titulo,this.plataformas.get(platpos).getNome()) != -1) {
			System.out.println("Jogo ja cadastrado.");
			return;
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
		String nome = getstr("Nome:");
		String rg = "0";
		int rgc = 0;
		while(rgc != -1) {
			rg = getstr("RG: ");
			rgc = search_client(rg);
		}
		String cpf = getstr("CPF: ");
		String email = getstr("E-mail:");
		String tel = getstr("Telefone:");
		
		// Salva localmente
		Cliente cli = new Cliente(nome,rg,cpf,email,tel);
		this.clientes.add(cli);
		// Salva no banco de dados
		ClienteDAO cdao = new ClienteDAO();
		cdao.add(cli);
		cdao.close();
	}
	
	public boolean consulta_jogo() {
		String gname = getstr("Nome do jogo:");
		if(!this.jogos.isEmpty()) {
			boolean found = false;
			for(int i=0;i<this.jogos.size();i++) {
				Jogo curr_gm = this.jogos.get(i);
				if(curr_gm.getTitulo().equals(gname)) {
					System.out.println("ID: " + curr_gm.getID() + " | Plataforma: " + curr_gm.getPlataforma().getNome() +
							" | Quantidade: " + curr_gm.getQtd() + " | Preco/dia: R$" + (curr_gm.getPrecoBase() * curr_gm.getPlataforma().getCoeficiente()));
					found = true;
				}
			}
			if(found == false) {
				System.out.println("Jogo nao cadastrado.");
			}
			return found;
		} else {
			System.out.println("Nenhum jogo cadastrado.");
			return false;
		}
	}
	
	public boolean consulta_plat() {
		String pname = getstr("Nome da plataforma:");
		int pnumber = search_plat(pname);
		if(pnumber != -1) {
			Plataforma curr_plat = this.plataformas.get(pnumber);
			if(!this.jogos.isEmpty()) {
				boolean found = false;
				for(int j=0;j<this.jogos.size();j++) {
					Jogo curr_gm = this.jogos.get(j);
					if(curr_gm.getPlataforma().getNome().equals(curr_plat.getNome())) {
						System.out.println("ID: " + curr_gm.getID() + " | Titulo: " +
										curr_gm.getTitulo() + " | Quantidade: " + curr_gm.getQtd() +
										" | Preco/dia: R$" + (curr_gm.getPrecoBase() * curr_plat.getCoeficiente()));
						found = true;
					}
				}
				if(!found)
					System.out.println("Nenhum jogo cadastrado para essa plataforma.");
			} else
				System.out.println("Nenhum jogo cadastrado.");
			return true;
		} else {
			System.out.println("Plataforma nao encontrada.");
			return false;
		}
	}
	
	public boolean consulta_cliente() {
		String crg = getstr("RG do cliente:");
		int nrg = search_client(crg);
		if(nrg != 1) {
			Cliente curr_c = this.clientes.get(nrg);
			//for(int i=0;i<this.clientes.size();i++) {
				if(curr_c.getRG().equals(crg)) {
					System.out.println("Nome: " + curr_c.getNome());
					System.out.println("CPF: " + curr_c.getCPF());
					System.out.println("E-mail: " + curr_c.getEmail());
					System.out.println("Telefone: " + curr_c.getTelefone());
					System.out.println("Divida: R$" + curr_c.Divida());
					System.out.println("Locacoes ativas: FAZER");
				}
			//}
			return true;
		} else {
			System.out.println("Cliente nao encontrado.");
			return false;
		}
	}
	
	/* ************************************************
	 * Funcoes auxiliares
	 * 
	 **************************************************/
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
				if(this.clientes.get(i).getRG().equals(rg))
					return i;
			}
		}
		return -1;
	}
	
	public int search_gameplat(String titulo, String plat) {
		if(!this.jogos.isEmpty()) {
			for(int i=0;i<this.jogos.size();i++) {
				if(this.jogos.get(i).getTitulo().equals(titulo) && this.jogos.get(i).getPlataforma().getNome().equals(plat))
						return i;
			}
		}
		return -1;
	}
	
	public String getstr(String txtin) {
		System.out.println(txtin);
		return this.reader.nextLine();
	}
	
	public int getint(String txtin) {
		System.out.println(txtin);
		return this.reader.nextInt();
	}
	
	public double getdbl(String txtin) {
		System.out.println(txtin);
		return this.reader.nextDouble();
	}

}
