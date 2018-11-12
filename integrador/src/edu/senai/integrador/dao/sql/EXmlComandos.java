package edu.senai.integrador.dao.sql;

public enum EXmlComandos {
	INSERT_PESSOA		(LeXml.getTag("comandos")[0]),
	INSERT_ALUNO		(LeXml.getTag("comandos")[1]),
	INSERT_FUNC			(LeXml.getTag("comandos")[2]),
	SELECT_ALUNOS		(LeXml.getTag("comandos")[3]),
	SELECT_ALUNO		(LeXml.getTag("comandos")[4]),
	SELECT_FUNCIONARIOS	(LeXml.getTag("comandos")[5]),
	SELECT_FUNCIONARIO	(LeXml.getTag("comandos")[6]),
	SELECT_MINISTRANTES	(LeXml.getTag("comandos")[7]),
	SELECT_PARTICIPANTES(LeXml.getTag("comandos")[8]),
	SELECT_MODALIDADE	(LeXml.getTag("comandos")[9]),
	SELECT_TURMAS		(LeXml.getTag("comandos")[10]),
	SELECT_TURMA		(LeXml.getTag("comandos")[11]);
	
	private String campo;

	private EXmlComandos(String campo) {
		this.campo = campo;
	}

	public String getCampo() {
		return campo;
	}
	
	@Override
	public String toString() {
		return this.getCampo();
	}
}
