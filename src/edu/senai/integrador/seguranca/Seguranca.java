package edu.senai.integrador.seguranca;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class Seguranca {
	private static Configuracoes config = new Configuracoes();
	private static Properties prop = config.carrega(true);
	private static String serialMatcher = "F2OC4NAB4LANC4";

	public static boolean confereSerial(String serial) throws SegurancaException, IOException {
		if (serial.trim().matches(serialMatcher)) {
			prop.setProperty("seguranca.isRegistered", "true");
			config.salva(prop, true);
			return true;
		} else {
			throw new SegurancaException();
		}
	}

	public static boolean getRegistro() throws IOException {
		Configuracoes config = new Configuracoes();
		Properties prop = config.carrega(true);
		return prop.getProperty("seguranca.isRegistered").matches("true");
	}

	public static void assinaArquivo(File arquivo, File chave, File assinatura) throws NoSuchAlgorithmException,
			NoSuchProviderException, InvalidKeyException, SignatureException, IOException {

		
		KeyPairGenerator geradorChave = KeyPairGenerator.getInstance("DSA", "SUN");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

		geradorChave.initialize(1024, random);

		KeyPair pair = geradorChave.generateKeyPair();
		PrivateKey priv = pair.getPrivate();
		PublicKey pub = pair.getPublic();

		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		dsa.initSign(priv);
		FileInputStream fis = new FileInputStream(arquivo);
		BufferedInputStream bufin = new BufferedInputStream(fis);
		byte[] buffer = new byte[1024];
		int len;
		while (bufin.available() != 0) {
			len = bufin.read(buffer);
			dsa.update(buffer, 0, len);
		}
		bufin.close();
		byte[] realSig = dsa.sign();
		FileOutputStream sigfos = new FileOutputStream(assinatura);
		sigfos.write(realSig);
		sigfos.close();
		byte[] key = pub.getEncoded();
		FileOutputStream keyfos = new FileOutputStream(chave);
		keyfos.write(key);

		keyfos.close();
	}

	public static boolean validaAssinatura(File arquivo, File chave, File assinatura) {
		boolean verifies = false;
		try {
			FileInputStream keyfis = new FileInputStream(chave);
			byte[] encKey = new byte[keyfis.available()];
			keyfis.read(encKey);

			keyfis.close();

			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

			FileInputStream sigfis = new FileInputStream(assinatura);
			byte[] sigToVerify = new byte[sigfis.available()];
			sigfis.read(sigToVerify);
			sigfis.close();

			Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
			sig.initVerify(pubKey);

			FileInputStream datafis = new FileInputStream(arquivo);
			BufferedInputStream bufin = new BufferedInputStream(datafis);

			byte[] buffer = new byte[1024];
			int len;
			while (bufin.available() != 0) {
				len = bufin.read(buffer);
				sig.update(buffer, 0, len);
			}

			bufin.close();

			verifies = sig.verify(sigToVerify);
		} catch (Exception ex) {
		}
		return verifies;
	}
}
