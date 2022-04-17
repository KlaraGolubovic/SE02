package org.hbrs.academicflow.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Encryption {
  public static String sha256(String base) throws NoSuchAlgorithmException {
    final MessageDigest digest = MessageDigest.getInstance("SHA-256");
    final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
    final StringBuilder hexString = new StringBuilder();
    for (byte value : hash) {
      final String hex = Integer.toHexString(0xff & value);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
