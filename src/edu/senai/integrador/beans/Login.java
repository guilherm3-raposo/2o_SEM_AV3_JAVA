package edu.senai.integrador.beans;

public class Login {
	private String usuario;
	private String senha;
	private String cpf;
	private int permissao;

	public Login() {
	}
	
	public Login(String usuario, String senha, String cpf, int permissao) {
		setUsuario(usuario);
		setSenha(senha);
		setCpf(cpf);
		setPermissao(permissao);
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
	
	public int getPermissao() {
		return permissao;
	}
	
	public void setPermissao(int permissao) {
		this.permissao = permissao;
	}
}
