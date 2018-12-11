package edu.senai.integrador.beans.pessoa;

import java.time.LocalDate;
import java.time.Period;

import edu.senai.integrador.ferramentas.FormataDados;
import edu.senai.integrador.logs.GeraLog;

public abstract class Pessoa {
	private String CPF;
	private String nome;
	private LocalDate dataDeNascimento;
	private ESexo sexo;
	private EEstadoCivil estadoCivil;
	private boolean ativo;

	public String getCPF() throws PessoaException  {
		return CPF;
	}

	public void setCPF(String cpf) throws PessoaException {
		this.CPF = cpf;
	}

	public String getNome() throws PessoaException  {
		return nome;
	}

	public void setNome(String nome) throws PessoaException {
		this.nome = nome;
	}

	public LocalDate getDataDeNascimento() throws PessoaException  {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) throws PessoaException {
		this.dataDeNascimento = dataDeNascimento;
	}

	public int getIdade() throws PessoaException  {
		return Period.between(getDataDeNascimento(), LocalDate.now()).getYears();
	}

	public String getIdadeCompleta() throws PessoaException  {
		Period idade = Period.between(getDataDeNascimento(), LocalDate.now());
		return idade.getYears() + " anos, " + idade.getMonths() + " meses e " + idade.getDays() + " dias";
	}

	public ESexo getSexo() throws PessoaException  {
		return sexo;
	}

	public void setSexo(ESexo sexo) throws PessoaException  {
		this.sexo = sexo;
	}

	public EEstadoCivil getEstadoCivil() throws PessoaException  {
		return estadoCivil;
	}

	public void setEstadoCivil(EEstadoCivil estadoCivil) throws PessoaException  {
		this.estadoCivil = estadoCivil;
	}
	
	public boolean isAtivo() throws PessoaException  {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) throws PessoaException  {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		try {
			return "CPF_______________ " + FormataDados.formataCpf(getCPF()) + "\nNome______________ " + this.getNome()
					+ "\nDataDeNascimento__ " + FormataDados.formataData(getDataDeNascimento()) + "\nSexo______________ "
					+ this.getSexo().getDescricao() + "\nEstadoCivil_______ "
					+ (this.getSexo() == ESexo.MASCULINO ? getEstadoCivil().getMasculino()
							: getEstadoCivil().getFeminino());
		} catch (PessoaException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return "Erro ao exportar os dados!";
	}

}
