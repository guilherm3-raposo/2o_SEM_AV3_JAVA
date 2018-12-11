package edu.senai.integrador.ferramentas;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormataDados {

	public static String formataCpf(String cpf) {
		String cpfFormatado = "";
		for (int i = 0; i < 11; i++) {
			cpfFormatado += (String.valueOf(cpf.charAt(i)));
			cpfFormatado += (i == 2 || i == 5) ? "." : i == 8 ? "-" : "";
		}
		return cpfFormatado;
	}

	public static String formataData(LocalDate data) {
		return String.valueOf(data.format(DateTimeFormatter.ofPattern("dd/MMM/uuuu")));
	}

	public static String formataTelefone(String telefone) {
		switch (telefone.length()) {
		case 8: 
			return formataFixoSemDDD(telefone);
		case 10:
			return formataFixoComDDD(telefone);
		case 9:
			return formataMovelSemDDD(telefone);
		case 11:
			return formataMovelComDDD(telefone);
		default: 
			return "Telefone Inválido";
		}
	}
	
	private static String formataMovelComDDD(String telefone) {
		StringBuffer telefoneFormatado = new StringBuffer();
		for(int i = 0; i < telefone.length(); i++) {
			if(i == 0) {
				telefoneFormatado.append("(");
			} else if(i == 2) {
				telefoneFormatado.append(")");
			} else if(i == 7) {
				telefoneFormatado.append("-");
			}
			telefoneFormatado.append(telefone.charAt(i));
		}
		return telefoneFormatado.toString();
	}
	
	private static String formataMovelSemDDD(String telefone) {
		StringBuffer telefoneFormatado = new StringBuffer();
		for(int i = 0; i < telefone.length(); i++) {
			if(i == 0) {
				telefoneFormatado.append("(XX)");
			} else if(i == 5) {
				telefoneFormatado.append("-");
			}
			telefoneFormatado.append(telefone.charAt(i));
		}
		return telefoneFormatado.toString();
	}
	
	private static String formataFixoComDDD(String telefone) {
		StringBuffer telefoneFormatado = new StringBuffer();
		for(int i = 0; i < telefone.length(); i++) {
			if(i == 0) {
				telefoneFormatado.append("(");
			} else if (i == 2) {
				telefoneFormatado.append(")");
			} else if(i == 6) {
				telefoneFormatado.append("-");
			}
			telefoneFormatado.append(telefone.charAt(i));
		}
		return telefoneFormatado.toString();
	}
	
	private static String formataFixoSemDDD(String telefone) {
		StringBuffer telefoneFormatado = new StringBuffer();
		for(int i = 0; i < telefone.length(); i++) {
			if(i == 0) {
				telefoneFormatado.append("(XX)");
			} else if(i == 4) {
				telefoneFormatado.append("-");
			}
			telefoneFormatado.append(telefone.charAt(i));
		}
		return telefoneFormatado.toString();
	}

	public static String formataAltura(double altura) {
		DecimalFormat df = new DecimalFormat("#.00");
		String texto = df.format(altura);
		return texto.charAt(0) + " metro e " + texto.substring(2, 4) + " centímetros";
	}

	public static String formataPeso(double peso) {
		return String.format("%.2f", peso) + "Kg";
	}
}