package edu.senai.integrador.z.populadores;

import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.beans.Aluno;
import edu.senai.integrador.dao.DAOException;

public class Populador {

	public static void main(String[] args) throws ConexaoException, DAOException {
		int numeroCriados = 20;
		int i = 0;
		
		Aluno aluno = Instanciador.criaAluno();
		System.out.println("teste");
		System.out.println(Instanciador.criaAluno());
//		System.out.println(aluno);
//		alunoDAO.insereVarios(Instanciador.criaAluno(5));
//		List<Aluno> alunos = Instanciador.criaAluno(numeroCriados);
//		alunos.forEach(aluno -> {
//			try {
//				System.out.println(aluno);
//				alunoDAO.insere(aluno);
//			} catch (ConexaoException | DAOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
//		
//		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
//		List<Funcionario> funcionarios = Instanciador.criaFuncionario(numeroCriados);
//		funcionarios.forEach(funcionario -> {
//			try {
//				funcionarioDAO.insere(funcionario);
//			} catch (ConexaoException | DAOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});	
//		
//		Connection conexao;
//		try {
//			conexao = Conexao.getConexao();
//			Statement st = conexao.createStatement();
//			conexao.setAutoCommit(false);
//
//			List<String> cidades = LeTxt.getLista("/cidades.txt");
//			cidades.forEach(nome -> {
//				try {
//					st.execute("INSERT INTO cidade VALUES ('" + nome + "');");
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//				}
//			});
//
//			for (i = 0; i < EEndereco.values().length; i++) {
//				st.execute("INSERT INTO logradouro VALUES('" + EEndereco.values()[i].getSigla() + "');");
//			}
//
//			String[] estados = LeXml.getTag("estados", "/populadorEnderecos.xml");
//
//			for (i = 0; i < estados.length; i++) {
//				st.execute("INSERT INTO estado VALUES('" + estados[i] + "');");
//			}
//			Random rand = new Random();
//			List<String> lista = LeTxt.getLista("/populadorRuas.txt");
//			String[] num = lista.get(0).split(",");
//			String[] dat = lista.get(1).split(",");
//			String[] tit = lista.get(2).split(",");
//			String[] nom1 = lista.get(3).split(",");
//			String[] nom2 = lista.get(4).split(",");
//
//			try {
//				for (int j = 0; j < numeroCriados; j++) {
//					System.out.println("INSERT INTO via VALUES('" + num[rand.nextInt(num.length - 1)] + "de "
//							+ dat[rand.nextInt(dat.length - 1)] + "');");
//					st.execute("INSERT INTO via VALUES('" + num[rand.nextInt(num.length - 1)]
//							+ dat[rand.nextInt(dat.length - 1)] + "');");
//				}
//				for (int j = 0; j < numeroCriados; j++) {
//					System.out.println("INSERT INTO via VALUES('" + tit[rand.nextInt(tit.length - 1)]
//							+ nom1[rand.nextInt(nom1.length - 1)] + nom2[rand.nextInt(nom2.length - 1)] + "');");
//					st.execute("INSERT INTO via VALUES('" + tit[rand.nextInt(tit.length - 1)]
//							+ nom1[rand.nextInt(nom1.length - 1)] + nom2[rand.nextInt(nom2.length - 1)] + "');");
//				}
//			} catch (Exception e2) {
//				// TODO: handle exception
//			}
//			conexao.commit();
//		} catch (ConexaoException | SQLException e) {
//			// TODO Auto-generated catch block
//		}
//
	}
}
