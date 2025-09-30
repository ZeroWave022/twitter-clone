package service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import core.Post;
import core.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.PostRepository;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
  @Mock
  private PostRepository postRepository;

  @InjectMocks
  private PostService postService;

  private User user;
  private Long postId;
  private Post post;

  @BeforeEach
  void setup() {
    postService = new PostService(postRepository);
    this.user = new User(1L, "john", "John Pork", "pass");
    this.postId = 123L;
    this.post = new Post(this.user, "hello world", postId);
  }

  @Test
  void getPostByIdReturnsEmptyWhenNotFound() {
    when(postRepository.findById(postId)).thenReturn(Optional.empty());
    Optional<Post> result = postService.getPostById(postId);
    assertTrue(result.isEmpty());
  }

  @Test
  void getPostByIdReturnsPostWhenFound() {
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    Optional<Post> result = postService.getPostById(postId);
    assertEquals(result.get(), post);
  }

  @Test
  void createPostSavesPostWhenIdIsUnique() {
    when(postRepository.findById(postId)).thenReturn(Optional.empty());
    when(postRepository.save(post)).thenReturn(post);

    Post result = assertDoesNotThrow(() -> postService.createPost(post));
    assertEquals(result, post);
  }

  @Test
  void createPostThrowsExceptionWhenIdExists() {
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    assertThrows(IllegalArgumentException.class, () -> postService.createPost(post));
  }
}
