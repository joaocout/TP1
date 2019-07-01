package locadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import interfaces.DAO;

public class JogoDAO implements DAO<Jogo, Integer>{
	
	private Connection conn;
	private String add_jogo_sql = "INSERT INTO Jogos (Titulo,Preco_base,Quantidade,Plataforma) VALUES (?,?,?,?)";
	private String rm_jogo_sql = "DELETE FROM Jogos WHERE Titulo = ? AND Plataforma = ?";
	private String get_jogo_sql = "SELECT * FROM Jogos WHERE id = ?";
	private String getall_jogo_sql = "SELECT * FROM Jogos";
	private String upd_jogo_sql = "UPDATE Jogos SET Titulo = ?, Preco_base = ?, Quantidade = ?, Plataforma = ? WHERE id = ?";

	public JogoDAO() {
		this.conn = new ConnFactory().getConn();
	}
	public JogoDAO(Connection c) {
		this.conn = c;
	}
	
	//public void addJogo(Jogo jogo) throws SQLException {
	public boolean add(Jogo jogo) {
		try {
			PreparedStatement smt = conn.prepareStatement(add_jogo_sql);
			smt.setString(1, jogo.getTitulo());
			smt.setDouble(2, jogo.getPrecoBase());
			smt.setInt(3, jogo.getQtd());
			smt.setString(4, jogo.getPlataforma().getNome());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//public void rmvJogo(Jogo jogo) throws SQLException {
	public boolean remove(Jogo jogo) {
		try {
			PreparedStatement smt = conn.prepareStatement(rm_jogo_sql);
			smt.setString(1, jogo.getTitulo());
			smt.setString(2, jogo.getPlataforma().getNome());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//public Jogo getJogo(int id) throws SQLException {
	public Jogo get(Integer id) {
		try{
			PreparedStatement smt = conn.prepareStatement(get_jogo_sql);
			smt.setInt(1, id);
			ResultSet rst = smt.executeQuery();
			PlataformaDAO pdao = new PlataformaDAO(conn);
			rst.next();
			Jogo jg = new Jogo(rst.getString("Titulo"), rst.getDouble("Preco_base"), rst.getInt("Quantidade"),pdao.get(rst.getString("Plataforma")));
			jg.setID(id);
			smt.close();
			rst.close();
			return jg;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//public ArrayList<Jogo> getAllJogos() throws SQLException {
	public ArrayList<Jogo> getAll() {
		ArrayList<Jogo> jogos = new ArrayList<Jogo>();
		try {
			PreparedStatement smt = conn.prepareStatement(getall_jogo_sql);
			ResultSet rst = smt.executeQuery();
			PlataformaDAO pdao = new PlataformaDAO(conn);
			if(rst.next() == true) {
				do {
					Jogo newg = new Jogo(rst.getString("Titulo"),rst.getDouble("Preco_base"),rst.getInt("Quantidade"),pdao.get(rst.getString("Plataforma")));
					newg.setID(rst.getInt("id"));
					jogos.add(newg);
				} while(rst.next());
			}
			smt.close();
			rst.close();
		}  catch(SQLException e) {
			e.printStackTrace();
		}
		return jogos;
	}
	
	//public void updateJogo(Jogo jogo) throws SQLException {
	public boolean update(Jogo jogo) {
		try {
			PreparedStatement smt = conn.prepareStatement(upd_jogo_sql);
			smt.setString(1, jogo.getTitulo());
			smt.setDouble(2, jogo.getPrecoBase());
			smt.setInt(3, jogo.getQtd());
			smt.setString(4, jogo.getPlataforma().getNome());
			smt.setInt(5, jogo.getID());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean close() {
		try {
			this.conn.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
