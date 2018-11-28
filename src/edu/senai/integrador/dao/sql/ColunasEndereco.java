package edu.senai.integrador.dao.sql;

public class ColunasEndereco {
	private String[] tags = LeXml.getTag("colunasEndereco", "/configBanco.xml");

	public final String CPF 		 = (tags[0]);
	public final String LOGRADOURO 	 = (tags[1]);
	public final String VIA		 	 = (tags[2]);
	public final String NUMERO		 = (tags[3]);
	public final String COMPLEMENTO	 = (tags[4]);
	public final String CIDADE		 = (tags[5]);
	public final String ESTADO		 = (tags[6]);
	public final String CEP	 		 = (tags[7]);

}
