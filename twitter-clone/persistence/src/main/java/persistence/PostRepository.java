package persistence;

import java.util.List;
import java.util.Optional;

import core.Post;

public interface PostRepository {
  Optional<Post> findById(String id);

  List<Post> findAll();

  Post save(Post post);

  void deleteById(String id);

  boolean existsById(String id);
}
