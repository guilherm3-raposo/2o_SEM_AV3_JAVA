package edu.senai.integrador.dao.sql;

public class ColunasFuncionario extends ColunasPessoa {
	private String[] tags = LeXml.getTag("colunasFuncionario", "configBanco.xml");

	public final String CPF 		 = (tags[0]);
	public final String ESCOLARIDADE = (tags[1]);
	public final String CTPS		 = (tags[2]);
}
