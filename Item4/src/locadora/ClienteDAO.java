package locadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import interfaces.DAO;

public class ClienteDAO implements DAO<Cliente, String>{
	
	private Connection conn;
	private String add_cli_sql = "INSERT INTO Clientes (RG, Nome, CPF, email, Telefone) VALUES (?,?,?,?,?)";
	private String rm_cli_sql = "DELETE FROM Clientes WHERE RG = ?";
	private String get_cli_sql = "SELECT * FROM Clientes WHERE RG = ?";
	private String getall_cli_sql = "SELECT * FROM Clientes";
	private String upd_cli_sql = "UPDATE Clientes SET Nome = ?, CPF = ?, email = ?, Telefone = ? WHERE RG = ?";

	public ClienteDAO() {
		this.conn = new ConnFactory().getConn();
	}
	
	public boolean add(Cliente client) {
		try {
			PreparedStatement smt = conn.prepareStatement(add_cli_sql);
			smt.setString(1, client.getRG());
			smt.setString(2, client.getNome());
			smt.setString(3, client.getCPF());
			smt.setString(4, client.getEmail());
			smt.setString(5, client.getTelefone());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean remove(Cliente client) {
		try {
			PreparedStatement smt = conn.prepareStatement(rm_cli_sql);
			smt.setString(1, client.getRG());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Cliente get(String RG) {
		try {
			PreparedStatement smt = conn.prepareStatement(get_cli_sql);
			smt.setString(1, RG);
			ResultSet rst = smt.executeQuery();
			Cliente cli = new Cliente(rst.getString("Nome"),rst.getString("RG"),rst.getString("CPF"),rst.getString("email"),rst.getString("Telefone"));
			smt.close();
			rst.close();
			return cli;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Cliente> getAll() {
		try {
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
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean update(Cliente cli) {
		try {
			PreparedStatement smt = conn.prepareStatement(upd_cli_sql);
			smt.setString(1, cli.getNome());
			smt.setString(2, cli.getCPF());
			smt.setString(3,  cli.getEmail());
			smt.setString(4,  cli.getTelefone());
			smt.setString(5, cli.getRG());
			smt.execute();
			smt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
