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
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.beans.enumeradores.EEscolaridade;
import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.dao.sql.ColunasFuncionario;
import edu.senai.integrador.dao.sql.SqlComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;
import edu.senai.integrador.dao.sql.SqlTabelas;

public class FuncionarioDAO implements ICRUDPadraoDAO<Funcionario, String> {
	private SqlSintaxe sq = new SqlSintaxe();
	private SqlComandos comandos = new SqlComandos();
	private SqlTabelas tabelas = new SqlTabelas();
	private ColunasFuncionario colunas = new ColunasFuncionario();

	private Funcionario constroiFuncionario(ResultSet rs) throws DAOException{
		try {
			return new Funcionario(			rs.getString(colunas.CPF.toString()), 
								   			rs.getString(colunas.CTPS.toString()),
    EEscolaridade.values()[Integer.parseInt(rs.getString(colunas.ESCOLARIDADE.toString()))],
											rs.getString(colunas.NOME.toString()), 
							LocalDate.parse(rs.getString(colunas.DATA_NASC.toString())),
			ESexo.values()[Integer.parseInt(rs.getString(colunas.SEXO.toString()))],
	 EEstadoCivil.values()[Integer.parseInt(rs.getString(colunas.ESTAD0_CIVIL.toString()))]);
		} catch (PessoaException | FuncionarioException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} catch (NumberFormatException e) {
			throw new DAOException(EDaoErros.NUMERO_INVALIDO, e.getMessage(), this.getClass());
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.SQL_INVALIDO, e.getMessage(), this.getClass());
		}
	}

	private String[] constroiInsert (Funcionario funcionario){
		String[] insert = new String[2];
		insert[0] = comandos.INSERT_PESSOA.toString() + 
			    sq.VARCHAR + funcionario.getCPF() + sq.VARCHAR + sq.COMMA +
			    sq.VARCHAR + funcionario.getNome()+ sq.VARCHAR + sq.COMMA +
						     funcionario.getEstadoCivil().ordinal() + sq.COMMA + 
						     funcionario.getSexo().ordinal() + sq.COMMA + 
   sq.VARCHAR + Date.valueOf(funcionario.getDataDeNascimento()) + sq.VARCHAR + sq.COMMA +
   							(funcionario.isAtivo() ? 1 : 0) + sq.CLOSE_PAR + sq.SEMI_COLON;
		
		insert[1] = comandos.INSERT_FUNC.toString() +  
				sq.VARCHAR + funcionario.getCPF() + sq.VARCHAR + sq.COMMA +
									  funcionario.getEscolaridade().ordinal() + sq.COMMA + 
				sq.VARCHAR + funcionario.getCtps() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
		return insert;
	}
	
	private String[] constroiUpdate(Funcionario funcionario) {
		String[] update = { sq.UPDATE + " ", sq.UPDATE + " " };
		
		update[0] += tabelas.FUNCIONARIO.toString() + 
					 sq.SET + 
				colunas.ESCOLARIDADE + sq.EQUALS + funcionario.getEscolaridade().ordinal() + sq.COMMA + 
				colunas.CTPS + sq.EQUALS + funcionario.getCtps() + 
					 sq.WHERE + colunas.CPF + sq.EQUALS + sq.VARCHAR + funcionario.getCPF() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;
		
		update[1] += tabelas.PESSOA.toString() + sq.SET + 
			    colunas.NOME + sq.EQUALS + sq.VARCHAR + funcionario.getNome() + sq.VARCHAR + sq.COMMA + 
			    colunas.DATA_NASC + sq.EQUALS + sq.VARCHAR + Date.valueOf(funcionario.getDataDeNascimento()) + sq.VARCHAR + sq.COMMA + 
			    colunas.ESTAD0_CIVIL + sq.EQUALS + funcionario.getEstadoCivil().ordinal() + sq.COMMA + 
			    colunas.SEXO + sq.EQUALS + funcionario.getSexo().ordinal() + 
					 sq.WHERE + colunas.CPF + sq.EQUALS + sq.VARCHAR + 
					 funcionario.getCPF() + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;

		return update;
	}
	
	@Override
	public Funcionario consulta(String funcionario) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement pst = conexao.prepareStatement(comandos.SELECT_FUNCIONARIO.toString());
			pst.setString(1, funcionario);
			ResultSet rs = pst.executeQuery();
			return rs.first() ? constroiFuncionario(rs) : null;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public Map<String, Funcionario> consultaTodos() throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		Map<String, Funcionario> pessoas = new HashMap<String, Funcionario>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery(comandos.SELECT_FUNCIONARIOS.toString());
			while (rs.next()) {
				pessoas.put(rs.getString(colunas.CPF.toString()), constroiFuncionario(rs));
			}
			return pessoas;
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
	}

	@Override
	public List<Funcionario> consultaFaixa(String... faixa) throws ConexaoException, DAOException {
		List<Funcionario> pessoas = new ArrayList<Funcionario>();
		for (String i : faixa) {
			pessoas.add(consulta(i));
		}
		return pessoas;
	}

	@Override
	public boolean insere(Funcionario funcionario) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();

		try {
			Statement st = conexao.createStatement();
			String[] insert = constroiInsert(funcionario);
			st.execute(insert[0]);
			st.execute(insert[1]);
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.CONSULTA_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public List<Funcionario> insereVarios(List<Funcionario> funcionarios) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
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
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return naoInseridos;
	}

	@Override
	public List<Funcionario> insereVarios(Map<String, Funcionario> funcionarios) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		List<Funcionario> naoInseridos = new ArrayList<Funcionario>();
		try {
			funcionarios.forEach((cpf,funcionario) -> {
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
	public boolean insereVariosTransacao(List<Funcionario> objetos) throws ConexaoException, DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean altera(Funcionario funcionario) throws ConexaoException, DAOException {
		Connection conexao = Conexao.getConexao();
		String[] update = constroiUpdate(funcionario);
		try {
			Statement st = conexao.createStatement();
			st.execute(update[0]);
			st.execute(update[1]);
		} catch (SQLException e) {
			throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
		} finally {
			Conexao.fechaConexao();
		}
		return true;
	}

	@Override
	public boolean exclui(String codigo) throws ConexaoException, DAOException {
		if (consulta(codigo) instanceof Funcionario) {
			Connection conexao = Conexao.getConexao();
			try {
				Statement st = conexao.createStatement();
				st.execute(sq.UPDATE +
					  tabelas.PESSOA + 
						   sq.SET +
					  colunas.ATIVO + sq.EQUALS +
						  	  0 +
						   sq.WHERE +
					  colunas.CPF + sq.EQUALS + 
				 sq.VARCHAR + codigo + sq.VARCHAR + sq.SEMI_COLON);
			} catch (SQLException e) {
				throw new DAOException(EDaoErros.INSERE_DADO, e.getMessage(), this.getClass());
			} finally {
				Conexao.fechaConexao();
			}
		}
		return true;
	}

//	@Override
	public boolean exclui(Funcionario funcionario) throws ConexaoException, DAOException {
		return exclui(funcionario.getCPF());
	}
}
