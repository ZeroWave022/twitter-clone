package api.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import javax.crypto.SecretKey;

/**
 * Utility class for generating a jwt secret key.
 */
public class JwtKeyGenerator {
  /**
   * Generates a jwt secret key for use in the API.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SecretKey key = Jwts.SIG.HS512.key().build();

    String jwtSecret = Encoders.BASE64.encode(key.getEncoded());

    System.out.println("Copy this into application.properties");
    System.out.println("jwtSecret=" + jwtSecret);
  }
}
