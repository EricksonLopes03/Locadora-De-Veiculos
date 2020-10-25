package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexao {

	public static Connection getConexao() {

		try {
			Class.forName("org.postgresql.Driver");

			Properties prop = new Properties();
			prop.put("user", "postgres");
			prop.put("password", "2603");
			prop.put("charset", "UTF-8");
			prop.put("lc_type", "ISO8859_1");
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Locadora", prop);
		} catch (Exception e) {

			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}
}
