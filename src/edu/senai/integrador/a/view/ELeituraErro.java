package edu.senai.integrador.a.view;

public enum ELeituraErro {
	NUMERO_INVALIDO		("Digite um n�mero inteiro v�lido!"),
	VALOR_VAZIO			("Campo n�o pode estar vazio!"),
	VALOR_INVALIDO		("Valor informado � inv�lido!"),
	IP_INVALIDO			("O IP informado � inv�lido! (XXX.XXX.X.X.X???)"), 
	SIM_NAO				("Valor informado inv�lido! Digite sim, s, nao, n, yes, no, 1 ou 0!"), 
	DATA_INVALIDA		("A data informada � inv�lida!");

	private String erro;
	
	private ELeituraErro(String erro) {
		this.erro = erro;
	}

	@Override
	public String toString() {
		return this.erro.toString();
	}
}
