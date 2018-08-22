package multicastapplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class KeyPairGen {
private PrivateKey priv;
private PublicKey pub;

public PrivateKey getPriv() {
return priv;
}

public PublicKey getPub() {
return pub;
}

public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

public KeyPairGen() {
try {

final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
final SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
keyGen.initialize(1024, random);
KeyPair pair = keyGen.generateKeyPair();
this.priv = pair.getPrivate();
this.pub = pair.getPublic();

} catch (Exception e) {
e.printStackTrace();
}

}

}