package edu.senai.integrador.beans.exception;

import edu.senai.integrador.beans.enumeradores.EExercicioErro;
import edu.senai.integrador.logs.GeraLog;

public class ExercicioException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ExercicioException(EExercicioErro erro, Class<?> classe, String mensagem) {
		super(erro.getErro());
		GeraLog log = new GeraLog();
		log.escreveLogArquivo(erro, classe.getName(), mensagem);
	}

}
