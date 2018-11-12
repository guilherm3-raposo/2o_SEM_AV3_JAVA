package edu.senai.integrador.dao.sql;

public enum EXmlTabelas {
	CONTATO		(LeXml.getTag("tabelas")[0]),
	ENDERECO	(LeXml.getTag("tabelas")[1]),
	PESSOA		(LeXml.getTag("tabelas")[2]),
	ALUNO		(LeXml.getTag("tabelas")[3]),
	FUNCIONARIO	(LeXml.getTag("tabelas")[4]),
	TURMA		(LeXml.getTag("tabelas")[5]),
	MODALIDADE	(LeXml.getTag("tabelas")[6]);
	
	private String tabela;

	private EXmlTabelas(String tabela) {
		this.tabela = tabela;
	}
	
	public String getTabela() {
		return tabela;
	}
}
