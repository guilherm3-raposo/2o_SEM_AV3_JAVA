package edu.senai.integrador.beans.pessoa;

import java.time.LocalDate;

import edu.senai.integrador.logs.GeraLog;

public class Funcionario extends Pessoa {

	private String ctps;
	private EEscolaridade escolaridade;

	public Funcionario() {

	}

	public Funcionario(String cpf, 
					   String ctps, 
				EEscolaridade escolaridade, 
					   String nome, 
					LocalDate dataDeNascimento,
						ESexo sexo, 
				 EEstadoCivil estadoCivil, 
				 	  boolean ativo) throws FuncionarioException, PessoaException {
		super.setCPF(cpf);
		this.setCtps(ctps);
		this.setEscolaridade(escolaridade);
		super.setNome(nome);
		super.setDataDeNascimento(dataDeNascimento);
		super.setSexo(sexo);
		super.setEstadoCivil(estadoCivil);
		super.setAtivo(ativo);
	}

	public String getCtps() throws FuncionarioException, PessoaException  {
		return ctps;
	}

	public void setCtps(String ctps) throws FuncionarioException, PessoaException {
		this.ctps = ctps;
	}

	public EEscolaridade getEscolaridade() throws FuncionarioException, PessoaException  {
		return escolaridade;
	}

	public void setEscolaridade(EEscolaridade escolaridade) throws FuncionarioException, PessoaException  {
		this.escolaridade = escolaridade;
	}

	@Override
	public String toString() {
		try {
			return super.toString() + "\nCTPS______________ " + getCtps()
			+ "\nEscolaridade______ " + getEscolaridade().getDescricao();
		} catch (FuncionarioException | PessoaException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return "Erro ao exportar os dados!";
	}
}
