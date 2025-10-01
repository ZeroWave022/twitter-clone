package core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

  // lazy fetch does not give hibernate/springboot access to post_likes db
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> likedByUsers = new HashSet<>();

  @SuppressFBWarnings(value = "CT_CONSTRUCTOR_THROW")
  public Post(User user, String content, Long id) {
    setAuthor(user);
    setContent(content);
    setId(id);
    // this.createdAt = Instant.now();
  }

  public Post() {
  }

  public void updateLikes(User user) {
    if (likedByUsers.contains(user)) {
      likedByUsers.remove(user);
    } else {
      likedByUsers.add(user);
    }
    likes = likedByUsers.size();
  }

  public boolean likedByUser(User user) {
    return likedByUsers.contains(user);
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

  public Set<User> getLikedByUsers() {
    return Collections.unmodifiableSet(likedByUsers);
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

  public void setLikedByUsers(Set<User> likedByUsers) {
    this.likedByUsers = new HashSet<>(likedByUsers);
  }

}
