package edu.senai.integrador.ferramentas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import edu.senai.integrador.logs.GeraLog;

public class Configuracoes {
//	TODO REFATORAR PRO TESTE DE ARQUIVO
	private static final String CONFIGURACOES = "dados/configuracoes.properties";
	private static final String SEGURANCA = "dados/seguranca.properties";

	public Configuracoes() {

	}

	public Properties carrega(boolean seguranca) {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream(new File(seguranca ? SEGURANCA : CONFIGURACOES));
			prop.load(is);
			is.close();
			return prop;
		} catch (IOException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return null;
	}

	public boolean salva(Properties prop, boolean seguranca) {
		try {
			FileOutputStream arquivoSaida = new FileOutputStream(new File(seguranca ? SEGURANCA : CONFIGURACOES));
			prop.store(arquivoSaida, "Alterado em: ");
			arquivoSaida.close();
			return true;
		} catch (IOException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return true;
	}
}
