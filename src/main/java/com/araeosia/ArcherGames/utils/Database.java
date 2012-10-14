package com.araeosia.ArcherGames.utils;

import com.araeosia.ArcherGames.ArcherGames;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

/**
 * 
 * @author Bruce, Daniel
 */
public class Database {
	
	public ArcherGames plugin;
	
	public Database(ArcherGames plugin){
		this.plugin = plugin;
	}
	
	public void setupTables() throws SQLException{
		plugin.dbConnect();
		
		PreparedStatement s = plugin.conn.prepareStatement("CREATE TABLE IF NOT EXISTS points(name varchar(20), points integer)");
		s.executeUpdate();
		s.close();
	}
	public void addPoints(String name, int points) throws SQLException{
		plugin.dbConnect();
		
		PreparedStatement s = plugin.conn.prepareStatement("INSERT INTO points (name,points) VALUES (?,?)");
		s.setString(1, name);
		s.setInt(2, points);
		s.executeUpdate();
		s.close();
		plugin.conn.close();
	}

	public void recordJoin(String name) {
		plugin.dbConnect();
		
		
	}

	public void recordQuit(String name) {
		plugin.dbConnect();
	}

	public void setMoney(String name, double d) {
		plugin.dbConnect();
		try {
			PreparedStatement s = plugin.conn.prepareStatement("INSERT INTO money (name,balance) VALUES (?,?)");
			s.setString(1, name);
			s.setDouble(2, d);
			s.executeUpdate();
			s.close();
		plugin.conn.close();
		} catch (SQLException e){
			
		}
	}
	
	public double getMoney(String name){
		plugin.dbConnect();
		double money = 0;
		
		try {
			PreparedStatement s = plugin.conn.prepareStatement("SELECT balance FROM money WHERE name=?");
			s.setString(1, name);
			ResultSet set = s.executeQuery();
		
			money = set.getDouble(1);
		
			s.close();
			plugin.conn.close();
			} catch (SQLException e){
			
			}
		
		return money;
	}
}
