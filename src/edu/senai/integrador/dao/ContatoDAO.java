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
import edu.senai.integrador.beans.cadastro.Contato;
import edu.senai.integrador.beans.cadastro.ContatoException;
import edu.senai.integrador.dao.sql.ColunasContato;
import edu.senai.integrador.dao.sql.ComandosContato;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;
import edu.senai.integrador.logs.GeraLog;

public class ContatoDAO implements ICRUDPadraoDAO<Contato, String> {
	private SqlSintaxe sq = new SqlSintaxe();
	private SqlTabelas tabelas = new SqlTabelas();
	private ColunasContato colunas = new ColunasContato();
	private ComandosContato comandos = new ComandosContato();
	
	private Contato constroiContato(ResultSet rs) throws DAOException, ContatoException, SQLException {
		return new Contato(rs.getString(colunas.CPF),
						   rs.getString(colunas.FIXO),
						   rs.getString(colunas.MOVEL),
						   rs.getString(colunas.EMAIL));
	}
	
	private String constroiInsert(Contato contato) throws ContatoException {
		String insert = new String();
		insert = comandos.INSERT + 
	 sq.VARCHAR + contato.getCPF() + sq.VARCHAR + sq.COMMA + 
	 sq.VARCHAR + contato.getTelefoneFixo() + sq.VARCHAR + sq.COMMA + 
	 sq.VARCHAR + contato.getTelefoneMovel() + sq.VARCHAR + sq.COMMA +
	 sq.VARCHAR + contato.getEmail() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
		return insert;
	}
	
	private String constroiUpdate(Contato contato) throws ContatoException {
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
	public Contato consulta(String codigo) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			PreparedStatement pst = conexao.prepareStatement(comandos.SELECT);
			pst.setString(1, codigo);
			ResultSet rs = pst.executeQuery();
			if (rs.first())
				return constroiContato(rs);
		} catch (SQLException | ContatoException | DAOException e) {
			throw new DAOException(EDAOErros.SQL_INVALIDO);
		} finally {
			Conexao.fechaConexao();
		}
		return null;
	}
	
	@Override
	public Map<String, Contato> consultaTodos() throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Map<String, Contato> contatos = new HashMap<String, Contato>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(comandos.SELECT);
			while(rs.next()) {
				contatos.put("\n"+rs.getString(colunas.CPF), constroiContato(rs));
			}
			return contatos;
		} catch (SQLException | DAOException | ContatoException e) {
			throw new DAOException(EDAOErros.SQL_INVALIDO);
		} finally {
			Conexao.fechaConexao();
		}
	}
	
	@Override
	public List<Contato> consultaFaixa(String... faixa) throws ConexaoException, DAOException, SQLException {
		List<Contato> contatos = new ArrayList<Contato>();
		for (String i : faixa) {
			contatos.add(consulta(i));
		}
		return contatos;
	}
	
	@Override
	public boolean insere(Contato contato) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao(); 
		try {
			Statement st = conexao.createStatement();
			String insert = constroiInsert(contato);
			st.execute(insert);
			return true;
		} catch(SQLException e) {
			if(e.getErrorCode() == Integer.valueOf(sq.DUPLICATE_PK))
				throw new DAOException(EDAOErros.REGISTRO_INATIVO);
		} catch (ContatoException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return false;
	}

	@Override
	public List<Contato> insereVarios(Map<String, Contato> contatos) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
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
				} catch (ContatoException e) {
					new GeraLog().escreveLogArquivo(e);
				}
			});
		} catch (Exception e) {
			throw new DAOException(EDAOErros.INSERE_DADO);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public List<Contato> insereVarios(List<Contato> contatos) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
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
			throw new DAOException(EDAOErros.INSERE_DADO);
		} catch (ContatoException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public boolean altera(Contato contato) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			String update = constroiUpdate(contato);
			Statement st = conexao.createStatement();
			st.execute(update);
		} catch (SQLException e) {
			throw new DAOException(EDAOErros.ALTERA_DADO);
		} catch (ContatoException e) {
			new GeraLog().escreveLogArquivo(e);
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
