package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasParticipantes {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasParticipantes", "/bancoConfiguracoes.xml");
	
	public final String ID_TURMA = (tags[0]);
	public final String ID_ALUNO = (tags[1]);
	public final String ID_FUNCI = (tags[2]);

}
