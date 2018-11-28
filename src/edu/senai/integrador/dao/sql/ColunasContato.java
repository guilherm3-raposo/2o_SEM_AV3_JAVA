package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasContato {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasContato", "/bancoConfiguracoes.xml");
	
	public final String CPF 	= (tags[0]);
	public final String FIXO	= (tags[1]);
	public final String MOVEL	= (tags[2]);
	public final String EMAIL	= (tags[3]);
}
