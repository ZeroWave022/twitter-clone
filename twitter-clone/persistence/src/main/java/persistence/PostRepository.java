package persistence;

import core.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
  Optional<Post> findById(Long id);

  List<Post> findAll();

  Post save(Post post);

  void deleteById(Long id);

  boolean existsById(Long id);
}
