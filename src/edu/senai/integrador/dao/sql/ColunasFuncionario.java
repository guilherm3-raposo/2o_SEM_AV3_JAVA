package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasFuncionario extends ColunasPessoa {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasFuncionario", "/bancoConfiguracoes.xml");

	public final String CPF 		 = (tags[0]);
	public final String ESCOLARIDADE = (tags[1]);
	public final String CTPS		 = (tags[2]);
}
