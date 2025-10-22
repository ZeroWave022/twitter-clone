package core.payload.response;

public record PostResponse(Long id, String content, int likes, int reTweets, int commentsAmount, UserResponse author) {
}
