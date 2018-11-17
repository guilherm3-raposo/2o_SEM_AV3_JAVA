package edu.senai.integrador.z.populadores;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import edu.senai.integrador.bancodedados.conexao.Conexao;
import edu.senai.integrador.bancodedados.conexao.ConexaoException;

public class Instalador {
	public static void instalaBanco () {
		try {
			Connection conexao = Conexao.getConexao();
			Statement st = conexao.createStatement();
			
		} catch (SQLException | ConexaoException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static void main(String[] args) {
		
	}
}
