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
import edu.senai.integrador.beans.academia.Exercicio;
import edu.senai.integrador.beans.academia.ExercicioException;
import edu.senai.integrador.dao.sql.ColunasExercicio;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;
import edu.senai.integrador.logs.GeraLog;

public class ExercicioDAO implements ICRUDPadraoDAO<Exercicio, String> {
	private SqlSintaxe sq = new SqlSintaxe();
	private SqlTabelas tabelas = new SqlTabelas();
	private ColunasExercicio colunas = new ColunasExercicio();
	
	private Exercicio constroiExercicio (ResultSet rs) throws DAOException, SQLException, ExercicioException {
		Exercicio exercicio = new Exercicio();
		try {
			exercicio = new Exercicio(
									rs.getString(colunas.EXERCICIO),
									rs.getInt(colunas.SERIE),
									rs.getInt(colunas.REPETICAO),
									rs.getInt(colunas.CARGA),
									rs.getInt(colunas.TREINO));
		} catch (SQLException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return exercicio;
	}
		
	public String constroiInsert (Exercicio exercicio) throws ExercicioException {
		return 		  sq.INSERT    + 
					  sq.INTO +
				 tabelas.EXERCICIO + sq.OPEN_PAR +
				 colunas.EXERCICIO + sq.COMMA +
				 colunas.SERIE + sq.COMMA +
				 colunas.REPETICAO + sq.COMMA +
				 colunas.CARGA + sq.COMMA +
				 colunas.TREINO + sq.CLOSE_PAR + " " +
				      sq.VALUES + sq.OPEN_PAR +
	        sq.VARCHAR + exercicio.getExercicio()     + sq.VARCHAR + sq.COMMA +
				 		 exercicio.getSerie()   + sq.COMMA +
				 		 exercicio.getRepeticao() + sq.COMMA +
				 		 exercicio.getCarga() + sq.COMMA +
				 		 exercicio.getTreino() + sq.CLOSE_PAR + sq.SEMI_COLON;	    
	}		
	
	private String constroiUpdate(Exercicio exercicio) throws ExercicioException {		
		return sq.UPDATE +
	       tabelas.EXERCICIO +	
		   		sq.SET + 	
		   colunas.SERIE + 
		   		sq.EQUALS + 
		 exercicio.getSerie() + sq.COMMA +
		   colunas.REPETICAO + 
		   		sq.EQUALS + 
		 exercicio.getRepeticao() + sq.COMMA +
		   colunas.CARGA + 
		   		sq.EQUALS + 
		 exercicio.getCarga() + sq.COMMA +
		   colunas.TREINO + 
		   		sq.EQUALS + 
		 exercicio.getTreino() + " " +
				sq.WHERE + 
		   colunas.EXERCICIO + 
		    	sq.EQUALS + sq.VARCHAR + 
	     exercicio.getExercicio() + sq.VARCHAR +sq.SEMI_COLON;
		
	}	
	@Override
	public Exercicio consulta(String exercicio) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = sq.SELECT +
						   sq.ALL + 
						   sq.FROM + 
					  tabelas.EXERCICIO +
					  	   sq.WHERE + 
					  colunas.EXERCICIO + 
					  	   sq.EQUALS + sq.VARCHAR +
					  	      exercicio + sq.VARCHAR + sq.SEMI_COLON;
			ResultSet rs = st.executeQuery(sqlSt);
			return rs.first() ? constroiExercicio(rs) : null;
		} catch (ExercicioException | SQLException e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		}finally {
			Conexao.fechaConexao();
		}
		
	}	
	@Override
	public Map<String, Exercicio> consultaTodos() throws ConexaoException, DAOException, SQLException{
		Connection conexao = Conexao.abreConexao();
		try {
			Map<String, Exercicio> exercicio = new HashMap<String, Exercicio>();
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(sq.SELECT + 
										   sq.ALL + 
										   sq.FROM + 
									  tabelas.EXERCICIO + sq.SEMI_COLON);
			while (rs.next()) {
				exercicio.put(rs.getString(colunas.EXERCICIO), constroiExercicio(rs));
			}
			return exercicio;
		} catch (Exception e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return null;
	}
	
	@Override
	public List<Exercicio> consultaFaixa(String... codigos) throws ConexaoException, DAOException, SQLException {
		List<Exercicio> exercicios = new ArrayList<Exercicio>();
		for (String i : codigos) {
			exercicios.add(consulta(i));
		}
		return exercicios;
	}
	
	@Override
	public boolean insere(Exercicio exercicio) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			st.execute(constroiInsert(exercicio));
		} catch (ExercicioException | SQLException e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}
	@Override
	public List<Exercicio> insereVarios(Map<String, Exercicio> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Exercicio> insereVarios(List<Exercicio> objetos) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Exercicio naoInserido = new Exercicio();
		List<Exercicio> naoInseridos = new ArrayList<Exercicio>();
		try {
			for (Exercicio exercicio : objetos) {
				Statement st = conexao.createStatement();
				st.execute(constroiInsert(exercicio));
				naoInserido = exercicio;
			}
		} catch (ExercicioException | SQLException e) {
			naoInseridos.add(naoInserido);
			throw new DAOException(EDAOErros.INSERE_DADO);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;		
	}
	
	@Override
	public boolean altera(Exercicio exercicio) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			String update = constroiUpdate(exercicio);
			Statement st = conexao.createStatement();
			st.execute(update);
		} catch (SQLException e) {
			throw new DAOException(EDAOErros.ALTERA_DADO);
		} catch (ExercicioException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}
	
	@Override
	public boolean exclui(String exercicio) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			st.execute(sq.DELETE + 
					   sq.FROM + 
				  tabelas.EXERCICIO + 
					   sq.WHERE + 
				  colunas.EXERCICIO + 
					   sq.EQUALS + sq.VARCHAR+ 
					      exercicio + sq.VARCHAR + sq.SEMI_COLON);
		} catch (SQLException e) {
			throw new DAOException(EDAOErros.INSERE_DADO);
		} finally {
			Conexao.fechaConexao();
		}
	return true;

}
	
}