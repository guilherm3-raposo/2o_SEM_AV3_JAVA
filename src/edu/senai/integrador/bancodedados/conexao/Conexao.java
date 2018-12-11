package edu.senai.integrador.bancodedados.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.TimeZone;

import edu.senai.integrador.ferramentas.Configuracoes;
import edu.senai.integrador.logs.GeraLog;

public class Conexao {
	private static Configuracoes config = new Configuracoes();
	private static Connection conexao = null;

	public static Connection abreConexao() throws ConexaoException {
		Properties prop = config.carrega(false);
		try {
			Class.forName(prop.getProperty("base.driver", "com.mysql.cj.jdbc.Driver"));
			conexao = DriverManager.getConnection(prop.getProperty("base.conector", "jdbc:mysql://") +
											      prop.getProperty("base.ip","localhost:3306/") + 
										          prop.getProperty("base.nome") +
								     "?useSSL=" + prop.getProperty("base.ssl", "false") +
						     "&serverTimezone=" + TimeZone.getDefault().getID(),
											      prop.getProperty("base.usr"),
											      prop.getProperty("base.pwd"));
		} catch (SQLException | ClassNotFoundException e) {
			throw new ConexaoException(EConexaoErro.ABRE_CONEXAO);
		}
		return conexao;
	}

	public static void fechaConexao() throws ConexaoException {
			if (conexao instanceof Connection)
				try {
					conexao.close();
				} catch (SQLException e) {
					new GeraLog().escreveLogArquivo(e);
					throw new ConexaoException(EConexaoErro.FECHA_CONEXAO);
				}
			conexao = null;
	}
}
