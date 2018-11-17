package edu.senai.integrador.beans;

import java.time.LocalDate;

import edu.senai.integrador.beans.enumeradores.EEscolaridade;
import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.ferramentas.FormataDados;
import edu.senai.integrador.ferramentas.Validacoes;

public class Funcionario extends Pessoa {

	private String ctps;
	private EEscolaridade escolaridade;

	public Funcionario() {

	}

	public Funcionario(String cpf, String ctps, EEscolaridade escolaridade, String nome, LocalDate dataDeNascimento,
			ESexo sexo, EEstadoCivil estadoCivil) throws FuncionarioException, PessoaException {
		super.setCPF(cpf);
		this.setCtps(ctps);
		this.setEscolaridade(escolaridade);
		super.setNome(nome);
		super.setDataDeNascimento(dataDeNascimento);
		super.setSexo(sexo);
		super.setEstadoCivil(estadoCivil);
	}

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) throws PessoaException {
		this.ctps = ctps;
	}

	public EEscolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(EEscolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	@Override
	public String toString() {
		return super.toString() + "\nCTPS______________ " + FormataDados.formataCtps(getCtps())
				+ "\nEscolaridade______ " + getEscolaridade().getDescricao();
	}
}
