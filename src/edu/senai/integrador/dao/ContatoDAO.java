package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Contato;
import edu.senai.integrador.beans.exception.ContatoException;
import edu.senai.integrador.dao.sql.ColunasContato;
import edu.senai.integrador.dao.sql.SqlComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class ContatoDAO implements ICRUDPadraoDAO<Contato, String> {
	SqlSintaxe sq = new SqlSintaxe();
	SqlComandos comandos = new SqlComandos();
	SqlTabelas tabelas = new SqlTabelas();
	ColunasContato colunas = new ColunasContato();
	
	private Contato constroiContato(ResultSet rs) throws DAOException, ContatoException {
		try {
			return new Contato(rs.getString(colunas.CPF),rs.getString(colunas.FIXO),rs.getString(colunas.MOVEL),rs.getString(colunas.EMAIL));
		} catch(SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		}
	}
	
	private String constroiInsert(Contato contato) {
		String insert = new String();
		insert = comandos.INSERT_CONTATO + 
	 sq.VARCHAR + contato.getCPF() + sq.VARCHAR + sq.COMMA + 
	 sq.VARCHAR + contato.getTelefoneFixo() + sq.VARCHAR + sq.COMMA + 
	 sq.VARCHAR + contato.getTelefoneMovel() + sq.VARCHAR + sq.COMMA +
	 sq.VARCHAR + contato.getEmail() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
		return insert;
	}
	
	private String constroiUpdate(Contato contato) {
		String update = new String();
		update = sq.UPDATE + 
			tabelas.CONTATO + 
				 sq.SET + 
			colunas.FIXO + 
				 sq.EQUALS + sq.VARCHAR + 
		    contato.getTelefoneFixo() +  sq.VARCHAR + sq.COMMA + 
			colunas.MOVEL + 
				 sq.EQUALS + sq.VARCHAR + 
			contato.getTelefoneMovel() + sq.VARCHAR + sq.COMMA +
			colunas.EMAIL + sq.EQUALS + sq.VARCHAR + 
			contato.getEmail() + sq.VARCHAR +
				 sq.WHERE + 
			colunas.CPF + 
				 sq.EQUALS + sq.VARCHAR + 
			contato.getCPF() + sq.VARCHAR + sq.SEMI_COLON;
		return update;
	}
	
	@Override
	public Contato consulta(String codigo) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement pst = conexao.prepareStatement(comandos.SELECT_CONTATO);
			pst.setString(1, codigo);
			ResultSet rs = pst.executeQuery();
			if (rs.first())
				return constroiContato(rs);
		} catch (SQLException | ContatoException | DAOException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return null;
	}
	
	@Override
	public Map<String, Contato> consultaTodos() throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Map<String, Contato> contatos = new HashMap<String, Contato>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(comandos.SELECT_CONTATOS);
			while(rs.next()) {
				contatos.put("\n"+rs.getString(colunas.CPF), constroiContato(rs));
			}
			return contatos;
		} catch (SQLException | DAOException | ContatoException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}
	
	@Override
	public List<Contato> consultaFaixa(String... faixa) throws ConexaoException, DAOException {
		List<Contato> contatos = new ArrayList<Contato>();
		for (String i : faixa) {
			contatos.add(consulta(i));
		}
		return contatos;
	}
	
	@Override
	public boolean insere(Contato contato) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao(); 
		try {
			Statement st = conexao.createStatement();
			String insert = constroiInsert(contato);
			st.execute(insert);
			return true;
		} catch(SQLException e) {
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Contato> insereVarios(Map<String, Contato> contatos) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		List<Contato> naoInseridos = new ArrayList<Contato>();
		try {
			contatos.forEach((cpf,contato) -> {
				Contato naoInserido = new Contato();
				Statement st;
				try {
					st = conexao.createStatement();
					String insert = constroiInsert(contato);
					st.execute(insert);
					naoInserido = contato;
				} catch (SQLException e) {
					naoInseridos.add(naoInserido);
				}
			});
		} catch (Exception e) {
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public List<Contato> insereVarios(List<Contato> contatos) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Contato naoInserido = new Contato();
		List<Contato> naoInseridos = new ArrayList<Contato>();
		try {
			for (Contato contato : contatos) {
				Statement st = conexao.createStatement();
				String insert = constroiInsert(contato);
				st.execute(insert);
				naoInserido = contato;
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
	public boolean altera(Contato contato) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		String update = constroiUpdate(contato);
		try {
			Statement st = conexao.createStatement();
			st.execute(update);
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.ALTERA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public boolean exclui(String cpf) throws ConexaoException, DAOException {
		// DO NOT EXCLUI PORRA! KAKA
		return false;
	}
}
