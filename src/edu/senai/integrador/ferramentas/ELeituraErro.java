package edu.senai.integrador.ferramentas;

public enum ELeituraErro {
	NOME_INVALIDO("Nome deve conter apenas letras e espaços"),
	NUMERO_INVALIDO("Digite um número inteiro válido"),
	VALOR_INVALIDO("Valor informado é inválido"),
	IP_INVALIDO("O IP informado é inválido! (XXX.XXX.X.X.X??"), 
	SIM_NAO("Valor informado inválido! Digite sim, s, nao, n, yes, no, 1 ou 0"), 
	DOUBLE_INVALIDO("");
	
	String erro;

	private ELeituraErro(String erro) {
		this.erro = erro;
	}
	
	public String getErro() {
		return erro;
	}
	
	@Override
	public String toString() {
		return getErro();
	}
}
