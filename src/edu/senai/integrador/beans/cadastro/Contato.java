package edu.senai.integrador.beans.cadastro;

import edu.senai.integrador.ferramentas.FormataDados;
import edu.senai.integrador.logs.GeraLog;

public class Contato {
	
	private String CPF;
	private String fixo;
	private String telefoneMovel;
	private String email;

	public Contato() {
		
	}
	
	public Contato(String CPF, 
				   String telefoneFixo, 
				   String telefoneMovel, 
				   String email) throws ContatoException {
		setCPF(CPF);
		setTelefoneFixo(telefoneFixo);
		setTelefoneMovel(telefoneMovel);
		setEmail(email);
	}
	
	public String getCPF() throws ContatoException {
		return CPF;
	}
	
	public void setCPF(String CPF) throws ContatoException {
		this.CPF = CPF;
	}

	public String getTelefoneFixo() throws ContatoException {
		return fixo;
	}

	public void setTelefoneFixo(String telefoneFixo) throws ContatoException {
		this.fixo = telefoneFixo;
	}

	public String getTelefoneMovel() throws ContatoException {
		return telefoneMovel;
	}

	public void setTelefoneMovel(String telefoneMovel1) throws ContatoException {
		this.telefoneMovel = telefoneMovel1;
	}

	public String getEmail() throws ContatoException {
		return email;
	}

	public void setEmail(String email) throws ContatoException {
		this.email = email;
	}

	@Override
	public String toString() {
		try {
			return "\nCPF_______________ " + FormataDados.formataCpf(getCPF()) +
				   "\nTelefone Fixo_____ " + FormataDados.formataTelefone(getTelefoneFixo()) + 
				   "\nTelefone Móvel____ " + FormataDados.formataTelefone(getTelefoneMovel()) + 
				   "\nEmail_____________ " + getEmail();
		} catch (ContatoException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return "Erro ao exportar os dados!";
	}
}
