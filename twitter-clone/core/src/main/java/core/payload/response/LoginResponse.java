package core.payload.response;

/**
 * A DTO for a login response.
 */
public class LoginResponse {
  private String jwtToken;

  /**
   * Constructs a {@link LoginResponse}.
   *
   * @param jwtToken a jwt token
   */
  public LoginResponse(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }
}
