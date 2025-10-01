package persistence;

import java.util.List;
import java.util.Optional;

import core.Post;
import persistence.hibernate.HibernateRepository;

public class OrmPostRepository extends HibernateRepository implements PostRepository {
  @Override
  public void deleteById(Long id) {
    sessionFactory.inTransaction(session -> {
      Post post = session.find(Post.class, id);
      if (post != null) {
        session.remove(post);
      }
    });
  }

  @Override
  public boolean existsById(Long id) {
    return sessionFactory.fromTransaction(session -> {
      return session.find(Post.class, id) != null;
    });
  }

  @Override
  public List<Post> findAll() {
    return sessionFactory.fromTransaction(session -> {
      return session.createSelectionQuery("from Post", Post.class).getResultList();
    });
  }

  @Override
  public Optional<Post> findById(Long id) {
    return sessionFactory.fromTransaction(session -> {
      Post post = session.find(Post.class, id);
      return Optional.ofNullable(post);
    });
  }

  @Override
  public Post save(Post post) {
    sessionFactory.inTransaction(session -> {
      session.persist(post);
    });
    return post;
  }

  @Override
  public Post update(Post post) {
    return sessionFactory.fromTransaction(session -> {
      Post managedPost = session.find(Post.class, post.getId());
      if (managedPost != null) {
        managedPost.setContent(post.getContent());

        // Sync liked users properly
        managedPost.setLikedByUsers(post.getLikedByUsers());
        managedPost.setLikes(post.getLikes());
      }
      return managedPost;
    });
  }
}
