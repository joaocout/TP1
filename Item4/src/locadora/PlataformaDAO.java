package locadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import interfaces.DAO;

public class PlataformaDAO implements DAO<Plataforma, String>{
	
	private Connection conn;
	private String add_plat_sql = "INSERT INTO Plataformas (NomePlat, Coeficiente) VALUES (?,?)";
	private String rm_plat_sql = "DELETE FROM Plataformas WHERE NomePlat = ?";
	private String get_plat_sql = "SELECT * FROM Plataformas WHERE NomePlat = ?";
	private String getall_plat_sql = "SELECT * FROM Plataformas";
	private String upd_plat_sql = "UPDATE Plataformas SET Coeficiente = ? WHERE NomePlat = ?";

	public PlataformaDAO() {
		this.conn = new ConnFactory().getConn();
	}
	public PlataformaDAO(Connection c) {
		this.conn = c;
	}
	
	public boolean add(Plataforma plat) {
		try {
			PreparedStatement smt = conn.prepareStatement(add_plat_sql);
			smt.setString(1, plat.getNome());
			smt.setDouble(2, plat.getCoeficiente());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean remove(Plataforma plat) {
		try {
			PreparedStatement smt = conn.prepareStatement(rm_plat_sql);
			smt.setString(1, plat.getNome());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Plataforma get(String NomePlat) {
		try {
			PreparedStatement smt = conn.prepareStatement(get_plat_sql);
			smt.setString(1, NomePlat);
			ResultSet rst = smt.executeQuery();
			rst.next();
			Plataforma plat = new Plataforma(rst.getString("NomePlat"), rst.getDouble("Coeficiente"));
			smt.close();
			rst.close();
			return plat;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Plataforma> getAll() {
		ArrayList<Plataforma> plats = new ArrayList<Plataforma>();
		try {
			PreparedStatement smt = conn.prepareStatement(getall_plat_sql);
			ResultSet rst = smt.executeQuery();
			if(rst.next() == true) {
				do {
					Plataforma plat = new Plataforma(rst.getString("NomePlat"), rst.getDouble("Coeficiente"));
					plats.add(plat);
				} while(rst.next());
			}
			smt.close();
			rst.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return plats;
	}
	
	public boolean update(Plataforma plat) {
		try {
			PreparedStatement smt = conn.prepareStatement(upd_plat_sql);
			smt.setDouble(1, plat.getCoeficiente());
			smt.setString(2,  plat.getNome());
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
