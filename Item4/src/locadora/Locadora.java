package locadora;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import exception.AlugarEx;
import exception.DevolverEx;
import exception.PrecoEx;

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
			sysmsg("Plataforma ja cadastrada");
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
			sysmsg("Jogo ja cadastrado.");
			return;
		}
		
		Jogo gm = new Jogo(titulo,pbase,qtd,plataformas.get(platpos));
		// Salva no banco de dados
		JogoDAO jdao = new JogoDAO();
		
		if(jdao.add(gm)) {
			// Salva localmente (com o id)
			this.jogos.clear();
			this.jogos = jdao.getAll();
		} else {
			sysmsg("Erro ao cadastrar jogo.");
		}
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
	
	public boolean alugar_jogo() {
		boolean flag = false;
		
		String crg = getstr("RG do cliente:");
		int client_num = search_client(crg);
		// Se o cliente nao for encontrado
		if(client_num == -1) {
			sysmsg("Cliente nao cadastrado.");
			return false;
		}
		int dias = getint("Tempo (dias) de aluguel:");
		int gmid = getint("ID do jogo:");
		for(int i=0;i<this.jogos.size();i++) {
			Jogo gmused = this.jogos.get(i);
			if(gmused.getID() == gmid && gmused.getQtd() > 0) {
				flag = true;
				Cliente cused = this.clientes.get(client_num);
				Locacao loc = new Locacao(gmused, dias, cused);
				LocacaoDAO ldao = new LocacaoDAO();
				if(ldao.add(loc)){
					// Atualizar lista dos jogos (-1)
					gmused.subQtd();
					JogoDAO gdao = new JogoDAO();
					gdao.update(gmused);
					this.jogos.clear();
					this.jogos = gdao.getAll();
					gdao.close();
					// Atualiza lista das locacoes
					this.locacoes.clear();
					this.locacoes = ldao.getAll();
					int protocol = this.locacoes.get(this.locacoes.size()-1).getID();
					sysmsg("Aluguel realizado com sucesso. Protocolo: " + protocol);
				} else {
					sysmsg("Erro ao realizar o aluguel.");
				}
				ldao.close();
				break;
			}
		}
		return flag;
	}
	
	public boolean devolver_jogo() {
		boolean flag = false;
		String crg = getstr("RG do cliente:");
		int cnum = search_client(crg);
		if(cnum == -1) {
			sysmsg("Cliente nao cadastrado.");
			return false;
		}
		print_cli_loc(crg); // imprime locacoes do cliente
		int locnum = getint("Protocolo da locacao:");
		for(int i=0;i<this.locacoes.size();i++) {
			Locacao loc = this.locacoes.get(i);
			if(loc.getID() == locnum && loc.getFinalizada() == false) {
				flag = true;
				try {
					loc.devolver();
				} catch(DevolverEx e) {
					e.printStackTrace();
				}
				LocacaoDAO ldao = new LocacaoDAO();
				if(ldao.update(loc)) {
					// Atualizar lista de jogos (+1)
					Jogo gmused = loc.getJogo();
					gmused.addQtd();
					JogoDAO gdao = new JogoDAO();
					gdao.update(gmused);
					this.jogos.clear();
					this.jogos = gdao.getAll();
					gdao.close();
					// Atualizar lista de locacoes
					this.locacoes.clear();
					this.locacoes = ldao.getAll();
					sysmsg("Jogo devolvido com sucesso. Obrigado!");
				} else {
					sysmsg("Erro ao devolver jogo.");
				}
				ldao.close();
				break;
			}
		}
		
		return flag;
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
			if(!found) {
				sysmsg("Jogo nao cadastrado.");
			}
			return found;
		} else {
			sysmsg("Nenhum jogo cadastrado.");
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
					sysmsg("Nenhum jogo cadastrado para essa plataforma.");
			} else
				sysmsg("Nenhum jogo cadastrado.");
			return true;
		} else {
			sysmsg("Plataforma nao encontrada.");
			return false;
		}
	}
	
	public boolean consulta_cliente() {
		String crg = getstr("RG do cliente:");
		int nrg = search_client(crg);
		if(nrg != 1) {
			Cliente curr_c = this.clientes.get(nrg);
			if(curr_c.getRG().equals(crg)) {
				System.out.println("Nome: " + curr_c.getNome());
				System.out.println("CPF: " + curr_c.getCPF());
				System.out.println("E-mail: " + curr_c.getEmail());
				System.out.println("Telefone: " + curr_c.getTelefone());
				//System.out.println("Divida: R$" + curr_c.Divida());
				print_cli_loc(curr_c.getRG());
			}
			return true;
		} else {
			sysmsg("Cliente nao encontrado.");
			return false;
		}
	}
	
	public void print_cli_loc(String cli_rg) {
		int ncli = search_client(cli_rg);
		if(ncli == -1) {
			sysmsg("Cliente nao encontrado.");
			return;
		}
		boolean flag = false;
		System.out.println("Locacoes ativas:");
		for(int i=0;i<this.locacoes.size();i++) {
			Locacao loc = this.locacoes.get(i);
			if(loc.getCliente().getRG().equals(cli_rg) && !loc.getFinalizada()) {
				flag = true;
				System.out.println(loc.toString());
			}
		}
		if(flag == false)
			System.out.println("Sem locacoes ativas.");
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
	
	private void sysmsg(String msg) {
		System.out.println("-----------------------------------------------");
		System.out.println(msg);
		System.out.println("-----------------------------------------------");
	}
}
