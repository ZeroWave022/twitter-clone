package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

import core.Post;
import persistence.json.JsonRepository;

@Repository
public class JsonPostRepository implements PostRepository {
  private final JsonRepository<Post> jsonRepository;
  private List<Post> posts;

  public JsonPostRepository(@Value("${app.data.directory}") String dataDirPath) {
    Path dataDir = Paths.get(dataDirPath);
    try {
      Files.createDirectories(dataDir);
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.jsonRepository = new JsonRepository<>(dataDir.resolve("posts.json"), new TypeReference<List<Post>>() {

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

  public void addPost(Post newPost) {
    this.posts.add(newPost);
    this.savePosts();
  }

  @Override
  public Optional<Post> findById(String id) {
    return this.posts.stream().filter(post -> post.getId().equals(id)).findFirst();
  }

  @Override
  public List<Post> findAll() {
    return new ArrayList<>(this.posts);
  }

  @Override
  public Post save(Post post) {
    this.addPost(post);
    return post;
  }

  @Override
  public void deleteById(String id) {
    this.posts.removeIf(post -> post.getId().equals(id));
  }

  @Override
  public boolean existsById(String id) {
    return this.findById(id).isPresent();
  }
}
