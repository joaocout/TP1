package locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exception.PrecoEx;

public class DBConn {
	
	private Connection conn;
	private boolean has_connection;
	private String add_cli_sql = "INSERT INTO Clientes (RG, Nome, CPF, email, Telefone) VALUES (?,?,?,?,?)";
	private String add_loc_sql = "INSERT INTO Locacoes (data_aluguel, data_devolucao, hora_aluguel, hora_devolucao, preco_final, finalizada, dias, jogo_id, cliente_id) VALUES (?,?,?,?,?,?,?,?,?)";
	private String add_plat_sql = "INSERT INTO Plataformas (NomePlat, Coeficiente) VALUES (?,?)";
	private String add_jogo_sql = "INSERT INTO Jogos (Titulo,Preco_base,Quantidade,Plataforma) VALUES (?,?,?,?)";

	private String rm_cli_sql = "DELETE FROM Clientes WHERE RG = ?";
	private String rm_loc_sql = "DELETE FROM Locacoes WHERE ID = ?";
	private String rm_plat_sql = "DELETE FROM Plataformas WHERE NomePlat = ?";
	private String rm_jogo_sql = "DELETE FROM Jogos WHERE ID = ?";
	
	private String get_cli_sql = "SELECT * FROM Clientes WHERE RG = ?";
	private String get_loc_sql = "SELECT * FROM Locacoes WHERE id = ?";
	private String get_plat_sql = "SELECT * FROM Plataformas WHERE NomePlat = ?";
	private String get_jogo_sql = "SELECT * FROM Jogos WHERE id = ?";
	
	private String getall_cli_sql = "SELECT * FROM Clientes";
	private String getall_loc_sql = "SELECT * FROM Locacoes";
	private String getall_plat_sql = "SELECT * FROM Plataformas";
	private String getall_jogo_sql = "SELECT * FROM Jogos";
	
	public DBConn(){
		has_connection = false;
	}
	/* ***************************************************
	 * Conexao ao banco de dados
	 * 
	 * ***************************************************/
	public void connect() {
		if(this.has_connection == false) {
			try {
				String url = "jdbc:mysql://localhost:3306/tp1";
				String user = "joao";
				String pwd = "12345";
				this.conn = DriverManager.getConnection(url, user, pwd);
				this.has_connection = true;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException();
		}
	}
	
	public void close() throws SQLException {
		if(this.has_connection == true)
			this.conn.close();
		else
			throw new RuntimeException();
	}
	
	public Connection getConn() throws Exception{
		if(this.has_connection == true)
			return this.conn;
		else
			throw new RuntimeException();
	}
	
	/* ***************************************************
	 * Insercoes de dados do banco de dados
	 * 
	 * ***************************************************/
	public void addJogo(Jogo jogo) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(add_jogo_sql);
		smt.setString(1, jogo.getTitulo());
		smt.setDouble(2, jogo.getPrecoBase());
		smt.setInt(3, jogo.getQtd());
		smt.setString(4, jogo.getPlataforma().getNome());
		smt.execute();
		smt.close();
	}
	
	public void addPlat(Plataforma plat) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(add_plat_sql);
		smt.setString(1, plat.getNome());
		smt.setDouble(2, plat.getCoeficiente());
		smt.execute();
		smt.close();
	}
	
	public void addLoc(Locacao loc, Cliente client, Jogo jogo, Plataforma plat) throws SQLException, PrecoEx {
		int gameid;
		
		PreparedStatement smt = conn.prepareStatement(add_loc_sql);
		smt.setString(1, loc.getDataAluguel());
		smt.setString(2, loc.getDataDevolucao());
		smt.setString(3, loc.getHoraAluguel());
		smt.setString(4, loc.getHoraDevolucao());
		smt.setDouble(5, loc.PrecoFinal());
		smt.setBoolean(6, loc.getFinalizada());
		smt.setInt(7, loc.getDias());
		// pega id do jogo
		String get_jogoid_sql = "SELECT id FROM Jogos WHERE Titulo = " + jogo.getTitulo() + " AND Plataforma = " + plat.getNome();
		PreparedStatement jogo_id = conn.prepareStatement(get_jogoid_sql);
		ResultSet jogo_id_rs = jogo_id.executeQuery();
		gameid = jogo_id_rs.getInt(1);
		jogo_id.close();
		
		smt.setInt(8, gameid);
		smt.setString(9, client.getRG());
		smt.execute();
		smt.close();
	}
	
	public void addCliente(Cliente client) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(add_cli_sql);
		smt.setString(1, client.getRG());
		smt.setString(2, client.getNome());
		smt.setString(3, client.getCPF());
		smt.setString(4, client.getEmail());
		smt.setString(5, client.getTelefone());
		smt.execute();
		smt.close();
	}
	
	/* ***************************************************
	 * Remocoes de dados do banco de dados
	 * 
	 * ***************************************************/
	public void rmvJogo(Jogo jogo) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(rm_jogo_sql);
		smt.setInt(1, jogo.getID());
		smt.execute();
		smt.close();
	}
	
	public void rmvPlat(Plataforma plat) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(rm_plat_sql);
		smt.setString(1, plat.getNome());
		smt.execute();
		smt.close();
	}
	
	public void rmvLoc(Locacao loc) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(rm_loc_sql);
		smt.setInt(1, loc.getID());
		smt.execute();
		smt.close();
	}
	
	public void rmvCliente(Cliente client) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(rm_cli_sql);
		smt.setString(1, client.getRG());
		smt.execute();
		smt.close();
	}
	
	/* ***************************************************
	 * Recuperar objetos do banco de dados
	 * (unico objeto, com base na primary key)
	 * ***************************************************/
	public Jogo getJogo(int id) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(get_jogo_sql);
		smt.setInt(1, id);
		ResultSet rst = smt.executeQuery();
		Jogo jg = new Jogo(rst.getString("Titulo"), rst.getDouble("Preco_base"), rst.getInt("Quantidade"),getPlat(rst.getString("Plataforma")));
		smt.close();
		rst.close();
		return jg;
	}
	
	public Plataforma getPlat(String NomePlat) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(get_plat_sql);
		smt.setString(1, NomePlat);
		ResultSet rst = smt.executeQuery();
		Plataforma plat = new Plataforma(rst.getString("NomePlat"), rst.getDouble("Coeficiente"));
		smt.close();
		rst.close();
		return plat;
	}
	
	public Locacao getLoc(int id) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(get_loc_sql);
		smt.setInt(1, id);
		ResultSet rst = smt.executeQuery();
		Locacao loc = new Locacao(rst.getString("data_aluguel"), rst.getString("data_devolucao"), rst.getString("hora_aluguel"), rst.getString("hora_devolucao"), rst.getDouble("preco_final"), rst.getBoolean("finalizada"), getJogo(rst.getInt("jogo_id")), rst.getInt("dias"));
		smt.close();
		rst.close();
		return loc;
	}
	
	public Cliente getCliente(String RG) throws SQLException {
		PreparedStatement smt = conn.prepareStatement(get_cli_sql);
		smt.setString(1, RG);
		ResultSet rst = smt.executeQuery();
		Cliente cli = new Cliente(rst.getString("Nome"),rst.getString("RG"),rst.getString("CPF"),rst.getString("email"),rst.getString("Telefone"));
		smt.close();
		rst.close();
		return cli;
	}
	
	/* ***************************************************
	 * Recuperar objetos do banco de dados
	 * (arraylist completo)
	 * ***************************************************/
	
	public ArrayList<Jogo> getAllJogos() throws SQLException {
		ArrayList<Jogo> jogos = new ArrayList<Jogo>();
		PreparedStatement smt = conn.prepareStatement(getall_jogo_sql);
		ResultSet rst = smt.executeQuery();
		while(rst.next()) {
			// faz outra query pra pegar a plataforma
			Jogo newg = new Jogo(rst.getString("Titulo"),rst.getDouble("Preco_base"),rst.getInt("Quantidade"),getPlat(rst.getString("Plataforma")));
			jogos.add(newg);
		}
		smt.close();
		rst.close();
		return jogos;
	}
	
	public ArrayList<Plataforma> getAllPlats() throws SQLException {
		ArrayList<Plataforma> plats = new ArrayList<Plataforma>();
		PreparedStatement smt = conn.prepareStatement(getall_plat_sql);
		ResultSet rst = smt.executeQuery();
		while(rst.next()) {
			Plataforma plat = new Plataforma(rst.getString("NomePlat"), rst.getDouble("Coeficiente"));
			plats.add(plat);
		}
		smt.close();
		rst.close();
		return plats;
	}
	
	public ArrayList<Locacao> getAllLocs() throws SQLException {
		ArrayList<Locacao> locs = new ArrayList<Locacao>();
		PreparedStatement smt = conn.prepareStatement(getall_loc_sql);
		ResultSet rst = smt.executeQuery();
		while(rst.next()) {
			Locacao loc = new Locacao(rst.getString("data_aluguel"), rst.getString("data_devolucao"), rst.getString("hora_aluguel"), rst.getString("hora_devolucao"), rst.getDouble("preco_final"), rst.getBoolean("finalizada"), getJogo(rst.getInt("jogo_id")), rst.getInt("dias"));
			locs.add(loc);
		}
		smt.close();
		rst.close();
		return locs;
	}
	
	public ArrayList<Cliente> getAllClientes() throws SQLException {
		ArrayList<Cliente> clients = new ArrayList<Cliente>();
		PreparedStatement smt = conn.prepareStatement(getall_cli_sql);
		ResultSet rst = smt.executeQuery();
		while(rst.next()) {
			Cliente cli = new Cliente(rst.getString("Nome"),rst.getString("RG"),rst.getString("CPF"),rst.getString("email"),rst.getString("Telefone"));
			clients.add(cli);
		}
		smt.close();
		rst.close();
		return clients;
	}
}
