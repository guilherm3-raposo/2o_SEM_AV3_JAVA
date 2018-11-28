package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasMusculacao {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasMusc", "/bancoConfiguracoes.xml");

	public final String TREINO= (tags[0]);
	public final String OBJETIVO= (tags[1]);
	public final String SESSAO= (tags[2]);
	public final String OBSERVACAO= (tags[3]);
	public final String CPF_ALUNO= (tags[4]);
}
