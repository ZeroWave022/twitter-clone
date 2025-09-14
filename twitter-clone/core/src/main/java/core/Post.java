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


    public Post(User user,
                String content) {
        if (user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if (content == null){
            throw new IllegalArgumentException("Content cannot be null");
        }
        if (content.isEmpty()){
            throw new IllegalArgumentException("Content can not be emtpy");
        }
        if (content.length() > MAX_CONTENT_LENGTH){
            throw new IllegalArgumentException("Content is to long");
        }
        this.originalPoster = user;
        this.content = content;
        this.createdAt = Instant.now();
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
}