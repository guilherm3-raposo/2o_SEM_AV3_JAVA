package edu.senai.integrador.ferramentas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import edu.senai.integrador.seguranca.Seguranca;

public class LeitorArquivo {
	private static final File DADOS = new File("dados");
	private static final File LOG = new File(DADOS + "/log");
	private static final File CONFIGURACOES = new File(DADOS + "/configuracoes.properties");
	private static final File SEGURANCA = new File(DADOS + "/seguranca.properties");
	private static final File INSTALADOR = new File(DADOS + "/bancoInstalador.txt");
	private static final File TABELAS_BANCO = new File(DADOS + "/SqlTabelas.xml");
	private static final File COLUNAS_BANCO = new File(DADOS + "/colunasSQL.xml");
	private static final File COMANDOS_BANCO = new File(DADOS + "/comandosSQL.xml");
	private static final File SINTAXE_BANCO = new File(DADOS + "/SqlSintaxe.xml");
	private static final File POPULA_ENDERECO = new File(DADOS + "/populadorEnderecos.txt");
	private static final File POPULA_RUAS = new File(DADOS + "/populadorRuas.txt");
	private static final File POPULA_CIDADES = new File(DADOS + "/populadorCidades.txt");
	private static final File POPULA_MODAL = new File(DADOS + "/populadorModalidades.txt");
	private static final File POPULA_EXER = new File(DADOS + "/populadorExercicios.txt");
	private static final File POPULA_FOCA = new File(DADOS + "/foca.ico");
	

	public static boolean criaArquivos() {
		mkFolder(DADOS);
		mkFolder(LOG);
		if(mkFile(new File(LOG + "/erros.log"))) escreveArquivo(LOG + "/erros.log");
		if(mkFile(CONFIGURACOES)) 	escreveArquivo("dados/configuracoes.properties");
		if(mkFile(SEGURANCA)) 	  	escreveArquivo("dados/seguranca.properties");
		if(mkFile(TABELAS_BANCO)) 	escreveArquivo("dados/SqlTabelas.xml");
		if(mkFile(COLUNAS_BANCO)) 	escreveArquivo("dados/colunasSQL.xml");
		if(mkFile(COMANDOS_BANCO))	escreveArquivo("dados/comandosSQL.xml");
		if(mkFile(SINTAXE_BANCO)) 	escreveArquivo("dados/SqlSintaxe.xml");
		if(mkFile(INSTALADOR))    	escreveArquivo("dados/bancoInstalador.txt");
		if(mkFile(POPULA_ENDERECO))	escreveArquivo("dados/populadorEnderecos.txt");
		if(mkFile(POPULA_RUAS))		escreveArquivo("dados/populadorRuas.txt");
		if(mkFile(POPULA_CIDADES))	escreveArquivo("dados/populadorCidades.txt");
		if(mkFile(POPULA_MODAL)) 	escreveArquivo("dados/populadorModalidades.txt");
		if(mkFile(POPULA_EXER)) 	escreveArquivo("dados/populadorExercicios.txt");
		if(mkFile(POPULA_FOCA)) 	escreveArquivo("dados/foca.ico");
		return true;
	}
	
	private static boolean mkFolder(File name) {
		if(!name.exists())
			name.mkdir();
		return true;
	}

	private static boolean mkFile(File arquivo) {
		try {
			return !arquivo.exists() ? arquivo.createNewFile() : false;
		} catch (IOException e) {
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
			e.printStackTrace();
		}
		return true;
	}
}
