package edu.senai.integrador.z.socket;

import java.io.IOException;
import java.net.ServerSocket;

public class Servidor {
	
	
	public ServerSocket criaPorta() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(9999);
			return serverSocket;
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	public String[] getAlunos() {
		return null;
		
//		AlunoDAO alunoDAO = new AlunoDAO();
//		try {
//			Map<String, Aluno> mapaAlunos = alunoDAO.consultaTodos();
//			List<Object> alunos = new ArrayList<Object>();
//			mapaAlunos.forEach((str, alu) -> alunos.add(alu));
//			return alunos;
//		} catch (ConexaoException | DAOException e) {
//			 TODO Auto-generated catch block
//		}
//		return null;
	}
}
