package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasAluno extends ColunasPessoa {
	Properties prop = Configuracoes.Carrega();
	private String[] tags = LeXml.getTag("colunasAluno", prop.getProperty("configBanco"));

	public final String CPF 		 = (tags[0]);
	public final String ALTURA		 = (tags[1]);
	public final String PESO		 = (tags[2]);
}
