package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class SqlComandos {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	String[] tags = LeXml.getTag("comandos", "/bancoConfiguracoes.xml");
	
	public final String INSERT_PESSOA		= (tags[0]);
	public final String INSERT_ALUNO		= (tags[1]);
	public final String INSERT_CONTATO		= (tags[2]);
	public final String INSERT_FUNC			= (tags[3]);
	public final String INSERT_MODALIDADE   = (tags[4]);
	public final String SELECT_ALUNOS		= (tags[5]);
	public final String SELECT_ALUNO		= (tags[6]);
	public final String SELECT_CONTATO		= (tags[7]);
	public final String SELECT_CONTATOS 	= (tags[8]);
	public final String SELECT_FUNCIONARIOS	= (tags[9]);
	public final String SELECT_FUNCIONARIO	= (tags[10]);
	public final String SELECT_MINISTRANTES	= (tags[11]);
	public final String SELECT_PARTICIPANTES= (tags[12]);
	public final String SELECT_MODALIDADE	= (tags[13]);
	public final String SELECT_TURMAS 		= (tags[14]);
	public final String SELECT_TURMA 		= (tags[15]);
}
