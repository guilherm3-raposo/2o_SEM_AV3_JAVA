package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Login;
import edu.senai.integrador.dao.sql.ColunasLogin;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class LoginDAO implements ICRUDPadraoDAO<Login, String> {
	SqlTabelas tabelas = new SqlTabelas();
	SqlSintaxe sq = new SqlSintaxe();
	ColunasLogin colunas = new ColunasLogin();
	
	private Login constroiLogin(ResultSet rs) {
		try {
			Login login = new Login(rs.getString(colunas.USUARIO),
							 		rs.getString(colunas.SENHA),
							 		rs.getString(colunas.CPF),
							 		rs.getInt(colunas.ADMIN));
			return login;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
	
	@Override
	public Login consulta(String codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(sq.SELECT + 
									    	 "*" + 
									       sq.FROM +
									  tabelas.LOGIN + 
								 	  	   sq.WHERE +
								 	  colunas.CPF +
								 	  	   sq.EQUALS + sq.VARCHAR +
								 	  	      codigo + sq.VARCHAR + sq.SEMI_COLON);
			if (rs.first())
				return constroiLogin(rs);
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return null;
	}

	@Override
	public Map<String, Login> consultaTodos() throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Login> consultaFaixa(String... codigos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insere(Login objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Login> insereVarios(Map<String, Login> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Login> insereVarios(List<Login> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean altera(Login objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exclui(String objeto) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
