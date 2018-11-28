package edu.senai.integrador.bancodedados.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class Conexao {
	private static Configuracoes config = new Configuracoes();
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
			Properties prop = config.carrega(false);
			Class.forName(prop.getProperty("driver", "com.mysql.cj.jdbc.Driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("conector", "jdbc:mysql://") +
											   prop.getProperty("ip","localhost:3306/") + 
//											   prop.getProperty("nomeBanco") +
											   "projeto_integrador" +
								  "?useSSL=" + prop.getProperty("ssl", "false") +
						  "&serverTimezone=" + prop.getProperty("timeZ"),	  		
											   prop.getProperty("usr"),
											   prop.getProperty("pwd"));
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
