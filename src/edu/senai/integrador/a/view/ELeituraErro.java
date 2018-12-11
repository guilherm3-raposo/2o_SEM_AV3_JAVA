package edu.senai.integrador.a.view;

public enum ELeituraErro {
	NUMERO_INVALIDO		("Digite um número inteiro válido!"),
	VALOR_VAZIO			("Campo não pode estar vazio!"),
	VALOR_INVALIDO		("Valor informado é inválido!"),
	IP_INVALIDO			("O IP informado é inválido! (XXX.XXX.X.X.X???)"), 
	SIM_NAO				("Valor informado inválido! Digite sim, s, nao, n, yes, no, 1 ou 0!"), 
	DATA_INVALIDA		("A data informada é inválida!");

	private String erro;
	
	private ELeituraErro(String erro) {
		this.erro = erro;
	}

	@Override
	public String toString() {
		return this.erro.toString();
	}
}
