package edu.senai.integrador.dao.sql;

public class ColunasPessoa {
	private String[] tags = LeXml.getTag("colunasPessoa", "configBanco.xml");

	public final String CPF 		 = (tags[0]);
	public final String NOME 		 = (tags[1]);
	public final String ESTAD0_CIVIL = (tags[2]);
	public final String SEXO		 = (tags[3]);
	public final String DATA_NASC	 = (tags[4]);

}
