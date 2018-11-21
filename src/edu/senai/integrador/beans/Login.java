package edu.senai.integrador.beans;

public class Login {
	private String usuario;
	private String senha;
	private String cpf;
	private boolean sUser;
	private boolean admin;

	public Login() {
	}
	
	public Login(String usuario, String senha, String cpf) {
		setUsuario(usuario);
		setSenha(senha);
		setCpf(cpf);
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isSu() {
		return sUser;
	}

	public void setSu(boolean su) {
		this.sUser = su;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
