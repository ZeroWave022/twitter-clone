package core.payload.request;

/**
 * DTO for logging in.
 *
 * @param username username
 * @param password password in plain text
 */
public record LoginRequest(String username, String password) {
}
