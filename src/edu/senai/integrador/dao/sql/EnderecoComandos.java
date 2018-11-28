package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class EnderecoComandos {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("enderecoComandos", "/bancoConfiguracoes.xml");
	
	public final String INSERT	= (tags[0]);
	public final String SELECT	= (tags[1]);
	public final String MOVEL	= (tags[2]);
	public final String EMAIL	= (tags[3]);
}
