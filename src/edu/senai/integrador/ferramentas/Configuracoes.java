package edu.senai.integrador.ferramentas;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracoes {
//	TODO REFATORAR PRO TESTE DE ARQUIVO
	static String enderecoConfiguracoes = System.getProperty("user.dir") + "/dados/configuracoes.properties";

	public static Properties Carrega() {
		Properties configuracoes = new Properties();
		try {
			FileInputStream arquivoEntrada = new FileInputStream(new File(enderecoConfiguracoes));
			configuracoes.load(arquivoEntrada);
		} catch (IOException e) {
		}
		return configuracoes;

	}
}
