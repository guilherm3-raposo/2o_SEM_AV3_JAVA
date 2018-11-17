package edu.senai.integrador.z.populadores;

public class Populador {

	public static void main(String[] args) {
//		int numeroCriados = 20;
//		int i = 0;
//		Map<String, Aluno> alunos = new HashMap<String, Aluno>();
//		AlunoDAO alunoDAO = new AlunoDAO();
//
//		for (i = 0; i < numeroCriados; i++) {
//			alunos.put(String.valueOf(i), Instanciador.criaAluno(i));
//		}
//		try {
//			alunoDAO.insereVarios(alunos);
//		} catch (ConexaoException | DAOException e) {
//		}
//
//		Map<String, Funcionario> funcionarios = new HashMap<String, Funcionario>();
//		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
//		for (i = 0; i < numeroCriados; i++) {
//			funcionarios.put(String.valueOf(i), Instanciador.criaFuncionario(i));
//		}
//		try {
//			funcionarioDAO.insereVarios(funcionarios);
//		} catch (ConexaoException | DAOException e) {
//		}
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
//			String[] estados = LeXml.getTag("estados", "/enderecos.xml");
//
//			for (i = 0; i < estados.length; i++) {
//				st.execute("INSERT INTO estado VALUES('" + estados[i] + "');");
//			}
//			Random rand = new Random();
//			List<String> lista = LeTxt.getLista("/ruas.txt");
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
		
	}
}
