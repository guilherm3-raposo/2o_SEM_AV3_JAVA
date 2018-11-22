package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasModalidade {
	Properties prop = Configuracoes.Carrega();
	private String[] tags = LeXml.getTag("colunasModalidade", prop.getProperty("configBanco"));

	public final String SEMANA		 = (tags[0]);
	public final String ID_MODALI	 = (tags[1]);
	public final String MIN_PARTIC	 = (tags[2]);
}
