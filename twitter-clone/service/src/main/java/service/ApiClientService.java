package service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiClientService {
  private WebClient webClient;
  private String jwtToken;

  public ApiClientService() {
    this.webClient = WebClient.builder()
        .baseUrl("http://localhost:8080")
        .build();
  }

  public <T> T get(String path, Class<T> responseType) {
    return this.webClient.get()
        .uri(path)
        .headers(headers -> {
          if (this.jwtToken != null) {
            headers.setBearerAuth(this.jwtToken);
          }
        })
        .retrieve()
        .bodyToMono(responseType)
        .block();
  }

  public <T, R> R post(String path, T body, Class<R> responseType) {
    return this.webClient.post()
        .uri(path)
        .headers(headers -> {
          if (this.jwtToken != null) {
            headers.setBearerAuth(this.jwtToken);
          }
        })
        .bodyValue(body)
        .retrieve()
        .bodyToMono(responseType)
        .block();
  }
}
