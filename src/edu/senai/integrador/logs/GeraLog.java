package edu.senai.integrador.logs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.senai.integrador.ferramentas.Arquivo;

public class GeraLog {
	public void escreveLogArquivo(Object... objetosErro) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss (E.)");
		StringBuffer mensagemErro = new StringBuffer("#" + sdf.format(new Date()) + " - ");
		for (Object exception : objetosErro) {
			mensagemErro.append(exception.toString());
		}
		try {
			Arquivo.gravaArquivo(System.getProperty("user.dir") + "/log/erros.log", mensagemErro.toString(), true);
		} catch (IOException e) {
		}
	}
}
