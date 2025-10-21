package api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * A utility service for generating and validating jwt tokens.
 */
@Component
public class JwtUtilsService {
  @Value("${jwtSecret}")
  private String jwtSecret;

  @Value("${jwtExpirationMs}")
  private int jwtExpirationMs;

  /**
   * Generates a jwt token for a given user.
   *
   * @param authentication a wrapper around the user details
   * @return the generated jwt token
   */
  public String generateJwtToken(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(getKey())
        .compact();
  }

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  /**
   * Extracts the username from a jwt token.
   *
   * @param token a jwt token
   * @return the extracted username
   */
  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  /**
   * Check if a jwt token is valid.
   *
   * @param token a jwt token
   * @return whether the token is valid
   */
  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().verifyWith(getKey()).build().parse(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
