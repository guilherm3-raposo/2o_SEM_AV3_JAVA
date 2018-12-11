package edu.senai.integrador.bancodedados.conexao;

public enum EConexaoErro {
	ABRE_CONEXAO("Erro de Conexão: Não foi possível estabelecer conexão com o Banco de Dados."),
	FECHA_CONEXAO("Erro de Conexão: Não foi possível fechar a conexão com o Banco de Dados.");

	private final String mensagem;


	private EConexaoErro(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
}
