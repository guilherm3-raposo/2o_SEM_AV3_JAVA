package edu.senai.integrador.dao.sql;

public class SqlComandos {
	String[] tags = LeXml.getTag("comandos", "/configBanco.xml");
	
	public final String INSERT_PESSOA		= (tags[0]);
	public final String INSERT_ALUNO		= (tags[1]);
	public final String INSERT_FUNC			= (tags[2]);
	public final String SELECT_ALUNOS		= (tags[3]);
	public final String SELECT_ALUNO		= (tags[4]);
	public final String SELECT_FUNCIONARIOS	= (tags[5]);
	public final String SELECT_FUNCIONARIO	= (tags[6]);
	public final String SELECT_MINISTRANTES	= (tags[7]);
	public final String SELECT_PARTICIPANTES= (tags[8]);
	public final String SELECT_MODALIDADE	= (tags[9]);
	public final String SELECT_TURMAS 		= (tags[10]);
	public final String SELECT_TURMA 		= (tags[11]);
	public final String CONFERE_TURMA 		= (tags[12]);
}
