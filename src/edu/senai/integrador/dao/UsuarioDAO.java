package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.cadastro.Usuario;
import edu.senai.integrador.dao.sql.ColunasLogin;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;
import edu.senai.integrador.logs.GeraLog;

public class UsuarioDAO implements ICRUDPadraoDAO<Usuario, String> {
	SqlTabelas tabelas = new SqlTabelas();
	SqlSintaxe sq = new SqlSintaxe();
	ColunasLogin colunas = new ColunasLogin();
	
	private Usuario constroiLogin(ResultSet rs) throws SQLException {
		Usuario login = new Usuario(rs.getString(colunas.USUARIO),
						 		rs.getString(colunas.SENHA),
						 		rs.getString(colunas.CPF),
						 		rs.getInt(colunas.PERMISSOES));
		return login;
	}
	
	private String constroiInsert(Usuario login) {
		return sq.INSERT + 
			     sq.INTO +
		    tabelas.LOGIN + sq.OPEN_PAR + 
		    colunas.USUARIO + sq.COMMA +
		    colunas.SENHA + sq.COMMA +
		    colunas.CPF + sq.COMMA +
		    colunas.PERMISSOES + sq.CLOSE_PAR + " " +
		  	     sq.VALUES + sq.OPEN_PAR + sq.VARCHAR + 
		 login.getUsuario() + sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
		 login.getSenha() + sq.VARCHAR + sq.COMMA + sq.VARCHAR +
		 login.getCpf() + sq.VARCHAR + sq.COMMA+
		 login.getPermissao() + sq.CLOSE_PAR + sq.SEMI_COLON;
		
	}
	
	private String constroiUpdate(Usuario usuario) {
		return sq.UPDATE 	  +
				   tabelas.LOGIN +
					sq.SET 		  +
			   colunas.USUARIO 	  +	sq.EQUALS + 
  sq.VARCHAR + usuario.getUsuario()    + sq.VARCHAR + sq.COMMA +	
			   colunas.SENHA	  +	sq.EQUALS + 
  sq.VARCHAR + usuario.getSenha()	+ sq.VARCHAR + sq.COMMA +
			   colunas.CPF     +	sq.EQUALS + 
  sq.VARCHAR + usuario.getCpf()	    + sq.VARCHAR + sq.COMMA +
			   colunas.PERMISSOES +	sq.EQUALS + 
  sq.VARCHAR + usuario.getPermissao()	+ sq.VARCHAR +  
			   		sq.WHERE + 
			   colunas.CPF + 
			   		sq.EQUALS + sq.VARCHAR +
			   usuario.getCpf() + sq.VARCHAR + sq.SEMI_COLON;
	}

	@Override
	public Usuario consulta(String codigo) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Statement st = conexao.createStatement();
		ResultSet rs = st.executeQuery(sq.SELECT + 
									   sq.ALL + 
									   sq.FROM +
								  tabelas.LOGIN + 
									   sq.WHERE +
								  colunas.USUARIO +
									   sq.EQUALS + sq.VARCHAR +
							  codigo + sq.VARCHAR + sq.SEMI_COLON);
			if (rs.first()) {
				return constroiLogin(rs);
			}
			return null;
	}

	public Usuario consultaCpf(String codigo) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Statement st = conexao.createStatement();
		ResultSet rs = st.executeQuery(sq.SELECT + 
									   sq.ALL + 
									   sq.FROM +
								  tabelas.LOGIN + 
									   sq.WHERE +
								  colunas.CPF +
									   sq.EQUALS + sq.VARCHAR +
							  codigo + sq.VARCHAR + sq.SEMI_COLON);
			if (rs.first()) {
				return constroiLogin(rs);
			}
			return null;
	}

	@Override
	public Map<String, Usuario> consultaTodos() throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Map<String, Usuario> usuarios = new HashMap<String, Usuario>();
		Statement st = conexao.createStatement();
		ResultSet rs = st.executeQuery(sq.SELECT +
									   sq.ALL + 
									   sq.FROM + 
								  tabelas.LOGIN + sq.SEMI_COLON);
		while (rs.next()) {
			usuarios.put(rs.getString(colunas.CPF), constroiLogin(rs));
		}
		return null;
	}

	@Override
	public List<Usuario> consultaFaixa(String... codigos) throws ConexaoException, DAOException, SQLException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for (String i : codigos) {
			usuarios.add(consulta(i));
		}
		return usuarios;
	}

	@Override
	public boolean insere(Usuario usuario) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			st.execute(constroiInsert(usuario));
		} catch (SQLException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public List<Usuario> insereVarios(Map<String, Usuario> usuarios) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		List<Usuario> naoInseridos = new ArrayList<Usuario>();
		try {
			usuarios.forEach((cpf,usuario) -> {
				Usuario naoInserido = new Usuario();
				Statement st;
				try {
					st = conexao.createStatement();
					st.execute(constroiInsert(usuario));
					naoInserido = usuario;
				} catch (SQLException e) {
					naoInseridos.add(naoInserido);
				}
			});
		} catch (Exception e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public List<Usuario> insereVarios(List<Usuario> usuarios) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		Usuario naoInserido = new Usuario();
		List<Usuario> naoInseridos = new ArrayList<Usuario>();
		try {
			for (Usuario usuario : usuarios) {
				Statement st = conexao.createStatement();
				st.execute(constroiInsert(usuario));
				naoInserido = usuario;
			}
		} catch (SQLException e) {
			naoInseridos.add(naoInserido);
			throw new DAOException(EDAOErros.REGISTRO_INVALIDO);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public boolean altera(Usuario usuario) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		String update = constroiUpdate(usuario);
		Statement st = conexao.createStatement();
		st.execute(update);
		return true;
	}

	@Override
	public boolean exclui(String cpf) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Statement st = conexao.createStatement();
		st.execute(sq.DELETE +
				   sq.FROM +
			  tabelas.LOGIN +
			  	   sq.WHERE +
			  colunas.CPF + 
			  	   sq.EQUALS  + sq.VARCHAR + 
			  	   	  cpf + sq.VARCHAR + sq.SEMI_COLON);
		return true;
	}

}
