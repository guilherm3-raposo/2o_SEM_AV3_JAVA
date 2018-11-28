package edu.senai.integrador.dao.sql;

import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class SqlSintaxe {
	Configuracoes config = new Configuracoes();
	Properties prop = config.carrega(false);
	String[] tags = LeXml.getTag("sintaxe", "/bancoConfiguracoes.xml");
	
	public final String CHAR 	 	= (tags[0]);
	public final String VARCHAR  	= (tags[1]);
	public final String DATE 	 	= (tags[2]);
	public final String DATETIME 	= (tags[3]);
	public final String COMMA 	 	= (tags[4]);
	public final String IN		 	= (tags[5]);
	public final String ON		 	= (tags[6]);
	public final String NOT		 	= (tags[7]);
	public final String AND		 	= (tags[8]);
	public final String NULL	 	= (tags[9]);
	public final String WHERE	 	= (tags[10]);
	public final String VALUES	 	= (tags[11]);
	public final String INNER	 	= (tags[12]);
	public final String LEFT	 	= (tags[13]);
	public final String RIGHT	 	= (tags[14]);
	public final String FULL	 	= (tags[15]);
	public final String SELF	 	= (tags[16]);
	public final String JOIN	 	= (tags[17]);
	public final String GROUP	 	= (tags[18]);
	public final String ORDER	 	= (tags[19]);
	public final String BY		 	= (tags[20]);
	public final String HAVING	 	= (tags[21]);
	public final String EXISTS	 	= (tags[22]);
	public final String ANY		 	= (tags[23]);
	public final String ALL		 	= (tags[24]);
	public final String CASE	 	= (tags[25]);
	public final String FROM	 	= (tags[26]);
	public final String INSERT	 	= (tags[27]);
	public final String CREATE	 	= (tags[28]);
	public final String SELECT	 	= (tags[29]);
	public final String UPDATE	 	= (tags[30]);
	public final String DELETE	 	= (tags[31]);
	public final String INTO	 	= (tags[32]);
	public final String DATABASE 	= (tags[33]);
	public final String SET		 	= (tags[34]);
	public final String EQUALS	 	= (tags[35]);
	public final String SEMI_COLON	= (tags[36]);
	public final String OPEN_PAR 	= (tags[37]);
	public final String CLOSE_PAR	= (tags[38]);
	public final String ALTER		= (tags[39]);
	public final String TABLE		= (tags[40]);
	public final String COLUMN		= (tags[41]);
}
