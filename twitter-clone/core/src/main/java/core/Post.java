package core;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Post {

    public static final int MAX_CONTENT_LENGTH = 280;

    private String id;
    private User originalPoster;
    private String content;
    private Instant createdAt;

    private int likes = 0;
    private int reTweets = 0;
    private int commentsAmount = 0;

    public Post(User user, String content, String id) {
        setOriginalPoster(user);
        setContent(content);
        setId(id);
        this.createdAt = Instant.now();
    }

    public Post(){
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public int getCommentsAmount() {
        return commentsAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public static int getMaxContentLength() {
        return MAX_CONTENT_LENGTH;
    }

    public User getOriginalPoster() {
        return originalPoster;
    }

    public int getReTweets() {
        return reTweets;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Content can not be emtpy");
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("Content is to long");
        }
        this.content = content;
    }

    public void setCommentsAmount(int commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setOriginalPoster(User originalPoster) {
        if (originalPoster == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.originalPoster = originalPoster;
    }

    public void setReTweets(int reTweets) {
        this.reTweets = reTweets;
    }
}