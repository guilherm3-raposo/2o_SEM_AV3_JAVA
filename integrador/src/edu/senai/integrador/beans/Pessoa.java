package edu.senai.integrador.beans;

import java.time.LocalDate;
import java.time.Period;

import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.ferramentas.FormataDados;

public abstract class Pessoa {
	private String CPF;
	private String nome;
	private LocalDate dataDeNascimento;
	private ESexo sexo;
	private EEstadoCivil estadoCivil;

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cpf) throws PessoaException {
		this.CPF = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws PessoaException {
		this.nome = nome;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) throws PessoaException {
		this.dataDeNascimento = dataDeNascimento;
	}

	public int getIdade() {
		return Period.between(getDataDeNascimento(), LocalDate.now()).getYears();
	}

	public String getIdadeCompleta() {
		Period idade = Period.between(getDataDeNascimento(), LocalDate.now());
		return idade.getYears() + " anos, " + idade.getMonths() + " meses e " + idade.getDays() + " dias";
	}

	public ESexo getSexo() {
		return sexo;
	}

	public void setSexo(ESexo sexo) {
		this.sexo = sexo;
	}

	public EEstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EEstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Override
	public String toString() {
		return "CPF_______________ " + FormataDados.formataCpf(getCPF()) + "\nNome______________ " + this.getNome()
				+ "\nDataDeNascimento__ " + FormataDados.formataData(getDataDeNascimento()) + "\nSexo______________ "
				+ this.getSexo().getDescricao() + "\nEstadoCivil_______ "
				+ (this.getSexo() == ESexo.MASCULINO ? getEstadoCivil().getMasculino()
						: getEstadoCivil().getFeminino());
	}

}
