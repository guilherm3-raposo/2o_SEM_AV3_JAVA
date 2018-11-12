package edu.senai.integrador.dao.sql;

public class TestaEnum {
	public static void main(String[] args) {
		for (int i = 0; i < EXmlColunas.values().length; i++) {
			System.out.println(EXmlColunas.values()[i].getCampo() + "\n");
		}
		System.out.println("___________________________________________________");
		for (int i = 0; i < EXmlTabelas.values().length; i++) {
			System.out.println(EXmlTabelas.values()[i].getTabela()+ "\n");
		}
	}
}
