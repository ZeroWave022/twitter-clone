package service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.Post;
import persistence.PostRepository;

@Service
public class PostService {
  private PostRepository postRepository;

  @Autowired
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
}
