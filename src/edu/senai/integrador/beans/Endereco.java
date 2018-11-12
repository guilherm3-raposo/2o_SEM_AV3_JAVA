package edu.senai.integrador.beans;

import edu.senai.integrador.beans.enumeradores.EEndereco;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.ferramentas.FormataDados;

public class Endereco {
	private EEndereco logradouro;
	private String endereco;
	private int numero;
	private String complemento;

	public Endereco(EEndereco logradouro, String endereco, int numero, String complemento) throws PessoaException {
		setLogradouro(logradouro);
		setEndereco(endereco);
		setNumero(numero);
		setComplemento(complemento);
	}

	public EEndereco getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(EEndereco logradouro) {
		this.logradouro = logradouro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) throws PessoaException {
		this.endereco = endereco;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) throws PessoaException {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) throws PessoaException {
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "\nEndereco__________ "
				+ FormataDados.formataEndereco(getLogradouro(), getEndereco(), getNumero(), getComplemento());

	}

}
