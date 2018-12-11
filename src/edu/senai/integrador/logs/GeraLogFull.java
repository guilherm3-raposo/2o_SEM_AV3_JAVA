package edu.senai.integrador.logs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import edu.senai.integrador.ferramentas.Arquivo;

public class GeraLogFull {
	public void escreveLogArquivo(Exception... objetosErro) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss (E.)");
		StringBuffer mensagemErro = new StringBuffer("#" + sdf.format(new Date()) + " - ");
		Stack<Exception> erros = new Stack<Exception>();

		for (Exception erro : objetosErro) {
			erros.push(erro);
		}
		StringBuffer buffer = new StringBuffer();
		StackTraceElement[] tracer = {};
		for (Exception exception : erros) {
			for (int i = 0; i < exception.getStackTrace().length; i++) {
				buffer.append("\n\n-----------------------------" + tracer[i].getFileName() + "\n\n"
						+ tracer[i].getClassName() + "\n\n" + tracer[i].getMethodName() + "\n\n"
						+ tracer[i].getLineNumber() + "\n\n" + tracer[i].getClass()
						+ "\n\n-----------------------------");
			}
		}
		try {
			Arquivo.gravaArquivo(System.getProperty("user.dir") + "/log/errosBanco.log",
					mensagemErro.toString() + buffer.toString(), true);
		} catch (IOException e) {
		}
	}
}
