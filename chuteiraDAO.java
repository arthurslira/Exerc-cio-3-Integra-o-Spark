import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.chuteira;

public class chuteiraDAO extends DAO {
	
	public chuteiraDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Chuteira chuteira) {
		boolean status = false;
		
		// Condição para impossibilitar valores inválidos
		if(chuteira.getPreco() > 0 && chuteira.getSabor() != "" && chuteira.getTamanho() != "") {
			try {  
				Statement st = conexao.createStatement();
				String sql = "INSERT INTO chuteira2 (codigo, sabor, tamanho, preco) "
					       + "VALUES ("+ chuteira.getCodigo() +",'"+ chuteira.getSabor() + "', '"  
					       + chuteira.getTamanho() + "', " + chuteira.getPreco() + ");";
				System.out.println(sql);
				st.executeUpdate(sql);
				st.close();
				status = true;
			} catch (SQLException u) {  
				throw new RuntimeException(u);
			}
		}
		
		return status;
	}

	
	public chuteira get(int codigo) {
		chuteira chuteira = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM chuteira2 WHERE codigo=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 chuteira = new chuteira(rs.getInt("codigo"), rs.getString("sabor"), rs.getString("tamanho"), rs.getDouble("preco"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return chuteira;
	}
	
	
	public List<chuteira> get() {
		return get("");
	}

	
	public List<chuteira> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	public List<chuteira> getOrderBySabor() {
		return get("sabor");		
	}
	
	public List<chuteira> getOrderByTamanho() {
		return get("tamanho");		
	}
	
	
	public List<chuteira> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<chuteira> get(String orderBy) {	
	
		List<chuteira> chuteiras = new ArrayList<chuteira>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM chuteira2" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	chuteira u = new chuteira(rs.getInt("codigo"), rs.getString("sabor"), rs.getString("tamanho"), rs.getDouble("preco"));
	            chuteiras.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return chuteiras;
	}	
	
	public boolean update(chuteira chuteira) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE chuteira2 SET codigo = " + chuteira.getCodigo()+", sabor = '" + chuteira.getSabor() + "', tamanho = '"  
				       + chuteira.getTamanho() + "', preco = " + chuteira.getPreco()
					   + " WHERE codigo = " + chuteira.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM chuteira2 WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean autenticar(String sabor, String tamanho) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM chuteira2 WHERE sabor LIKE '" + sabor + "' AND tamanho LIKE '" + tamanho  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
}