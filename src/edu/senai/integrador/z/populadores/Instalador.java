package edu.senai.integrador.z.populadores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;
import edu.senai.integrador.ferramentas.LeTxt;

public class Instalador {
	public static void instalaBanco(String nomeBanco) throws ConexaoException, IOException {
		try {
			Connection conexao = Conexao.abreConexao();
			Statement st = conexao.createStatement();

			StringBuffer linhas = new StringBuffer();
			LeTxt.getLista("/bancoInstalador.txt").forEach(string -> linhas.append(string));
			String[] linha = linhas.toString().replaceAll("projeto_integrador", nomeBanco).split(";");

			for (int i = 0; i < linha.length; i++) {
				st.execute(linha[i] + ";\n");
			}
		} catch (SQLException e) {
		} finally {
			Conexao.fechaConexao();
		}
	}
}
