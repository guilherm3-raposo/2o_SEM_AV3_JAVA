package edu.senai.integrador.seguranca;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

import edu.senai.integrador.ferramentas.Configuracoes;

public class Seguranca {
	private static Configuracoes config = new Configuracoes();
	private static Properties prop = config.carrega(true);
	private static final String serialMatcher = "QRKBWVNAK2VUDV52E63BBY2GTKECSD266";
	
	public static boolean setRegistro(String serial) {
		if (serial.trim().matches(serialMatcher)) {
			prop.setProperty("seguranca.isRegistered", "true");
			config.salva(prop, true);
			return true;
		}
		return false;
	}

	public static boolean getRegistro() {
		return prop.getProperty("seguranca.isRegistered").matches("true");
	}

	public static void signFile(File arquivo, File pkey, File sign) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			PrivateKey chavePrivada = keyPair.getPrivate();
			PublicKey chavePublica = keyPair.getPublic();

			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initSign(chavePrivada);
			FileInputStream fis = new FileInputStream(arquivo);
			BufferedInputStream bufin = new BufferedInputStream(fis);
			byte[] buffer = new byte[1024];
			int len = bufin.read(buffer);
			while (len >= 0) {
				dsa.update(buffer, 0, len);
			}
			bufin.close();
			byte[] realSig = dsa.sign();

			FileOutputStream sigfos = new FileOutputStream(sign);
			sigfos.write(realSig);
			sigfos.close();

			byte[] key = chavePublica.getEncoded();
			FileOutputStream keyfos = new FileOutputStream(pkey);
			keyfos.write(key);
			keyfos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean fileIsValid(File file, File pkey, File sign) {
		boolean verifies = false;
		try {
			FileInputStream keyfis = new FileInputStream(pkey);
			byte[] encKey = new byte[keyfis.available()];
			keyfis.read(encKey);

			keyfis.close();

			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

			FileInputStream sigfis = new FileInputStream(sign);
			byte[] sigToVerify = new byte[sigfis.available()];
			sigfis.read(sigToVerify);
			sigfis.close();

			Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
			sig.initVerify(pubKey);

			FileInputStream datafis = new FileInputStream(file);
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
			ex.printStackTrace();
		}
		return verifies;
	}
}
