package core.payload.request;

public record UpdateUserRequest(String username, String displayName, String password) {
}
