package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasExercicio {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasExercicio", "/colunasSQL.xml");

	public final String EXERCICIO 		 = (tags[0]);
	public final String SERIE       	 = (tags[1]);
	public final String REPETICAO		 = (tags[2]);
	public final String CARGA	 	     = (tags[3]);
	public final String TREINO	 	     = (tags[4]);
}
