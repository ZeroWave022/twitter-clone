package api.utils;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class JwtKeyGenerator {
  public static void main(String[] args) {
    SecretKey key = Jwts.SIG.HS512.key().build();

    String jwtSecret = Encoders.BASE64.encode(key.getEncoded());

    System.out.println("Copy this into application.properties");
    System.out.println("jwtSecret=" + jwtSecret);
  }
}
