package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ColunasAluno extends ColunasPessoa {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	private String[] tags = LeXml.getTag("colunasAluno", "/colunasSQL.xml");

	public final String CPF 		 = (tags[0]);
	public final String ALTURA		 = (tags[1]);
	public final String PESO		 = (tags[2]);
}
