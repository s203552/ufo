package it.polito.tdp.ufo.db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.ufo.db.DBConnect;
import it.polito.tdp.ufo.bean.Coppia;
import it.polito.tdp.ufo.bean.FasciaOraria;
import it.polito.tdp.ufo.bean.Sighting;

public class UfoDAO {
	
	public List<String> getAllShape() {

		final String sql = "SELECT DISTINCT shape FROM sighting WHERE shape <>''";
		List<String> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {				
				result.add(rs.getString("shape"));
			}	conn.close();
			return result;
		} catch (SQLException e) {e.printStackTrace();
			return null;
			}
	 }


	public List<Sighting> getAllSighting(FasciaOraria fo, String shape) {

		final String sql = "SELECT  DISTINCT * FROM sighting s1 WHERE  Extract(HOUR FROM s1.`datetime`)>=? AND Extract(HOUR FROM s1.`datetime`)<=? and shape=? ";
		List<Sighting> o = new ArrayList<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fo.getOraP());
			st.setInt(2, fo.getOraA());
			st.setString(3, shape);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {	
				Sighting s= new Sighting (rs.getInt("id"), rs.getDate("datetime").toLocalDate(),rs.getString("shape"),
						new LatLng(rs.getDouble("latitude"), rs.getDouble("longitude")));
				o.add(s);	}	
			st.close();
			conn.close();
			return o;
		} catch (SQLException e) {e.printStackTrace();
			return null;
			}
	 }
	
	public List<Sighting> getAllSightingUnknown(FasciaOraria fo, String shape) {
		final String sql = "(SELECT  DISTINCT * FROM sighting s1 WHERE  Extract(HOUR FROM s1.`datetime`)>=? AND Extract(HOUR FROM s1.`datetime`)<=? and shape='unknown' ORDER BY Extract(HOUR FROM DATETIME))UNION( SELECT  DISTINCT * FROM sighting s1 WHERE  Extract(HOUR FROM s1.`datetime`)>=? AND Extract(HOUR FROM s1.`datetime`)<=? AND shape='' ORDER BY Extract(HOUR FROM DATETIME) )";
		List<Sighting> o = new ArrayList<>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fo.getOraP());
			st.setInt(2, fo.getOraA());
			st.setString(3, shape);
			st.setInt(4, fo.getOraP());
			st.setInt(5, fo.getOraA());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {	
				Sighting s= new Sighting (rs.getInt("id"), rs.getDate("datetime").toLocalDate(),rs.getString("shape"),
						new LatLng(rs.getDouble("latitude"), rs.getDouble("longitude")));
				o.add(s);	}	
			st.close();
			conn.close();
			return o;
		} catch (SQLException e) {e.printStackTrace();
			return null;
			}
	 }

    
	
public List<Coppia> getCoppieDiffDay(Integer da , Integer a, String shape,Map<Integer,Sighting>map) {  //Map<Integer,Sighting>map) {
		
		String sql = "SELECT s1.id,s1.datetime,s2.id,s2.datetime,Extract(HOUR FROM s1.`datetime`) o,Extract(HOUR FROM s2.`datetime`) o2,"
				+ "Extract(DAY FROM s2.datetime)-Extract(DAY FROM s1.datetime) diffDay, count(*) FROM sighting s1,sighting s2 "
				+ "WHERE Extract(HOUR FROM s1.`datetime`)<Extract(HOUR FROM s2.`datetime`) "
				+ "AND Extract(HOUR FROM s1.`datetime`)>=? AND Extract(HOUR FROM s1.`datetime`)<=? "
				+ "AND Extract(HOUR FROM s2.`datetime`)>=? AND Extract(HOUR FROM s2.`datetime`)<=? "
				+ "AND s1.shape=s2.shape AND s1.Shape=? AND s1.id<>s2.id "
				+ "GROUP BY s1.id,s2.id ORDER BY diffDay asc " ;
		List<Coppia>result=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, da);
			st.setInt(2, a);
			st.setInt(3, da);
			st.setInt(4, a);
			st.setString(5, shape);
			ResultSet rs = st.executeQuery() ;
			

			while(rs.next()) {
				result.add(new Coppia ( map.get(rs.getInt("s1.id")),map.get(rs.getInt("s2.id")),rs.getInt("diffDay"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public static void main(String[] args) {
		UfoDAO dao = new UfoDAO() ;
		
//		List<Integer> years = dao.getAnni() ;
//		System.out.println(years);
//		
//		List<Integer> seasons = dao.listCircoscrizioni(2009) ;
//		System.out.println(seasons);
//	
//		List<Coppie2> c = dao.getNumC(2009) ;
//		System.out.println(c);
		
	}

	
}


