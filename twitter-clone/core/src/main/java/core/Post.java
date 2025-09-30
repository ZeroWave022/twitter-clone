package core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

  public static final int MAX_CONTENT_LENGTH = 280;

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
  private User author;
  private String content;
  // private Instant createdAt;

  private int likes = 0;
  private int reTweets = 0;
  private int commentsAmount = 0;

  @SuppressFBWarnings(value = "CT_CONSTRUCTOR_THROW")
  public Post(User user, String content, Long id) {
    setAuthor(user);
    setContent(content);
    setId(id);
    // this.createdAt = Instant.now();
  }

  public Post() {
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

  // public Instant getCreatedAt() {
  // return createdAt;
  // }

  public static int getMaxContentLength() {
    return MAX_CONTENT_LENGTH;
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP")
  public User getAuthor() {
    return author;
  }

  public int getReTweets() {
    return reTweets;
  }

  public Long getId() {
    return id;
  }

  public void setContent(String content) {
    if (content == null) {
      throw new IllegalArgumentException("Content cannot be null");
    }
    if (content.isEmpty()) {
      throw new IllegalArgumentException("Content can not be empty");
    }
    if (content.length() > MAX_CONTENT_LENGTH) {
      throw new IllegalArgumentException("Content is too long");
    }
    this.content = content;
  }

  public void setCommentsAmount(int commentsAmount) {
    this.commentsAmount = commentsAmount;
  }

  // public void setCreatedAt(Instant createdAt) {
  // this.createdAt = createdAt;
  // }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
  public void setAuthor(User author) {
    if (author == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    this.author = author;
  }

  public void setReTweets(int reTweets) {
    this.reTweets = reTweets;
  }
}
