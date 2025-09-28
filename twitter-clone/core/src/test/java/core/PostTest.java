package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PostTest {
  @Test
  void testAllArgsConstructorAndGetters() {
    User user = new User(1L, "john", "John Doe", "pass");
    Post post = new Post(user, "Hello world", "p1");

    assertEquals("p1", post.getId());
    assertEquals(user, post.getOriginalPoster());
    assertEquals("Hello world", post.getContent());
    assertEquals(0, post.getLikes());
    assertEquals(0, post.getReTweets());
    assertEquals(0, post.getCommentsAmount());
  }

  @Test
  void testNoArgConstructorAndSetters() {
    User user = new User(2L, "jane", "Jane Doe", "pw");
    Post post = new Post();

    post.setId("p2");
    post.setOriginalPoster(user);
    post.setContent("New content");
    post.setLikes(5);
    post.setReTweets(2);
    post.setCommentsAmount(3);

    assertEquals("p2", post.getId());
    assertEquals(user, post.getOriginalPoster());
    assertEquals("New content", post.getContent());
    assertEquals(5, post.getLikes());
    assertEquals(2, post.getReTweets());
    assertEquals(3, post.getCommentsAmount());
  }

  @Test
  void testSetContentRejectsNull() {
    Post post = new Post();
    Exception ex = assertThrows(IllegalArgumentException.class, () -> post.setContent(null));
    assertEquals("Content cannot be null", ex.getMessage());
  }

  @Test
  void testSetContentRejectsEmptyString() {
    Post post = new Post();
    Exception ex = assertThrows(IllegalArgumentException.class, () -> post.setContent(""));
    assertEquals("Content can not be emtpy", ex.getMessage());
  }

  @Test
  void testSetContentRejectsTooLong() {
    Post post = new Post();
    String tooLong = "a".repeat(Post.MAX_CONTENT_LENGTH + 1);
    Exception ex = assertThrows(IllegalArgumentException.class, () -> post.setContent(tooLong));
    assertEquals("Content is to long", ex.getMessage());
  }

  @Test
  void testSetIdRejectsNull() {
    Post post = new Post();
    Exception ex = assertThrows(IllegalArgumentException.class, () -> post.setId(null));
    assertEquals("Id cannot be null", ex.getMessage());
  }

  @Test
  void testSetOriginalPosterRejectsNull() {
    Post post = new Post();
    Exception ex = assertThrows(IllegalArgumentException.class, () -> post.setOriginalPoster(null));
    assertEquals("User cannot be null", ex.getMessage());
  }

  @Test
  void testMaxContentLengthConstant() {
    assertEquals(280, Post.getMaxContentLength());
  }
}
