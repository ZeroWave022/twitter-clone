package core.payload.response;

/**
 * DTO for users returned from the API.
 *
 * @param id          database row id
 * @param username    username
 * @param displayName display name
 */
public record UserResponse(Long id, String username, String displayName) {
}
