package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class MusculacaoComandos {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasExercicio", "/bancoConfiguracoes.xml");

	public final String INSERT	= (tags[0]);
	public final String SELECT	= (tags[1]);
}
