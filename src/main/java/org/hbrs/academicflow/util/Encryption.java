package org.hbrs.academicflow.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Encryption {

  public static String sha256(String value) {
    return DigestUtils.sha256Hex(value);
  }

  // a method which encodes images in base64
  public static byte[] encodeImage(byte[] image) {
    return Base64.encode(image);
  }
}
