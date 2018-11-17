package edu.senai.integrador.dao.sql;

public class SqlTabelas {
	String[] tags = LeXml.getTag("tabelas", "configBanco.xml");
	
	public final String CONTATO		  = (tags[0] + " ");
	public final String ENDERECO	  = (tags[1] + " ");
	public final String PESSOA		  = (tags[2] + " ");
	public final String ALUNO		  = (tags[3] + " ");
	public final String FUNCIONARIO	  = (tags[4] + " ");
	public final String TURMA		  = (tags[5] + " ");
	public final String MODALIDADE	  = (tags[6] + " ");
	public final String PARTICIPANTES = (tags[7] + " ");
}
