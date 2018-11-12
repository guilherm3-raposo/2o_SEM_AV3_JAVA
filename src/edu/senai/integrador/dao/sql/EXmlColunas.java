package edu.senai.integrador.dao.sql;

public enum EXmlColunas {
	CPF			(LeXml.getTag("colunas")[0]), 
	NOME		(LeXml.getTag("colunas")[1]),
	ESTAD0_CIVIL(LeXml.getTag("colunas")[2]),
	SEXO		(LeXml.getTag("colunas")[3]),
	DATA_NASC	(LeXml.getTag("colunas")[4]),
	ALTURA		(LeXml.getTag("colunas")[5]),
	PESO		(LeXml.getTag("colunas")[6]),
	IMC			(LeXml.getTag("colunas")[7]),
	ESCOLARIDADE(LeXml.getTag("colunas")[8]),
	CTPS		(LeXml.getTag("colunas")[9]),
	ID_TURMA	(LeXml.getTag("colunas")[10]),
	HORA_INICIO	(LeXml.getTag("colunas")[11]),
	DURACAO		(LeXml.getTag("colunas")[12]),
	SEMANA		(LeXml.getTag("colunas")[13]),
	ID_ALUNO	(LeXml.getTag("colunas")[14]),
	ID_FUNCIO	(LeXml.getTag("colunas")[15]),
	ID_MODALI	(LeXml.getTag("colunas")[16]),
	MIN_PARTIC	(LeXml.getTag("colunas")[17]),
	MAX_PARTIC	(LeXml.getTag("colunas")[18]);

	private String campo;

	private EXmlColunas(String campo) {
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
