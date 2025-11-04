package core.payload.response;

/**
 * DTO for a login response.
 *
 * @param jwtToken jwt token for use in authentication
 */
public record LoginResponse(String jwtToken) {
}
