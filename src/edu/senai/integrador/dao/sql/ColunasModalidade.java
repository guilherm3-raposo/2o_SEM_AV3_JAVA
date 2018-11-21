package edu.senai.integrador.dao.sql;

public class ColunasModalidade {
	private String[] tags = LeXml.getTag("colunasModalidade", "/configBanco.xml");

	public final String SEMANA		 = (tags[0]);
	public final String ID_MODALI	 = (tags[1]);
	public final String MIN_PARTIC	 = (tags[2]);
}
