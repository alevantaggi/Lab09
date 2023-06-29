package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) 
				result.add(new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme")));

			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			return null;
		}
	}

	public List<Border> getCountryPairs(int anno,Map<Integer, Country> countryIdMap) {
		List<Border> risultato= new ArrayList<>();
		String sql="SELECT state1no, state2no, year FROM contiguity WHERE conttype=1 AND YEAR<=? AND state1no > state2no ORDER BY year";
		
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			
			st.setInt(1, anno);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				Country uno= countryIdMap.get(rs.getInt("state1no"));
				Country due= countryIdMap.get(rs.getInt("state2no"));
				risultato.add(new Border(uno, due, rs.getInt("year")));
			}
			
			conn.close();
			return risultato;	
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
