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
import edu.senai.integrador.beans.Exercicio;
import edu.senai.integrador.dao.sql.ColunasExercicio;
import edu.senai.integrador.dao.sql.ExercicosComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class ExercicioDAO implements ICRUDPadraoDAO<Exercicio, String> {
	private SqlSintaxe sq = new SqlSintaxe();
	private ExercicosComandos comandos = new ExercicosComandos();
	private SqlTabelas tabelas = new SqlTabelas();
	private ColunasExercicio colunas = new ColunasExercicio();
	
	private Exercicio constroiExercicio (ResultSet resultSet) throws DAOException {
		try {
		Exercicio exercicio = new Exercicio (
											resultSet.getString(colunas.EXERCICIO),
											resultSet.getString(colunas.SERIE),
											resultSet.getString(colunas.REPETICAO),
											resultSet.getString(colunas.CARGA),
											resultSet.getString(colunas.TREINO));
		return exercicio;									
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	private String constroiInsert (Exercicio exercicio) throws DAOException {
		return comandos.INSERT    + 
				sq.VARCHAR + exercicio.getExercicio()     + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + exercicio.getSerie()   + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + exercicio.getRepeticao()     + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + exercicio.getCarga() + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + exercicio.getTreino() + sq.CLOSE_PAR + sq.SEMI_COLON;	    	
	}	
	private String constroiUpdate(Exercicio exercicio) throws DAOException {
		return 	sq.UPDATE + tabelas.EXERCICIO +	sq.SET +
		   colunas.EXERCICIO + sq.EQUALS + sq.VARCHAR + exercicio.getExercicio() + sq.VARCHAR + sq.COMMA +	
		   colunas.SERIE + sq.EQUALS + sq.VARCHAR + exercicio.getSerie() + sq.VARCHAR + sq.COMMA +
		   colunas.REPETICAO + sq.EQUALS + sq.VARCHAR + exercicio.getRepeticao() + sq.VARCHAR + sq.COMMA +
		   colunas.CARGA + sq.EQUALS + sq.VARCHAR + exercicio.getCarga() + sq.VARCHAR + sq.COMMA +
		   colunas.TREINO + sq.EQUALS + sq.VARCHAR + exercicio.getTreino() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
	}	
	@Override
	public Exercicio consulta(String exercicio) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = comandos.SELECT;
			ResultSet resultSet = st.executeQuery(sqlSt);
			return resultSet.first() ? constroiExercicio(resultSet) : null;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		}finally {
			Conexao.fechaConexao();
		}
	}	
	@Override
	public Map<String, Exercicio> consultaTodos() throws ConexaoException, DAOException {
		   Connection conexao = Conexao.abreConexao();
		   Map<String, Exercicio> exercicio = new HashMap<String, Exercicio>();
		   try {
				Statement st = conexao.createStatement();
				ResultSet resultSet = st.executeQuery("select * from exercicio");
				while (resultSet.next()) {
					exercicio.put(resultSet.getString(colunas.EXERCICIO.toString()), constroiExercicio(resultSet));
				}
				return exercicio;
		   } catch (SQLException e) {
				throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		   } finally {
				Conexao.fechaConexao();
		   }
	}
	@Override
	public List<Exercicio> consultaFaixa(String... codigos) throws ConexaoException, DAOException {
		List<Exercicio> exercicio = new ArrayList<Exercicio>();
		for (String i : codigos) {
			exercicio.add(consulta(i));
		}
		return exercicio;
	}	
	@Override
	public boolean insere(Exercicio exercicio) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			st.execute(constroiInsert(exercicio));
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
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
	public List<Exercicio> insereVarios(List<Exercicio> objetos) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		Exercicio naoInserido = new Exercicio();
		List<Exercicio> naoInseridos = new ArrayList<Exercicio>();
		try {
			for (Exercicio exercicio : objetos) {
				Statement st = conexao.createStatement();
				st.execute(constroiInsert(exercicio));
				naoInserido = exercicio;
			}
		} catch (SQLException e) {
			naoInseridos.add(naoInserido);
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;		
	}
	
	@Override
	public boolean altera(Exercicio objeto) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			String update = constroiUpdate(objeto);
			Statement st = conexao.createStatement();
			st.execute(update);		
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}
	
	@Override
	public boolean exclui(String exercicio) throws ConexaoException, DAOException {
		if (consulta(exercicio) instanceof Exercicio) {
			Connection conexao = Conexao.abreConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.DELETE);
			} catch (SQLException e) {
				throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
			} finally {
				Conexao.fechaConexao();
			}
		}
		return true;
	}
}