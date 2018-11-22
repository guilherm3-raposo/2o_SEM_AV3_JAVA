package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasLogin {
	Properties prop = Configuracoes.Carrega();
	private String[] tags = LeXml.getTag("colunasLogin", prop.getProperty("configBanco"));

	public final String USUARIO	= (tags[0]);
	public final String SENHA	= (tags[1]);
	public final String CPF		= (tags[2]);
	public final String ADMIN	= (tags[4]);
}
