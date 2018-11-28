package edu.senai.integrador.ferramentas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracoes {
//	TODO REFATORAR PRO TESTE DE ARQUIVO
	private static final String CONFIGURACOES = "dados/configuracoes.properties";
	private static final String SEGURANCA = "dados/seguranca.properties";

	public Configuracoes() {

	}

	public Properties carrega(boolean seguranca) {
		Properties prop = new Properties();
		try {
			InputStream is = new FileInputStream(new File(seguranca ? SEGURANCA : CONFIGURACOES));
			prop.load(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	public boolean salva(Properties prop, boolean seguranca) {
		try {
			FileOutputStream arquivoSaida = new FileOutputStream(new File(seguranca ? SEGURANCA : CONFIGURACOES));
			prop.store(arquivoSaida, "Alterado em: ");
			arquivoSaida.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
