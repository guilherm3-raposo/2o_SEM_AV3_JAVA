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
import edu.senai.integrador.beans.Musculacao;
import edu.senai.integrador.beans.exception.MusculacaoException;
import edu.senai.integrador.dao.sql.ColunasMusculacao;
import edu.senai.integrador.dao.sql.MusculacaoComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class MusculacaoDAO implements ICRUDPadraoDAO<Musculacao, String> {
	private SqlSintaxe sq = new SqlSintaxe();
	private MusculacaoComandos comandos = new MusculacaoComandos();
	private SqlTabelas tabelas = new SqlTabelas();
	private ColunasMusculacao colunas = new ColunasMusculacao();
	
	private Musculacao constroiMusculacao(ResultSet rs) {
		
		try {
	Musculacao musculacao = new Musculacao(	rs.getString(colunas.TREINO),
											rs.getString(colunas.OBJETIVO),
											rs.getString(colunas.SESSAO),
											rs.getString(colunas.OBSERVACAO),
											rs.getString(colunas.CPF_ALUNO));
		return musculacao;
		} catch (SQLException | MusculacaoException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	private String constroiInsert (Musculacao musculacao){
		return comandos.INSERT    + 
				sq.VARCHAR + musculacao.getTreino()     + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + musculacao.getObjetivo()   + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + musculacao.getSessao()     + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + musculacao.getObservacao() + sq.VARCHAR + sq.COMMA +
				musculacao.getCpfaluno() + sq.CLOSE_PAR + sq.SEMI_COLON;	    	
	}
	
	private String constroiUpdate(Musculacao musculacao){
		return 	sq.UPDATE 	  +
		   tabelas.MUSCULACAO +
				sq.SET 		  +
		   colunas.TREINO 	  +	sq.EQUALS + sq.VARCHAR + musculacao.getTreino()	    + sq.VARCHAR + sq.COMMA +	
		   colunas.OBJETIVO	  +	sq.EQUALS + sq.VARCHAR + musculacao.getObjetivo()	+ sq.VARCHAR + sq.COMMA +
		   colunas.SESSAO     +	sq.EQUALS + sq.VARCHAR + musculacao.getSessao()	    + sq.VARCHAR + sq.COMMA +
		   colunas.OBSERVACAO +	sq.EQUALS + sq.VARCHAR + musculacao.getObservacao()	+ sq.VARCHAR + sq.COMMA +
		   colunas.CPF_ALUNO   + sq.EQUALS + sq.VARCHAR + musculacao.getCpfaluno()	+ sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
	}
	
	@Override
	public Musculacao consulta(String musculacao) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = comandos.SELECT;
			ResultSet rs = st.executeQuery(sqlSt);
			return rs.first() ? constroiMusculacao(rs) : null;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		}finally {
			Conexao.fechaConexao();
		}
		
	}

	@Override
	public Map<String, Musculacao> consultaTodos() throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		Map<String, Musculacao> musculacao = new HashMap<String, Musculacao>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("select * from musculacao");
			while (rs.next()) {
				musculacao.put(rs.getString(colunas.CPF_ALUNO), constroiMusculacao(rs));
			}
			return musculacao;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Musculacao> consultaFaixa(String... codigos) throws ConexaoException, DAOException {
		List<Musculacao> musculacao = new ArrayList<Musculacao>();
		for (String i : codigos) {
			musculacao.add(consulta(i));
		}
		return musculacao;
	}

	@Override
	public boolean insere(Musculacao musculacao) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			st.execute(constroiInsert(musculacao));
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public List<Musculacao> insereVarios(Map<String, Musculacao> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Musculacao> insereVarios(List<Musculacao> objetos) throws ConexaoException, DAOException {
		Connection conexao = Conexao.abreConexao();
		Musculacao naoInserido = new Musculacao();
		List<Musculacao> naoInseridos = new ArrayList<Musculacao>();
		try {
			for (Musculacao musculacao : objetos) {
				Statement st = conexao.createStatement();
				st.execute(constroiInsert(musculacao));
				naoInserido = musculacao;
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
	public boolean altera(Musculacao objeto) throws ConexaoException, DAOException {
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
	public boolean exclui(String musculacao) throws ConexaoException, DAOException {
		if (consulta(musculacao) instanceof Musculacao) {
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
