package edu.senai.integrador.z.populadores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.ferramentas.LeTxt;
import edu.senai.integrador.ferramentas.LeituraTerminal;

public class Populador {

	public static void main(String[] args) {
		int numeroCriados = 20;
		int i = 0;
		Map<String, Aluno> alunos = new HashMap<String, Aluno>();
		AlunoDAO alunoDAO = new AlunoDAO();

		for (i = 0; i < numeroCriados; i++) {
			alunos.put(String.valueOf(i), Instanciador.criaAluno(i));
		}
		System.out.println("\n" + ++i + " Alunos criados, deseja inserir no banco? (S/N)");
		try {
			char confirma = LeituraTerminal.leString().toLowerCase().charAt(0);
			if (confirma == 's') {
				alunoDAO.insereVarios(alunos);
			}
		} catch (ConexaoException | DAOException e) {
			e.printStackTrace();
		}

		Map<String, Funcionario> funcionarios = new HashMap<String, Funcionario>();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		for (i = 0; i < 20; i++) {
			funcionarios.put(String.valueOf(i), Instanciador.criaFuncionario(i));
		}
		System.out.println("\n" + ++i + " Funcionarios criados, deseja inserir no banco? (S/N)");
		try {
			char confirma = LeituraTerminal.leString().toLowerCase().charAt(0);
			if (confirma == 's') {
				funcionarioDAO.insereVarios(funcionarios);
			}
		} catch (ConexaoException | DAOException e) {
			e.printStackTrace();
		}

		System.out.println("fim");
		
		List<String> cidades = LeTxt.getLista("/cidades.txt");
		cidades.forEach(nome -> );
	}
}
