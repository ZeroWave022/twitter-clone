package service;

import core.Post;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.PostRepository;

@Service
public class PostService {
  private PostRepository postRepository;

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject the PostRepository service")
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Transactional(readOnly = true)
  public Optional<Post> getPostById(String id) {
    return this.postRepository.findById(id);
  }

  @Transactional
  public Post createPost(Post post) {
    boolean postExists = this.postRepository.findById(post.getId()).isPresent();
    if (postExists) {
      throw new IllegalArgumentException("Id \"" + post.getId() + "\" already exists");
    }

    return this.postRepository.save(post);
  }

  @Transactional(readOnly = true)
  public List<Post> getAllPosts() {
    return this.postRepository.findAll();
  }
}
