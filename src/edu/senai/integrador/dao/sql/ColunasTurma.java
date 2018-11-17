package edu.senai.integrador.dao.sql;

public class ColunasTurma {
	private String[] tags = LeXml.getTag("colunasTurma", "/configBanco.xml");
	
	public final String ID_TURMA	 	= (tags[0]);
	public final String ID_MODALIDADE	= (tags[1]);
	public final String HORA_INICIO	 	= (tags[2]);
	public final String DURACAO		 	= (tags[3]);

}
