package edu.senai.integrador.dao;

import java.sql.Date;
import java.sql.ResultSet;

import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.dao.sql.SqlComandos;
import edu.senai.integrador.dao.sql.SqlSintaxe;

public class PessoaDAO {
	private static SqlComandos comandos = new SqlComandos();
	private static SqlSintaxe sq = new SqlSintaxe();
	
	public static Object constroiPessoa (ResultSet rs) {
		Object pessoa = null;
		
		
		return pessoa;
	}
	
	public static String constroiInsert (Aluno aluno) {
		String insertPessoa = comandos.INSERT_PESSOA + 
							  aluno.getCPF() + sq.VARCHAR + sq.COMMA + sq.VARCHAR +
							  aluno.getNome() + sq.VARCHAR + sq.COMMA + 
							  aluno.getEstadoCivil().ordinal() + sq.COMMA + 
							  aluno.getSexo().ordinal() + sq.COMMA + sq.VARCHAR +
				 Date.valueOf(aluno.getDataDeNascimento()) + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;;
		return insertPessoa;
		
	}
	
	public static String constroiInsert (Funcionario funcionario) {
		String insertPessoa = comandos.INSERT_PESSOA + 
							  funcionario.getCPF() + sq.VARCHAR + sq.COMMA + sq.VARCHAR +
							  funcionario.getNome() + sq.VARCHAR + sq.COMMA + 
							  funcionario.getEstadoCivil().ordinal() + sq.COMMA + 
							  funcionario.getSexo().ordinal() + sq.COMMA + sq.VARCHAR +
				 Date.valueOf(funcionario.getDataDeNascimento()) + sq.VARCHAR + sq.CLOSE_PAR + sq.SEMI_COLON;;
		return insertPessoa;
		
	}

}
