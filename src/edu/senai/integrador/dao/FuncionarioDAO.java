package edu.senai.integrador.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.pessoa.EEscolaridade;
import edu.senai.integrador.beans.pessoa.EEstadoCivil;
import edu.senai.integrador.beans.pessoa.ESexo;
import edu.senai.integrador.beans.pessoa.Funcionario;
import edu.senai.integrador.beans.pessoa.FuncionarioException;
import edu.senai.integrador.beans.pessoa.PessoaException;
import edu.senai.integrador.dao.sql.ColunasFuncionario;
import edu.senai.integrador.dao.sql.ComandosFuncionario;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;
import edu.senai.integrador.logs.GeraLog;

public class FuncionarioDAO implements ICRUDPadraoDAO<Funcionario, String> {
	private SqlSintaxe sq = new SqlSintaxe();
	private SqlTabelas tabelas = new SqlTabelas();
	private ColunasFuncionario colunas = new ColunasFuncionario();
	private ComandosFuncionario comandos = new ComandosFuncionario();

	private Funcionario constroiFuncionario(ResultSet rs) {
			try {
				return new Funcionario(			rs.getString(colunas.CPF), 
									   			rs.getString(colunas.CTPS),
		EEscolaridade.values()[Integer.parseInt(rs.getString(colunas.ESCOLARIDADE))],
												rs.getString(colunas.NOME), 
								LocalDate.parse(rs.getString(colunas.DATA_NASC)),
				ESexo.values()[Integer.parseInt(rs.getString(colunas.SEXO))],
         EEstadoCivil.values()[Integer.parseInt(rs.getString(colunas.ESTAD0_CIVIL))],
												rs.getBoolean(colunas.ATIVO));
			} catch (NumberFormatException | FuncionarioException | PessoaException | SQLException e) {
				new GeraLog().escreveLogArquivo(e);
			}
			return null;
	}

	private String[] constroiInsert (Funcionario funcionario) throws PessoaException, FuncionarioException{
		String[] insert = new String[2];
		insert[0] = sq.INSERT + 
					sq.INTO + 
			   tabelas.PESSOA + sq.OPEN_PAR + 
			   colunas.CPF + sq.COMMA +  
			   colunas.NOME + sq.COMMA +
			   colunas.ESTAD0_CIVIL + sq.COMMA +
			   colunas.SEXO + sq.COMMA +
			   colunas.DATA_NASC + sq.COMMA +
			   colunas.ATIVO + sq.CLOSE_PAR + " "+ 
			   		sq.VALUES + sq.OPEN_PAR + sq.VARCHAR + 
		   funcionario.getCPF() + sq.VARCHAR + sq.COMMA + sq.VARCHAR + 
		   funcionario.getNome()+ sq.VARCHAR + sq.COMMA +
		   funcionario.getEstadoCivil().ordinal() + sq.COMMA + 
		   funcionario.getSexo().ordinal() + sq.COMMA + sq.VARCHAR + Date.valueOf(
		   funcionario.getDataDeNascimento()) + sq.VARCHAR + sq.COMMA +
   		  (funcionario.isAtivo() ? 1 : 0) + sq.CLOSE_PAR + sq.SEMI_COLON;
		insert[1] = sq.INSERT + 
					sq.INTO +
			   tabelas.FUNCIONARIO + sq.OPEN_PAR +
			   colunas.CPF + sq.COMMA +
			   colunas.ESCOLARIDADE + sq.COMMA +
			   colunas.CTPS + sq.CLOSE_PAR + " " +
			   		sq.VALUES + sq.OPEN_PAR + sq.VARCHAR + 
		   funcionario.getCPF() + sq.VARCHAR + sq.COMMA +
		   funcionario.getEscolaridade().ordinal() + sq.COMMA + sq.VARCHAR + 
		   funcionario.getCtps() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
		return insert;
	}
	
	private String[] constroiUpdate(Funcionario funcionario) throws FuncionarioException, PessoaException {
		String[] update = { sq.UPDATE + " ", sq.UPDATE + " " };
		
		update[0] += tabelas.FUNCIONARIO + 
						  sq.SET + 
					 colunas.ESCOLARIDADE + 
					      sq.EQUALS + 
				 funcionario.getEscolaridade().ordinal() + sq.COMMA + 
					 colunas.CTPS + 
					 	  sq.EQUALS + sq.VARCHAR +
				 funcionario.getCtps() + sq.VARCHAR + " " +
						  sq.WHERE + 
					 colunas.CPF + 
					 	  sq.EQUALS + sq.VARCHAR + 
				 funcionario.getCPF() + sq.VARCHAR + sq.SEMI_COLON;
		
		update[1] += tabelas.PESSOA + 
						  sq.SET + 
					 colunas.NOME + 
					 	  sq.EQUALS + sq.VARCHAR + 
				 funcionario.getNome() + sq.VARCHAR + sq.COMMA + 
				 	 colunas.DATA_NASC + 
				 	 	  sq.EQUALS + sq.VARCHAR + 
	Date.valueOf(funcionario.getDataDeNascimento()) + sq.VARCHAR + sq.COMMA + 
			    	 colunas.ESTAD0_CIVIL + sq.EQUALS + 
			     funcionario.getEstadoCivil().ordinal() + sq.COMMA + 
			     	 colunas.SEXO + 
			     	 	  sq.EQUALS + 
			     funcionario.getSexo().ordinal() + sq.COMMA +
			     	 colunas.ATIVO +
			     	 	  sq.EQUALS +
			    (funcionario.isAtivo() ? 1 : 0) +
					 	  sq.WHERE + 
					 colunas.CPF + 
					 	  sq.EQUALS + sq.VARCHAR + 
				 funcionario.getCPF() + sq.VARCHAR + sq.SEMI_COLON;
		return update;
	}
	
	@Override
	public Funcionario consulta(String funcionario) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			PreparedStatement pst = conexao.prepareStatement(comandos.SELECT);
			pst.setString(1, funcionario);
			ResultSet rs = pst.executeQuery();
			return rs.first() ? constroiFuncionario(rs) : null;
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public Map<String, Funcionario> consultaTodos() throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Map<String, Funcionario> pessoas = new HashMap<String, Funcionario>();
		Statement st = conexao.createStatement();
		ResultSet rs = st.executeQuery(comandos.SELECT_ALL);
		while (rs.next()) {
			pessoas.put(rs.getString(colunas.CPF), constroiFuncionario(rs));
		}
		Conexao.fechaConexao();
		return pessoas;
	}

	@Override
	public List<Funcionario> consultaFaixa(String... faixa) throws ConexaoException, DAOException, SQLException {
		List<Funcionario> pessoas = new ArrayList<Funcionario>();
		for (String i : faixa) {
			pessoas.add(consulta(i));
		}
		return pessoas;
	}

	@Override
	public boolean insere(Funcionario funcionario) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();

		try {
			Statement st = conexao.createStatement();
			String[] insert = constroiInsert(funcionario);
			st.execute(insert[0]);
			st.execute(insert[1]);
		} catch (PessoaException | FuncionarioException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public List<Funcionario> insereVarios(List<Funcionario> funcionarios) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		Funcionario naoInserido = new Funcionario();
		List<Funcionario> naoInseridos = new ArrayList<Funcionario>();
		try {
			for (Funcionario funcionario : funcionarios) {
				Statement st = conexao.createStatement();
				String[] insert = constroiInsert(funcionario);
				st.execute(insert[0]);
				st.execute(insert[1]);
				naoInserido = funcionario;
			}
		} catch (SQLException e) {
			naoInseridos.add(naoInserido);
			throw new DAOException(EDAOErros.INSERE_DADO);
		} catch (PessoaException | FuncionarioException e) {
			new GeraLog().escreveLogArquivo(e);
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public List<Funcionario> insereVarios(Map<String, Funcionario> funcionarios) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		List<Funcionario> naoInseridos = new ArrayList<Funcionario>();
		funcionarios.forEach((cpf, funcionario) -> {
			Funcionario naoInserido = new Funcionario();
			Statement st;
			try {
				st = conexao.createStatement();
				String[] insert = constroiInsert(funcionario);
				st.execute(insert[0]);
				st.execute(insert[1]);
				naoInserido = funcionario;
			} catch (SQLException e) {
				naoInseridos.add(naoInserido);
			} catch (FuncionarioException | PessoaException e) {
				new GeraLog().escreveLogArquivo(e);
			}
		});
		Conexao.fechaConexao();
		return naoInseridos;
	}

	@Override
	public boolean altera(Funcionario funcionario) throws ConexaoException, DAOException, SQLException {
		Connection conexao = Conexao.abreConexao();
		try {
			String[] update = constroiUpdate(funcionario);
			Statement st = conexao.createStatement();
			st.execute(update[0]);
			st.execute(update[1]);
			Conexao.fechaConexao();
			return true;
		} catch (FuncionarioException | PessoaException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return false;
	}

	@Override
	public boolean exclui(String codigo) throws ConexaoException, DAOException, SQLException {
		if (consulta(codigo) instanceof Funcionario) {
			Connection conexao = Conexao.abreConexao();
			Statement st = conexao.createStatement();
			st.execute(sq.UPDATE +
				  tabelas.PESSOA + 
					   sq.SET +
				  colunas.ATIVO + sq.EQUALS +
					  	  0 +
					   sq.WHERE +
				  colunas.CPF + sq.EQUALS + 
			 sq.VARCHAR + codigo + sq.VARCHAR + sq.SEMI_COLON);
			Conexao.fechaConexao();
		}
		return true;
	}

//	@Override
	public boolean exclui(Funcionario funcionario) throws ConexaoException, DAOException, SQLException {
		try {
			return exclui(funcionario.getCPF());
		} catch (PessoaException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return false;
	}
}
