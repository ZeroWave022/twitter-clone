package persistence;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;

import core.Post;
import persistence.json.JsonRepository;

public class PostRepository {
  private final JsonRepository<Post> jsonRepository;
  private List<Post> posts;

  public PostRepository(Path dataDirPath) {
    this.jsonRepository = new JsonRepository<>(dataDirPath.resolve("posts.json"), new TypeReference<List<Post>>() {

    });

    // FIXME: Don't load every post into memory :)
    this.loadPosts();
  }

  private void loadPosts() {
    try {
      this.posts = this.jsonRepository.load();
    } catch (IOException e) {
      this.posts = new ArrayList<>();
    }
  }

  public void savePosts() {
    try {
      this.jsonRepository.save(this.posts);
    } catch (IOException e) {

    }
  }

  public List<Post> getAllPosts() {
    return new ArrayList<>(this.posts);
  }

  public Optional<Post> getPostById(String id) {
    return this.posts.stream().filter(post -> post.getId().equals(id)).findFirst();
  }

  public boolean addPost(Post newPost) {
    if (this.getPostById(newPost.getId()).isPresent()) {
      return false;
    }

    this.posts.add(newPost);
    this.savePosts();
    return true;
  }
}
