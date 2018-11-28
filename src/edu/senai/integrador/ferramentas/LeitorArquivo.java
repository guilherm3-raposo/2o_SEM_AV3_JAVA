package edu.senai.integrador.ferramentas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import edu.senai.integrador.seguranca.Seguranca;

public class LeitorArquivo {
	private static final File DADOS = new File("dados");
	private static final File CONFIGURACOES = new File(DADOS + "/configuracoes.properties");
	private static final File SEGURANCA = new File(DADOS + "/seguranca.properties");
	private static final File BD_XML = new File(DADOS + "/bancoConfiguracoes.xml");
	private static final File INSTALADOR = new File(DADOS + "/bancoInstalador.txt");


	public static boolean criaArquivos() {
		mkDados();
		mkFile(CONFIGURACOES);
		mkFile(SEGURANCA);
		mkFile(BD_XML);
		mkFile(INSTALADOR);
		escreveArquivo("dados/configuracoes.properties");
		escreveArquivo("dados/seguranca.properties");
		escreveArquivo("dados/bancoConfiguracoes.xml");
		escreveArquivo("dados/bancoInstalador.txt");
		
		return true;
	}
	
	private static boolean mkDados() {
		return !DADOS.exists() ? DADOS.mkdir() : false;
	}

	private static boolean mkFile(File arquivo) {
		try {
			return arquivo.exists() ? arquivo.createNewFile() : false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static File leArquivo(String caminhoArquivo) {
		File config = new File(DADOS + caminhoArquivo);
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

	public static boolean escreveArquivo(String origem) {
		try {
			InputStream conteudo = Seguranca.class.getResourceAsStream(origem);
			OutputStream novoConteudo = null;
			String destino = origem;
			novoConteudo = new FileOutputStream(new File(destino));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = conteudo.read(buffer)) > 0) {
				novoConteudo.write(buffer, 0, length);
			}
			conteudo.close();
			novoConteudo.close();
		} catch (IOException e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
