package edu.senai.integrador.z.populadores;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.Funcionario;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.dao.FuncionarioDAO;
import edu.senai.integrador.ferramentas.LeTxt;

public class Instalador {
	public static List<String> instalaBanco () {
		List<String> script = LeTxt.getLista("/bancoInstalador.txt");
		System.out.println(LeTxt.getLista("/bancoInstalador.txt"));
		try {
			Connection conexao = Conexao.getConexao();
			Statement st = conexao.createStatement();
			for (int i = 0; i < script.size(); i++) {
				st.execute(script.get(i));
			}
		} catch (SQLException e) {
			System.out.println("deubosta " + e.getMessage());
		} catch (ConexaoException e) {
			System.out.println("deuoutrabosta " + e.getMessage());
		}
		return script;
	}
	
	public static boolean populaAlunos(int numeroAlunos) {
		Map<String, Aluno> alunos = new HashMap<String, Aluno>();
		AlunoDAO alunoDAO = new AlunoDAO();

		for (int i = 0; i < numeroAlunos; i++) {
//			alunos.put(String.valueOf(i), Instanciador.criaAluno(i));
		}
		try {
			alunoDAO.insereVarios(alunos);
		} catch (ConexaoException | DAOException e) {
			
		}
		return true;
	}
	
	public static boolean populaFuncionarios(int numeroFuncionarios) {
		Map<String, Funcionario> funcionarios = new HashMap<String, Funcionario>();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		
		for (int i = 0; i < numeroFuncionarios; i++) {
//			funcionarios.put(String.valueOf(i), Instanciador.criaFuncionario(i));
		}
		try {
			funcionarioDAO.insereVarios(funcionarios);
		} catch (ConexaoException | DAOException e) {
			
		}
		return true;
	}
	
	public static void main(String[] args) {
		List<String> script = instalaBanco();
		populaAlunos(20);
		populaFuncionarios(20);
		System.out.println(script);
	}
}
