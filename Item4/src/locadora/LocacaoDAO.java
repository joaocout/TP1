package locadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exception.PrecoEx;
import interfaces.DAO;

public class LocacaoDAO implements DAO<Locacao, Integer>{

	private Connection conn;
	private String add_loc_sql = "INSERT INTO Locacoes (data_aluguel, data_devolucao, hora_aluguel, hora_devolucao, preco_final, finalizada, dias, jogo_id, cliente_id) VALUES (?,?,?,?,?,?,?,?,?)";
	private String rm_loc_sql = "DELETE FROM Locacoes WHERE ID = ?";
	private String get_loc_sql = "SELECT * FROM Locacoes WHERE id = ?";	
	private String getall_loc_sql = "SELECT * FROM Locacoes";
	private String upd_loc_sql = "UPDATE Locacoes SET data_aluguel = ?, data_devolucao = ?, hora_aluguel = ?, hora_devolucao = ?, preco_final = ?, finalizada = ?, dias = ?, jogo_id = ?, cliente_id = ? WHERE id = ?";
	
	public LocacaoDAO() {
		this.conn = new ConnFactory().getConn();
	}
	
	public boolean add(Locacao loc) {
		try {
			PreparedStatement smt = conn.prepareStatement(add_loc_sql);
			smt.setString(1, loc.getDataAluguel());
			smt.setString(2, loc.getDataDevolucao());
			smt.setString(3, loc.getHoraAluguel());
			smt.setString(4, loc.getHoraDevolucao());
			smt.setDouble(5, loc.PrecoFinal());
			smt.setBoolean(6, loc.getFinalizada());
			smt.setInt(7, loc.getDias());
			smt.setInt(8, loc.getJogo().getID());
			smt.setString(9, loc.getCliente().getRG());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(PrecoEx e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean remove(Locacao loc) {
		try {
			PreparedStatement smt = conn.prepareStatement(rm_loc_sql);
			smt.setInt(1, loc.getID());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Locacao get(Integer id) {
		try {
			PreparedStatement smt = conn.prepareStatement(get_loc_sql);
			smt.setInt(1, id);
			ResultSet rst = smt.executeQuery();
			JogoDAO jdao = new JogoDAO(conn);
			Locacao loc = new Locacao(rst.getString("data_aluguel"), rst.getString("data_devolucao"), rst.getString("hora_aluguel"), rst.getString("hora_devolucao"), rst.getDouble("preco_final"), rst.getBoolean("finalizada"), jdao.get(rst.getInt("jogo_id")), rst.getInt("dias"));
			smt.close();
			rst.close();
			return loc;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Locacao> getAll() {
		try {
			ArrayList<Locacao> locs = new ArrayList<Locacao>();
			PreparedStatement smt = conn.prepareStatement(getall_loc_sql);
			ResultSet rst = smt.executeQuery();
			while(rst.next()) {
				JogoDAO jdao = new JogoDAO(conn);
				Locacao loc = new Locacao(rst.getString("data_aluguel"), rst.getString("data_devolucao"), rst.getString("hora_aluguel"), rst.getString("hora_devolucao"), rst.getDouble("preco_final"), rst.getBoolean("finalizada"), jdao.get(rst.getInt("jogo_id")), rst.getInt("dias"));
				locs.add(loc);
			}
			smt.close();
			rst.close();
			return locs;
		}  catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean update(Locacao loc) {
		try {
			PreparedStatement smt = conn.prepareStatement(upd_loc_sql);
			smt.setString(1,  loc.getDataAluguel());
			smt.setString(2,  loc.getDataDevolucao());
			smt.setString(3,  loc.getHoraAluguel());
			smt.setString(4,  loc.getHoraDevolucao());
			smt.setDouble(5,  loc.PrecoFinal());
			smt.setBoolean(6,  loc.getFinalizada());
			smt.setInt(7, loc.getDias());
			smt.setInt(8, loc.getJogo().getID());
			smt.setString(9, loc.getCliente().getRG());
			smt.setInt(10, loc.getID());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(PrecoEx e) {
			e.printStackTrace();
			return false;
		}
	}

}
