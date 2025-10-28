package core.payload.request;

/**
 * DTO for updating a user.
 *
 * @param username    new username
 * @param displayName new display name
 * @param password    new plain text password
 */
public record UpdateUserRequest(String username, String displayName, String password) {
}
