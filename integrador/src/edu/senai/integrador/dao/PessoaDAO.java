package edu.senai.integrador.dao;

import java.sql.Date;
import java.sql.ResultSet;

import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.dao.sql.EXmlComandos;
import edu.senai.integrador.dao.sql.EXmlSintaxe;

public class PessoaDAO {
	public static Object constroiPessoa (ResultSet rs) {
		Object pessoa = null;
		
		
		return pessoa;
	}
	
	public static String constroiInsert (Aluno aluno) {
		String insertPessoa = EXmlComandos.INSERT_PESSOA + 
							  aluno.getCPF() + EXmlSintaxe.VARCHAR + EXmlSintaxe.COMMA + EXmlSintaxe.VARCHAR +
							  aluno.getNome() + EXmlSintaxe.VARCHAR + EXmlSintaxe.COMMA + 
							  aluno.getEstadoCivil().ordinal() + EXmlSintaxe.COMMA + 
							  aluno.getSexo().ordinal() + EXmlSintaxe.COMMA + EXmlSintaxe.VARCHAR +
				 Date.valueOf(aluno.getDataDeNascimento()) + EXmlSintaxe.VARCHAR + EXmlSintaxe.END_LINE;;
		return insertPessoa;
		
	}
	
	public static String constroiInsert (Funcionario funcionario) {
		String insertPessoa = EXmlComandos.INSERT_PESSOA + 
							  funcionario.getCPF() + EXmlSintaxe.VARCHAR + EXmlSintaxe.COMMA + EXmlSintaxe.VARCHAR +
							  funcionario.getNome() + EXmlSintaxe.VARCHAR + EXmlSintaxe.COMMA + 
							  funcionario.getEstadoCivil().ordinal() + EXmlSintaxe.COMMA + 
							  funcionario.getSexo().ordinal() + EXmlSintaxe.COMMA + EXmlSintaxe.VARCHAR +
				 Date.valueOf(funcionario.getDataDeNascimento()) + EXmlSintaxe.VARCHAR + EXmlSintaxe.END_LINE;;
		return insertPessoa;
		
	}

}
