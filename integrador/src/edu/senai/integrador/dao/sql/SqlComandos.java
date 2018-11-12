package edu.senai.integrador.dao.sql;

public class SqlComandos {
	public final String INSERT_PESSOA		= (LeXml.getTag("comandos")[0]);
	public final String INSERT_ALUNO		= (LeXml.getTag("comandos")[1]);
	public final String INSERT_FUNC			= (LeXml.getTag("comandos")[2]);
	public final String SELECT_ALUNOS		= (LeXml.getTag("comandos")[3]);
	public final String SELECT_ALUNO		= (LeXml.getTag("comandos")[4]);
	public final String SELECT_FUNCIONARIOS	= (LeXml.getTag("comandos")[5]);
	public final String SELECT_FUNCIONARIO	= (LeXml.getTag("comandos")[6]);
	public final String SELECT_MINISTRANTES	= (LeXml.getTag("comandos")[7]);
	public final String SELECT_PARTICIPANTES= (LeXml.getTag("comandos")[8]);
	public final String SELECT_MODALIDADE	= (LeXml.getTag("comandos")[9]);
	public final String SELECT_TURMAS 		= (LeXml.getTag("comandos")[10]);
	public final String SELECT_TURMA 		= (LeXml.getTag("comandos")[11]);
}
