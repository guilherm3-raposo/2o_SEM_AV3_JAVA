package edu.senai.integrador.dao.sql;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.senai.integrador.ferramentas.LeitorArquivo;

public class LeXml {
	public static String[] getTag(String nomeTag, String caminhoArquivo) {
		Document documento = getXml(LeitorArquivo.testaArquivo(caminhoArquivo));
		
		NodeList tags = documento.getElementsByTagName(nomeTag).item(0).getChildNodes();
		int tamanho = tags.getLength() / 2;
		String[] tabelas = new String[tamanho];
		for (int i = 0; i < tags.getLength() - 1; i++) {
			String tag = tags.item(i).getTextContent();
			tabelas[i / 2] = tag;
		}
		return tabelas;
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
