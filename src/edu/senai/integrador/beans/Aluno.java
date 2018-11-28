package edu.senai.integrador.beans;

import java.time.LocalDate;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.enumeradores.EEstadoCivil;
import edu.senai.integrador.beans.enumeradores.ESexo;
import edu.senai.integrador.beans.exception.AlunoException;
import edu.senai.integrador.beans.exception.FuncionarioException;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.DAOException;

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
		return super.toString() + "\nAltura____________ " + (getAltura())
				+ "\nPeso______________ " + (getPeso());
	}
	
	public static void main(String[] args) throws PessoaException, ConexaoException, DAOException {
		Aluno aluno = new Aluno();
		aluno.setCPF("99999999999");
		aluno.setAltura(1);
		aluno.setAtivo(true);
		aluno.setDataDeNascimento(LocalDate.now());
		aluno.setEstadoCivil(EEstadoCivil.DIVORCIADO);
		aluno.setNome("Janaina");
		aluno.setPeso(9);
		aluno.setSexo(ESexo.MASCULINO);
		System.out.println(aluno);
		AlunoDAO alunoDAO = new AlunoDAO();
		alunoDAO.insere(aluno);
//		alunoDAO.exclui(aluno);
	}
}
