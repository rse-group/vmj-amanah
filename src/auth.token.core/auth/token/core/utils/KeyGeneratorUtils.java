package auth.token.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyGeneratorUtils {
    private static void generateKey(String algorithm, int keyLength) {
		if (!(fileExists("private_key.pem") && fileExists("public_key.pem"))) {
			try {
				KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
				keyGen.initialize(keyLength);
				KeyPair keyPair = keyGen.generateKeyPair();
				byte[] privateKey = keyPair.getPrivate().getEncoded();
				byte[] publicKey = keyPair.getPublic().getEncoded();
				writePEMFile("PRIVATE KEY", privateKey, "private_key.pem");
				writePEMFile("PUBLIC KEY", publicKey, "public_key.pem");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean fileExists(String path) {
		return Files.exists(Paths.get(path));
	}

	private static void writePEMFile(String type, byte[] keyBytes, String outFile) {
		String keyString = Base64.getEncoder().encodeToString(keyBytes);
		int chunkSize = 64;

        StringBuilder pemBuilder = new StringBuilder();
        pemBuilder.append("-----BEGIN ").append(type).append("-----\n");
		for (int i = 0; i < keyString.length(); i += chunkSize)
			pemBuilder.append(keyString, i, Math.min(i + chunkSize, keyString.length())).append("\n");
        pemBuilder.append("-----END ").append(type).append("-----\n");
		
		try (FileWriter fileWriter = new FileWriter(outFile)) {
			fileWriter.write(pemBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();;
		}
    }

	public static PrivateKey getPrivateKey(String algorithm, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyGeneratorUtils.generateKey(algorithm, keyLength);
		String privateKey = new String();
        try {
            privateKey = new String(Files.readAllBytes(Paths.get("private_key.pem")), Charset.defaultCharset());
			Pattern pattern = Pattern.compile("-----.*?-----", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(privateKey);
			privateKey = matcher.replaceAll("").replaceAll("\\r\\n|\\r|\\n", "").trim();
        } catch (IOException | InvalidPathException e) {
            e.printStackTrace();
        }
		
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		byte[] keyBytes = Base64.getDecoder().decode(privateKey);
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		return keyFactory.generatePrivate(privateKeySpec);
	}

	public static PublicKey getPublicKey(String algorithm, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyGeneratorUtils.generateKey(algorithm, keyLength);
		String publicKey = new String();
        try {
            publicKey = new String(Files.readAllBytes(Paths.get("public_key.pem")), Charset.defaultCharset());
			Pattern pattern = Pattern.compile("-----.*?-----", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(publicKey);
			publicKey = matcher.replaceAll("").replaceAll("\\r\\n|\\r|\\n", "").trim();
        } catch (IOException | InvalidPathException e) {
            e.printStackTrace();
        }
    	
    	KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(publicKeySpec);
	}

	public static String getPublicKey() {
		String publicKey = new String();
		try {
            publicKey = new String(Files.readAllBytes(Paths.get("public_key.pem")), Charset.defaultCharset());
			publicKey = publicKey.replaceAll("\\r\\n|\\r|\\n", "").trim();
        } catch (IOException | InvalidPathException e) {
            e.printStackTrace();
        }
		return publicKey;
	}
}
