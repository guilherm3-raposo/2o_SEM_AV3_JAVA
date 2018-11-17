package edu.senai.integrador.testes;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.beans.exception.PessoaException;
import edu.senai.integrador.dao.AlunoDAO;
import edu.senai.integrador.dao.DAOException;
import edu.senai.integrador.ferramentas.LeituraTerminal;

public class TestaAluno {
	public static void main(String[] args) throws PessoaException, ConexaoException, DAOException {
		Aluno aluno = new Aluno();

//		System.out.print("Digite o CPF do Aluno_____________________ ");
//		aluno.setCPF(LeituraTerminal.leCpf());
//
//		System.out.print("Digite o nome do aluno____________________ ");
//		aluno.setNome(LeituraTerminal.leNome());

		System.out.print("Digite a data de nascimento do aluno______ ");
		aluno.setDataDeNascimento(LeituraTerminal.leData(true));
		System.out.println(aluno.getDataDeNascimento() + "\n" + aluno.getIdadeCompleta());
//
//		System.out.print("Digite o sexo do aluno (M/F)______________ ");
//		aluno.setSexo(LeituraTerminal.leSexo());
//
//		System.out.print("Digite a altura do aluno__________________ ");
//		aluno.setAltura(LeituraTerminal.leDouble());
//
//		System.out.print("Digite o peso do aluno____________________ ");
//		aluno.setPeso(LeituraTerminal.leDouble());
//
//		System.out.println("1- solteiro(a)\t2- casado(a)\t3- divorciado(a)\t4- separado(a)");
//		System.out.print("Digite o estado civil do aluno____________ ");
//		aluno.setEstadoCivil(LeituraTerminal.leEstadoCivil());

//		System.out.println("1- Rua\t2- Avenida\t3- Alameda\t4- Rodovia\t5-Vila\n");
//		System.out.print("Digite o código do Logradouro_____________ ");
//		aluno.setLogradouro(LeituraTerminal.leLogradouro());
//
//		System.out.print("Digite o endereço_________________________ ");
//		aluno.setEndereco(LeituraTerminal.leString());
//
//		System.out.print("Digite o número___________________________ ");
//		aluno.setNumeroCasa(LeituraTerminal.leInt());
//
//		System.out.print("Digite o complemento______________________ ");
//		aluno.setComplemento(LeituraTerminal.leString());
//
//		System.out.print("Digite o telefone do aluno________________ ");
//		aluno.setTelefone(LeituraTerminal.leString());
//
//		System.out.print("Digite o email do aluno___________________ ");
//		aluno.setEmail(LeituraTerminal.leString());
//
//		System.out.println("\n" + xanaina.toString());
//		System.out.println(aluno.toString());
//		
//		AlunoDAO alunoDAO = new AlunoDAO();
//		Aluno aluno = alunoDAO.consulta("99988877700");
//		aluno.setCPF("99988877766");
//		aluno.setSexo(ESexo.MASCULINO);
//		alunoDAO.insere(aluno);
//		alunoDAO.exclui("99988877766");
//		System.out.println(aluno.toString());

	}
}
