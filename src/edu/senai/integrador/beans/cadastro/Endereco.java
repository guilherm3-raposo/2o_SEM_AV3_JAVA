package edu.senai.integrador.beans.cadastro;

import edu.senai.integrador.logs.GeraLog;

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
	
	public Endereco(String cpf, 
			     EEndereco logradouro, 
			        String via, 
			           int numero, 
			        String complemento, 
			        String cidade, 
			        String estado,
			        String cep) throws EnderecoException {
		setCpf(cpf);
		setLogradouro(logradouro);
		setVia(via);
		setNumero(numero);
		setComplemento(complemento);
		setCidade(cidade);
		setEstado(estado);
		setCep(cep);
	}
	
	public Endereco(String cpf, 
				 EEndereco logradouro, 
				 	String via, 
				 	   int numero, 
				 	String cidade, 
				 	String estado,
				 	String cep) throws EnderecoException {
		setCpf(cpf);
		setLogradouro(logradouro);
		setVia(via);
		setNumero(numero);
		setCidade(cidade);
		setEstado(estado);
		setCep(cep);
	}


	public String getCpf() throws EnderecoException {
		return cpf;
	}

	public void setCpf(String cpf) throws EnderecoException {
		this.cpf = cpf;
	}


	public EEndereco getLogradouro() throws EnderecoException {
		return logradouro;
	}

	public void setLogradouro(EEndereco logradouro) throws EnderecoException {
		this.logradouro = logradouro;
	}

	public String getVia() throws EnderecoException {
		return via;
	}

	public void setVia(String via) throws EnderecoException  {
		this.via = via;
	}

	public int getNumero() throws EnderecoException {
		return numero;
	}

	public void setNumero(int numero) throws EnderecoException {
		this.numero = numero;
	}

	public String getComplemento() throws EnderecoException {
		return complemento;
	}

	public void setComplemento(String complemento) throws EnderecoException {
		this.complemento = complemento;
	}
	
	public String getCidade() throws EnderecoException {
		return cidade;
	}

	public void setCidade(String cidade) throws EnderecoException {
		this.cidade = cidade;
	}
	
	public String getEstado() throws EnderecoException {
		return estado;
	}

	public void setEstado(String estado) throws EnderecoException {
		this.estado = estado;
	}
	
	public String getCep() throws EnderecoException {
		return cep;
	}

	public void setCep(String cep) throws EnderecoException {
		this.cep = cep;
	}

	@Override
	public String toString() {
		try {
			return "\nCPF______________ " + getCpf() 
					+ "\nLogradouro_______ " + getLogradouro().getDescricao()
					+ "\nVia______________ " + getVia()
					+ "\nNúmero___________ " + getNumero()
					+ "\nComplemento______ " + getComplemento()
					+ "\nCidade___________ " + getCidade()
					+ "\nEstado___________ " + getEstado()
					+ "\nCEP______________ " + getCep();
		} catch (EnderecoException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return "Erro ao exportar os dados";
	}

}
