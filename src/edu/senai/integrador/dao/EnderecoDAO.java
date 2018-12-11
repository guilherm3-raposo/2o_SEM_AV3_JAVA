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
import edu.senai.integrador.beans.cadastro.EEndereco;
import edu.senai.integrador.beans.cadastro.Endereco;
import edu.senai.integrador.beans.cadastro.EnderecoException;
import edu.senai.integrador.dao.sql.ColunasEndereco;
import edu.senai.integrador.dao.sql.ComandosEndereco;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;
import edu.senai.integrador.logs.GeraLog;

public class EnderecoDAO implements ICRUDPadraoDAO<Endereco, String> {
	private SqlSintaxe sq = new SqlSintaxe();
	private ComandosEndereco comandos = new ComandosEndereco();
	private SqlTabelas tabelas = new SqlTabelas();
	private ColunasEndereco colunas = new ColunasEndereco();
	

	private Endereco constroiEndereco(ResultSet rs) throws EnderecoException {
		try {
			Endereco endereco = new Endereco(rs.getString(colunas.CPF),
						  EEndereco.values()[rs.getInt(colunas.LOGRADOURO)],
							 				 rs.getString(colunas.VIA),
											 rs.getInt(colunas.NUMERO),
											 rs.getString(colunas.COMPLEMENTO),
											 rs.getString(colunas.CIDADE),
											 rs.getString(colunas.ESTADO),
											 rs.getString(colunas.CEP));
			return endereco;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	private String constroiInsert (Endereco endereco) throws EnderecoException{
		return comandos.INSERT + 
				sq.VARCHAR + endereco.getCpf() + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + endereco.getLogradouro().getSigla() + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + endereco.getVia() + sq.VARCHAR + sq.COMMA +
							 endereco.getNumero() + sq.COMMA +
				sq.VARCHAR + endereco.getComplemento() + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + endereco.getCidade() + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + endereco.getEstado() + sq.VARCHAR + sq.COMMA +
				sq.VARCHAR + endereco.getCep() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;	    	
	}
	
	private String constroiUpdate(Endereco endereco) throws EnderecoException {
		return 	sq.UPDATE 	  +
		   tabelas.ENDERECO   +
				sq.SET 		  +
		   colunas.CPF 		  +	sq.EQUALS + sq.VARCHAR + endereco.getCpf()	+ sq.VARCHAR + sq.COMMA +	
		   colunas.VIA 		  +	sq.EQUALS + sq.VARCHAR + endereco.getVia()	+ sq.VARCHAR + sq.COMMA +
		   colunas.NUMERO     +	sq.EQUALS + sq.VARCHAR + endereco.getNumero()	+ sq.VARCHAR + sq.COMMA +
		   colunas.COMPLEMENTO+	sq.EQUALS + sq.VARCHAR + endereco.getComplemento()	+ sq.VARCHAR + sq.COMMA +
		   colunas.CIDADE 	  +	sq.EQUALS + sq.VARCHAR + endereco.getCidade()	+ sq.VARCHAR + sq.COMMA +
		   colunas.ESTADO     + sq.EQUALS + sq.VARCHAR + endereco.getEstado()	+ sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
	}

	
	
	@Override
	public Endereco consulta(String codigo) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			String sqlSt = comandos.SELECT;
			ResultSet rs = st.executeQuery(sqlSt);
			return rs.first() ? constroiEndereco(rs) : null;
		} catch (SQLException e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		} catch (EnderecoException e) {
			new GeraLog().escreveLogArquivo(e);
		}finally {
			Conexao.fechaConexao();
		}
		return null;
	}

	@Override
	public Map<String, Endereco> consultaTodos() throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Map<String, Endereco> endereco = new HashMap<String, Endereco>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("select * from endereco");
			while (rs.next()) {
				endereco.put(rs.getString(colunas.CPF.toString()), constroiEndereco(rs));
			}
			return endereco;
		} catch (SQLException e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		} catch (EnderecoException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return null;
	}

	@Override
	public List<Endereco> consultaFaixa(String... codigos) throws ConexaoException, DAOException, SQLException {
		List<Endereco> endereco = new ArrayList<Endereco>();
		for (String i : codigos) {
			endereco.add(consulta(i));
		}
		return endereco;
	}

	@Override
	public boolean insere(Endereco endereco) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			Statement st = conexao.createStatement();
			String linha = constroiInsert(endereco);
			st.execute(linha);
		} catch (SQLException e) {
			throw new DAOException(EDAOErros.CONSULTA_DADO);
		} catch (EnderecoException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public List<Endereco> insereVarios(Map<String, Endereco> enderecos) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		List<Endereco> naoInseridos = new ArrayList<Endereco>();
		try {
			enderecos.forEach((cpf,endereco) -> {
				Endereco naoInserido = new Endereco();
				Statement st;
				try {
					st = conexao.createStatement();
					String insert = constroiInsert(endereco);
					st.execute(insert);
					naoInserido = endereco;
				} catch (SQLException e) {
					naoInseridos.add(naoInserido);
				} catch (EnderecoException e) {
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
	public List<Endereco> insereVarios(List<Endereco> enderecos) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Endereco naoInserido = new Endereco();
		List<Endereco> naoInseridos = new ArrayList<Endereco>();
		try {
			for (Endereco endereco : enderecos) {
				Statement st = conexao.createStatement();
				st.execute(constroiInsert(endereco));
				naoInserido = endereco;
			}
		} catch (SQLException e) {
			naoInseridos.add(naoInserido);
			throw new DAOException(EDAOErros.INSERE_DADO);
		} catch (EnderecoException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public boolean altera(Endereco endereco) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			String update = constroiUpdate(endereco);
			Statement st = conexao.createStatement();
			st.execute(update);		
		} catch (SQLException e) {
			throw new DAOException(EDAOErros.INSERE_DADO);
		} catch (EnderecoException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public boolean exclui(String endereco) throws ConexaoException, DAOException, SQLException {
		if (consulta(endereco) instanceof Endereco) {
			Connection conexao = Conexao.abreConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.DELETE);
			} catch (SQLException e) {
				throw new DAOException(EDAOErros.INSERE_DADO);
			} finally {
				Conexao.fechaConexao();
			}
		}
		return true;
	}
}
