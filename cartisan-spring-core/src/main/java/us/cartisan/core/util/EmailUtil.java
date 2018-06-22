package us.cartisan.core.util;

import javax.mail.internet.InternetAddress;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CharMatcher;

/**
 * @author  Hyunju Shin
 */
public class EmailUtil {
	private EmailUtil() {}

	public static String toEncodedAddress(String str) {
		try {
			InternetAddress ia = new InternetAddress(str);
			String name = ia.getPersonal();
			String addr = ia.getAddress();
			return toEncodedAddress(addr, name);
		} catch (Exception e) {
			return str;
		}
	}

	public static String toEncodedAddress(String addr, String name) {
		try {
			if (StringUtils.isEmpty(name)) {
				return addr;
			} else {
				if (CharMatcher.ASCII.matchesAllOf(name)) {
					return name + " <" + addr + ">";
				} else {
					String ename = Base64.encodeBase64String(name.getBytes("utf-8"));
					return "=?utf-8?B?" + ename + "?= <" + addr + ">";
				}
			}
		} catch (Exception e) {
			return addr;
		}
	}
}
