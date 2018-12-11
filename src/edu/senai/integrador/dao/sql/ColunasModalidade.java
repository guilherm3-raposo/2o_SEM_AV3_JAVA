package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasModalidade {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasModalidade", "/colunasSql.xml");

	public final String ID_MODALI	 = (tags[0]);
	public final String SEMANA		 = (tags[1]);
	public final String MIN_PARTIC	 = (tags[2]);
}
