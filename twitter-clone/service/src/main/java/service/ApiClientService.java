package service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service for making requests to a REST API.
 */
@Service
public class ApiClientService {
  private WebClient webClient;
  private String jwtToken;

  /**
   * Constructs an ApiClientService that hits localhost at port 8080.
   */
  public ApiClientService() {
    this.webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
  }

  /**
   * Sets the jwt token used for authentication.
   *
   * @param jwtToken the jwt token
   */
  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  /**
   * Sends a GET request to the API.
   *
   * @param <T>          the type of the response body
   * @param path         the API path
   * @param responseType the class of the response type
   * @return the response body deserialized into the type {@code T}
   */
  public <T> T get(String path, Class<T> responseType) {
    return this.webClient.get().uri(path).headers(headers -> {
      if (this.jwtToken != null) {
        headers.setBearerAuth(this.jwtToken);
      }
    }).retrieve().bodyToMono(responseType).block();
  }

  /**
   * Sends a GET request to the API.
   *
   * @param <T>          the type of the response body
   * @param path         the API path
   * @param responseType the class of the response type
   * @return the response body deserialized into the type {@code T}
   */
  public <T> T get(String path, ParameterizedTypeReference<T> responseType) {
    return this.webClient.get().uri(path).headers(headers -> {
      if (this.jwtToken != null) {
        headers.setBearerAuth(this.jwtToken);
      }
    }).retrieve().bodyToMono(responseType).block();
  }

  /**
   * Sends a POST request to the API.
   *
   * @param <T>          the type of the request body
   * @param <R>          the type of the response body
   * @param path         the API path
   * @param body         request body
   * @param responseType the class of the response type
   * @return the response body deserialized into the type {@code R}
   */
  public <T, R> R post(String path, T body, Class<R> responseType) {
    return this.webClient.post().uri(path).headers(headers -> {
      if (this.jwtToken != null) {
        headers.setBearerAuth(this.jwtToken);
      }
    }).bodyValue(body).retrieve().bodyToMono(responseType).block();
  }

  /**
   * Sends a POST request to the API.
   *
   * @param <T>          the type of the request body
   * @param <R>          the type of the response body
   * @param path         the API path
   * @param body         request body
   * @param responseType the class of the response type
   * @return the response body deserialized into the type {@code R}
   */
  public <T, R> R post(String path, T body, ParameterizedTypeReference<R> responseType) {
    return this.webClient.post().uri(path).headers(headers -> {
      if (this.jwtToken != null) {
        headers.setBearerAuth(this.jwtToken);
      }
    }).bodyValue(body).retrieve().bodyToMono(responseType).block();
  }
}
