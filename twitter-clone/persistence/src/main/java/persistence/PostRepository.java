package persistence;

import core.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
  Optional<Post> findById(String id);

  List<Post> findAll();

  Post save(Post post);

  void deleteById(String id);

  boolean existsById(String id);
}
