package edu.senai.integrador.beans;

import java.time.LocalDate;

import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.AlunoException;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.ferramentas.FormataDados;

public class Aluno extends Pessoa {
	private double altura;
	private double peso;

	public Aluno() {

	}

	public Aluno(String cpf, String nome, LocalDate dataDeNascimento, ESexo sexo, double altura, double peso,
			EEstadoCivil estadoCivil) throws PessoaException, AlunoException, FuncionarioException {
		super.setCPF(cpf);
		super.setNome(nome);
		super.setDataDeNascimento(dataDeNascimento);
		super.setSexo(sexo);
		this.setAltura(altura);
		this.setPeso(peso);
		super.setEstadoCivil(estadoCivil);
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) throws PessoaException {
		this.altura = altura;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) throws PessoaException {
		this.peso = peso;
	}

	public double getIMC() {
		return this.getPeso() / Math.pow(getAltura(), 2);
	}

	@Override
	public String toString() {
		return super.toString() + "\nAltura____________ " + FormataDados.formataAltura(getAltura())
				+ "\nPeso______________ " + FormataDados.formataPeso(getPeso());
	}
}
