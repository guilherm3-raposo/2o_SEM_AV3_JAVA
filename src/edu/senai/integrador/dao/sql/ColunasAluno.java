package edu.senai.integrador.dao.sql;

public class ColunasAluno extends ColunasPessoa {
	private String[] tags = LeXml.getTag("colunasAluno");
	
	public final String CPF 		 = (tags[0]);
	public final String ALTURA		 = (tags[1]);
	public final String PESO		 = (tags[2]);
}
