package persistence;

import java.util.List;
import java.util.Optional;

import core.Post;

public interface PostRepository {
  Optional<Post> findById(Long id);

  List<Post> findAll();

  Post save(Post post);

  Post update(Post post);

  void deleteById(Long id);

  boolean existsById(Long id);
}
