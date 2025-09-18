package service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import core.Post;
import core.User;
import persistence.PostRepository;

@ExtendWith(MockitoExtension.class)

public class PostServiceTest {
  @Mock
  private PostRepository postRepository;

  @InjectMocks
  private PostService postService;

  private User user;
  private String postID;
  private Post post;

  @BeforeEach
  void setup() {
    postService = new PostService(postRepository);
    this.user = new User(1L, "john", "John Pork", "pass");
    this.postID = "123";
    this.post = new Post(this.user, "hello world", postID);
  }

  @Test
  void getPostByIdReturnsEmptyWhenNotFound() {

    when(postRepository.findById(postID)).thenReturn(Optional.empty());
    Optional<Post> result = postService.getPostById(postID);
    assertTrue(result.isEmpty());

  }

  @Test
  void getPostByIdReturnsPostWhenFound() {

    when(postRepository.findById(postID)).thenReturn(Optional.of(post));
    Optional<Post> result = postService.getPostById(postID);
    assertEquals(result.get(), post);

  }

  @Test
  void createPostSavesPostWhenIDIsUnique() {
    when(postRepository.findById(postID)).thenReturn(Optional.empty());
    when(postRepository.save(post)).thenReturn(post);

    Post result = assertDoesNotThrow(() -> postService.createPost(post));
    assertEquals(result, post);
  }

  @Test
  void createPostThrowsExceptionWhenIDExists() {
    when(postRepository.findById(postID)).thenReturn(Optional.of(post));

    assertThrows(IllegalArgumentException.class, () -> postService.createPost(post));
  }
}
