package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class ComandosAluno {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	String[] tags = LeXml.getTag("comandosAluno", "/comandosSQL.xml");

	public final String INSERT 		= (tags[0]);
	public final String SELECT 		= (tags[1]);
	public final String SELECT_ALL  = (tags[2]);
	public final String UPDATE 	    = (tags[3]);
	public final String DELETE      = (tags[4]);
}
