package edu.senai.integrador.dao.sql;

public class ColunasParticipantes {
	private String[] tags = LeXml.getTag("colunasParticipantes", "configBanco.xml");
	
	public final String ID_TURMA = (tags[0]);
	public final String ID_ALUNO = (tags[1]);
	public final String ID_FUNCI = (tags[2]);

}
