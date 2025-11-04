package api;

import core.payload.request.LoginRequest;
import core.payload.response.LoginResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import core.payload.response.UserResponse;

@SpringBootTest()
class AuthControllerTest {

  private RestTestClient restClient;

  @BeforeEach
  void setUp(WebApplicationContext context) {
    restClient = RestTestClient.bindToApplicationContext(context)
        .configureServer(mockMvc -> mockMvc.apply(SecurityMockMvcConfigurers.springSecurity()))
        .baseUrl("/auth").build();
  }

  @Test
  void login() {
    LoginRequest request = new LoginRequest("test user for api", "password");

    restClient.post()
        .uri("/login")
        .body(request)
        .exchange()
        .expectStatus().isOk()
        .expectBody(LoginResponse.class)
        .returnResult().getResponseBody();

    LoginRequest invalidRequest = new LoginRequest("test user for api", "wrong");

    restClient.post()
        .uri("/login")
        .body(invalidRequest)
        .exchange()
        .expectStatus().isForbidden();
  }

  @Test
  void getUserData() {
    LoginRequest request = new LoginRequest("test user for api", "password");

    LoginResponse response = restClient.post().uri("/login").body(request).exchange().returnResult(LoginResponse.class)
        .getResponseBody();

    UserResponse user = restClient.get().uri("/me").header("Authorization",
        "Bearer " + response.jwtToken()).exchange()
        .expectBody(UserResponse.class)
        .returnResult().getResponseBody();

    assertEquals(request.username(), user.username());
  }

  @Test
  void handleUnauthedUser() {
    restClient.get().uri("/me").exchange().expectStatus().isForbidden();
  }
}
