package tech.mistermel.discordlinker;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.configuration.ConfigurationSection;

import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

	private HikariDataSource ds;
	
	public void connect(ConfigurationSection configSection) {
		this.ds = new HikariDataSource();
		ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        ds.addDataSourceProperty("serverName", configSection.getString("host"));
        ds.addDataSourceProperty("port", configSection.getInt("port"));
        ds.addDataSourceProperty("databaseName", configSection.getString("database"));
        ds.addDataSourceProperty("user", configSection.getString("username"));
        ds.addDataSourceProperty("password", configSection.getString("password"));
	}
	
	public void createTables() throws SQLException {
		Connection conn = ds.getConnection();
		
		
		
		conn.close();
	}
	
}
