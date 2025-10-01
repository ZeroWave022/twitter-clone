package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.Post;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import persistence.PostRepository;

@Service
public class PostService {
  private PostRepository postRepository;

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject "
      + "the PostRepository service")
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Transactional(readOnly = true)
  public Optional<Post> getPostById(Long id) {
    return this.postRepository.findById(id);
  }

  @Transactional
  public Post createPost(Post post) {
    if (post == null) {
      throw new IllegalArgumentException("Post cannot be null");
    }

    // Make sure we don't overwrite an existing post
    if (post.getId() != null) {
      boolean postExists = this.postRepository.findById(post.getId()).isPresent();
      if (postExists) {
        throw new IllegalArgumentException("Post with id \"" + post.getId() + "\" already exists");
      }
    }

    return this.postRepository.save(post);
  }

  @Transactional(readOnly = true)
  public List<Post> getAllPosts() {
    return this.postRepository.findAll();
  }

  @Transactional
  public void likePost(Post post) {
    post.setLikes(post.getLikes() + 1);
    postRepository.update(post);
  }

  public Post update(Post post) {
    return postRepository.update(post);
  }

  // public Post removeLike(Post currentPost, User loggedInUser) {
  // return postRepository.removeLike();
  // }
}
