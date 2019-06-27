package locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.PrecoEx;

public class DBConn {
	
	private Connection conn;
	private boolean has_connection;
	private String add_cli_sql = "INSERT INTO Clientes" + " (RG, Nome, CPF, email, Telefone) " + "VALUES (?,?,?,?,?)";
	private String add_loc_sql = "INSERT INTO Locacoes" + " (data_aluguel, data_devolucao, hora_aluguel, hora_devolucao, preco_final, finalizada, dias, jogo_id, cliente_id) " + "VALUES (?,?,?,?,?,?,?,?,?)";
	private String add_plat_sql = "INSERT INTO Plataformas" + " (NomePlat, Coeficiente) " + "VALUES (?,?)";
	private String add_jogo_sql = "INSERT INTO Jogos" + " (Titulo,Preco_base,Quantidade,Plataforma) " + "VALUES (?,?,?,?)";

	
	public DBConn(){
		has_connection = false;
	}
	
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
		this.conn.close();
	}
	
	public Connection getConn() {
		return this.conn;
	}
	
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
}
