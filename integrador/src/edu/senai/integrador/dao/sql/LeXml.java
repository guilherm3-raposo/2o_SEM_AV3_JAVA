package edu.senai.integrador.dao.sql;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeXml {
	static Document documento = getXml(setArquivo());

	public static String[] getTag(String nomeTag) {
		NodeList tags = documento.getElementsByTagName(nomeTag).item(0).getChildNodes();
		int tamanho = tags.getLength() / 2;
		String[] tabelas = new String[tamanho];
		for (int i = 0; i < tags.getLength() - 1; i++) {
			String tag = tags.item(i).getTextContent();
			tabelas[i / 2] = tag;
		}
		return tabelas;
	}
	
	private static File setArquivo() {
		String workspace = System.getProperty("user.dir");
		File dados = new File(workspace + "/dados");
		File config = new File(dados + "/configBanco.xml");

		if (!dados.exists()) {
			dados.mkdir();
		}
		if (config.exists() && !config.isDirectory()) {
			return config;
		} else {
			try {
				config.createNewFile();
				return config;
			} catch (IOException e) {

			}
		}
		return config;
	}

	private static Document getXml(File config) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(config);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
