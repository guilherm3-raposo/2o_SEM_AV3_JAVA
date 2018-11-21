package edu.senai.integrador.dao.sql;

public class ColunasLogin {
	private String[] tags = LeXml.getTag("colunasLogin", "/configBanco.xml");

	public final String USUARIO	= (tags[0]);
	public final String SENHA	= (tags[1]);
	public final String CPF		= (tags[2]);
	public final String ADMIN	= (tags[4]);
}
