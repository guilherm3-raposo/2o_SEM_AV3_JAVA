package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasLogin {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasLogin", "/bancoConfiguracoes.xml");

	public final String USUARIO	= (tags[0]);
	public final String SENHA	= (tags[1]);
	public final String CPF		= (tags[2]);
	public final String ADMIN	= (tags[4]);
}
