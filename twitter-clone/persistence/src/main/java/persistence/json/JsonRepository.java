package persistence.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonRepository<T> {
  private final ObjectMapper objectMapper;
  private final Path filePath;
  private final TypeReference<List<T>> typeReference;

  public JsonRepository(Path filePath, TypeReference<List<T>> typeReference) {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.findAndRegisterModules();

    this.filePath = filePath;
    this.typeReference = typeReference;
  }

  public List<T> load() throws IOException {
    File file = this.filePath.toFile();
    if (!file.exists() || file.length() == 0) {
      return new ArrayList<>();
    }

    return this.objectMapper.readValue(file, this.typeReference);
  }

  public void save(List<T> data) throws IOException {
    this.objectMapper.writeValue(this.filePath.toFile(), data);
  }
}
