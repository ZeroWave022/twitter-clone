package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Post;
import core.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Integration test for {@link OrmPostRepository}. */
class OrmPostRepositoryTest {
  private OrmUserRepository userRepository = new OrmUserRepository();
  private OrmPostRepository postRepository = new OrmPostRepository();
  private Post post;

  @BeforeEach
  void setup() {
    postRepository.dropDatabase();

    // Initialize test user and post
    User user = new User(null, "username", "display name", "password123");
    Post post = new Post(user, "Another post", null);
    this.post = post;
    userRepository.save(post.getAuthor());

  }

  @Test
  void test_emptyState() {
    List<Post> users = this.postRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addPost() {
    this.postRepository.save(post);
    assertTrue(post.getId() != null);

    List<Post> posts = this.postRepository.findAll();
    assertEquals(1, posts.size());
  }

  @Test
  void test_modifyPost() {
    Post savedPost = this.postRepository.save(post);

    String newMessage = "Even more important message";
    savedPost.setContent(newMessage);
    this.postRepository.update(savedPost);

    List<Post> posts = this.postRepository.findAll();
    assertEquals(1, posts.size());
    Optional<Post> fetchedPost = this.postRepository.findById(savedPost.getId());
    assertEquals(newMessage, fetchedPost.get().getContent());
  }

  @Test
  @DisplayName("User likes a post")
  void test_likePostIncrementsLikes() {
    postRepository.save(post);

    // Like the post
    postRepository.likePost(post, post.getAuthor());

    Post updatedPost = postRepository.findById(post.getId(), true).orElseThrow();
    assertEquals(1, updatedPost.getLikes());
    assertTrue(updatedPost.getLikedByUsers().contains(post.getAuthor()));
  }

  @Test
  @Transactional
  @DisplayName("User likes and then unlikes a post")
  void test_likePostToggles() {
    postRepository.save(post);

    // Like once
    postRepository.likePost(post, post.getAuthor());
    Post likedPost = postRepository.findById(post.getId()).orElseThrow();
    assertEquals(1, likedPost.getLikes());

    // Unlike
    postRepository.likePost(post, post.getAuthor());
    Post unlikedPost = postRepository.findById(post.getId(), true).orElseThrow();
    assertEquals(0, unlikedPost.getLikes());
    assertFalse(unlikedPost.getLikedByUsers().contains(post.getAuthor()));
  }

  @Test
  void test_deleteById() {
    postRepository.save(post);
    assertEquals(postRepository.findById(post.getId()).get().getId(), post.getId());
    postRepository.deleteById(12345L);
    assertEquals(postRepository.findById(post.getId()).get().getId(), post.getId());
    postRepository.deleteById(post.getId());
    assertTrue(postRepository.findById(post.getId()).isEmpty());
  }

  @Test
  void test_updateRetweetCount() {
    post.setReTweets(123);
    postRepository.save(post);
    assertEquals(postRepository.findById(post.getId()).get().getReTweets(), 123);
    post.setReTweets(123456);
    postRepository.updateRetweetCount(post);
    assertEquals(postRepository.findById(post.getId()).get().getReTweets(), 123456);
  }

  @Test
  void test_existsById() {
    postRepository.save(post);
    assertTrue(postRepository.existsById(post.getId()));
  }

  @Test
  void test_findAllWithRelations() {
    postRepository.save(post);
    postRepository.likePost(post, post.getAuthor());
    List<Post> posts = postRepository.findAll(true);
    assertEquals(1, posts.size());
    assertTrue(posts.get(0).getLikedByUsers().contains(post.getAuthor()));
  }
}
