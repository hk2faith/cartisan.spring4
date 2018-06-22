package us.cartisan.core.util.cipher;

import java.security.Key;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.CharEncoding;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
public class AesCipher {
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String KEY_ALGORITHM = "AES";
	private static final String IV = "band_email_2017!";
	private static final String DEFAULT_CHARSET = CharEncoding.UTF_8;

	private Cipher encrypter;
	private Cipher decrypter;

	private AtomicBoolean isInitialized = new AtomicBoolean(false);

	public void init(String key) {
		if (isInitialized.get())
			return;

		try {
			encrypter = Cipher.getInstance(CIPHER_ALGORITHM);
			decrypter = Cipher.getInstance(CIPHER_ALGORITHM);

			Key keySpec = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), KEY_ALGORITHM);
			IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(DEFAULT_CHARSET));
			encrypter.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			decrypter.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		} catch (Exception e) {
			log.error("can't initialize a EmailCipher", e);
			return;
		}

		isInitialized.set(true);
	}

	public String encrypt(String src) {
		if (!isInitialized.get()) {
			log.error("not been initialized.");
			return null;
		}
		try {
			byte[] encrypted = encrypter.doFinal(src.getBytes(DEFAULT_CHARSET));
			return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			log.warn("can't encrypt a src: {}", src, e);
			return null;
		}
	}

	public String decrypt(String src) {
		if (!isInitialized.get()) {
			log.error("not been initialized.");
			return null;
		}
		try {
			byte[] encrypted = Base64.decodeBase64(src);
			byte[] decrypted = decrypter.doFinal(encrypted);
			return new String(decrypted, DEFAULT_CHARSET);
		} catch (Exception e) {
			log.warn("can't encrypt a src: {}", src, e);
			return null;
		}
	}
}
