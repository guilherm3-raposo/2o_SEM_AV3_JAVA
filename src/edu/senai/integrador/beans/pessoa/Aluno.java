package edu.senai.integrador.beans.pessoa;

import java.time.LocalDate;

import edu.senai.integrador.logs.GeraLog;

public class Aluno extends Pessoa {
	private double altura;
	private double peso;

	public Aluno() {
	}

	public Aluno(String cpf, 
				 String nome, 
			  LocalDate dataDeNascimento, 
			  	  ESexo sexo, 
			  	 double altura, 
			  	 double peso,
		   EEstadoCivil estadoCivil) throws PessoaException, AlunoException {
		super.setCPF(cpf);
		super.setNome(nome);
		super.setDataDeNascimento(dataDeNascimento);
		super.setSexo(sexo);
		this.setAltura(altura);
		this.setPeso(peso);
		super.setEstadoCivil(estadoCivil);
	}

	public double getAltura() throws PessoaException, AlunoException {
		return altura;
	}

	public void setAltura(double altura) throws PessoaException, AlunoException {
		this.altura = altura;
	}

	public double getPeso() throws PessoaException, AlunoException {
		return peso;
	}

	public void setPeso(double peso) throws PessoaException, AlunoException {
		this.peso = peso;
	}

	public double getIMC() throws PessoaException, AlunoException {
		return this.getPeso() / Math.pow(getAltura(), 2);
	}

	@Override
	public String toString() {
		try {
			return super.toString() + "\nAltura____________ " + (getAltura())
					+ "\nPeso______________ " + (getPeso());
		} catch (PessoaException |AlunoException e) {
			new GeraLog().escreveLogArquivo(e);
		}
		return "Erro ao exportar os dados!";
	}
}
