package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class SqlTabelas {
	Properties prop = Configuracoes.Carrega();
	String[] tags = LeXml.getTag("tabelas", prop.getProperty("configBanco"));
	
	public final String CONTATO		  = (tags[0] + " ");
	public final String ENDERECO	  = (tags[1] + " ");
	public final String PESSOA		  = (tags[2] + " ");
	public final String ALUNO		  = (tags[3] + " ");
	public final String FUNCIONARIO	  = (tags[4] + " ");
	public final String TURMA		  = (tags[5] + " ");
	public final String MODALIDADE	  = (tags[6] + " ");
	public final String PARTICIPANTES = (tags[7] + " ");
	public final String LOGIN		  = (tags[8] + " ");
}
