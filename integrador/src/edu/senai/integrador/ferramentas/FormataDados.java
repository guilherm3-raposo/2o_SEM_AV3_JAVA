package edu.senai.integrador.ferramentas;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import edu.senai.integrador.beans.enumeradores.EEndereco;

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

	public static String formataEndereco(EEndereco logradouro, String endereco, int numeroCasa, String complemento) {
		return logradouro.getDescricao() + " " + endereco + " nº" + numeroCasa + " " + complemento;
	}

	public static String formataTelefone(String telefone) {
		StringBuffer telefoneFormatado = new StringBuffer();
		for (int i = 0; i < telefone.length(); i++) {
			if (telefone.length() > 10) {
				if (i == 0)
					telefoneFormatado.append('(');
				if (i == 2)
					telefoneFormatado.append(')');
				if (i == 5 || i == 8)
					telefoneFormatado.append('-');
			}
			if (telefone.length() < 10) {
				if (i == 0)
					telefoneFormatado.append("(XX)");
				if (i == 3 || i == 6 || i == 9)
					telefoneFormatado.append('-');
			}
			telefoneFormatado.append(telefone.charAt(i));
		}
		return telefoneFormatado.toString();
	}

//	public static double getIMC(double peso, double altura) {
//		DecimalFormat df = new DecimalFormat("###.##");
//		return Double.valueOf(df.format(peso / Math.pow(altura, 2)));
//	}

	public static String formataAltura(double altura) {
		DecimalFormat df = new DecimalFormat("#.00");
		String texto = df.format(altura);
		return texto.charAt(0) + " metro e " + texto.substring(2, 4) + " centímetros";
	}

	public static String formataPeso(double peso) {
		return String.format("%.2f", peso) + "Kg";
	}

	public static String formataCtps(String ctps) {
		// TODO validarRegexTamanhosETalz
		// 333.666666.22-22????
		return ctps;
	}
}
