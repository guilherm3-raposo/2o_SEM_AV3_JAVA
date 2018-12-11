package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasTurma {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasTurma", "/colunasSQL.xml");
	
	public final String ID_TURMA	 	= (tags[0]);
	public final String ID_MODALIDADE	= (tags[1]);
	public final String HORA_INICIO	 	= (tags[2]);
	public final String DURACAO		 	= (tags[3]);
	public final String ATIVO			= (tags[4]);

}
