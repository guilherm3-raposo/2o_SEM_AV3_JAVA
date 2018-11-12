package edu.senai.integrador.bancodedados.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class Conexao {
	private static Connection conn = null;

	public static Connection getConexao() throws ConexaoException {
		try {
			if (conn == null || conn.isClosed()) {
				conn = abreConexao();
			}
		} catch (SQLException e) {
			// TODO resolver
		}
		return conn;
	}

	private static Connection abreConexao() throws ConexaoException {
		try {
			Properties prop = Configuracoes.Carrega();
//			String url1 = "jdbc:mysql://localhost:3306/projeto_integrador";
//			String url2 = "?useSSL=false";
//			String url3 = "&serverTimezone=America/Sao_Paulo";
//			String usr = "root";
//			String pwd = "12345";
			String url1 = prop.getProperty("url1");
			String url2 = prop.getProperty("url2");
			String url3 = prop.getProperty("url3");
			String usr = prop.getProperty("usr");
			String pwd = prop.getProperty("pwd");
			System.out.println(url1 + url2 + url3 + "," + usr + "," + pwd);
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url1 + url2 + url3, usr, pwd);
			return conn;
		} catch (Exception e) {
			throw new ConexaoException(EConexaoErro.ABRE_CONEXAO, e.getMessage());
		}
	}

	public static void fechaConexao() throws ConexaoException {
		try {
			if (conn instanceof Connection)
				conn.close();
			conn = null;
		} catch (Exception e) {
			throw new ConexaoException(EConexaoErro.FECHA_CONEXAO, e.getMessage());
		}
	}
}
