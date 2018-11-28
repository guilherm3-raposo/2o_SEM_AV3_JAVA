package edu.senai.integrador.beans;

import edu.senai.integrador.beans.enumeradores.EEndereco;

public class Endereco {
	private String cpf;
	private EEndereco logradouro;
	private String via;
	private int numero;
	private String complemento;
	private String cidade;
	private String estado;
	private String cep;

	public Endereco() {
		
	}
	
	public Endereco(String cpf, EEndereco logradouro, String via, int numero, String complemento, String cidade, String estado,
			String cep) {
		setCpf(cpf);
		setLogradouro(logradouro);
		setVia(via);
		setNumero(numero);
		setComplemento(complemento);
		setCidade(cidade);
		setEstado(estado);
		setCep(cep);
	}
	
	public Endereco(String cpf, EEndereco logradouro, String via, int numero, String cidade, String estado,
			String cep) {
		setCpf(cpf);
		setLogradouro(logradouro);
		setVia(via);
		setNumero(numero);
		setCidade(cidade);
		setEstado(estado);
		setCep(cep);
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public EEndereco getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(EEndereco logradouro) {
		this.logradouro = logradouro;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via)  {
		this.via = via;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero){
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return super.toString() + "\nCPF______________ " + getCpf() 
								+ "\nLogradouro_______ " + getLogradouro().getDescricao()
								+ "\nVia______________ " + getVia()
								+ "\nNúmero___________ " + getNumero()
								+ "\nComplemento______ " + getComplemento()
								+ "\nCidade___________ " + getCidade()
								+ "\nEstado___________ " + getEstado()
								+ "\nCEP______________ " + getCep();
	}

}
