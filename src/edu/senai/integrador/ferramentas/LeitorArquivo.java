package edu.senai.integrador.ferramentas;

import java.io.File;
import java.io.IOException;

public class LeitorArquivo {
	public static File testaArquivo(String caminhoArquivo) {
		String workspace = System.getProperty("user.dir");
		File dados = new File(workspace + "/dados");
		File config = new File(dados + caminhoArquivo);

		if (!dados.exists()) {
			dados.mkdir();
		}
		if (config.exists() && !config.isDirectory()) {
			return config;
		} else {
			try {
				config.createNewFile();
				return config;
			} catch (IOException e) {

			}
		}
		return config;
	}
}
