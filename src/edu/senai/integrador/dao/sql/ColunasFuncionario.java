package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasFuncionario extends ColunasPessoa {
	Properties prop = Configuracoes.Carrega();
	private String[] tags = LeXml.getTag("colunasFuncionario", prop.getProperty("configBanco"));

	public final String CPF 		 = (tags[0]);
	public final String ESCOLARIDADE = (tags[1]);
	public final String CTPS		 = (tags[2]);
}
