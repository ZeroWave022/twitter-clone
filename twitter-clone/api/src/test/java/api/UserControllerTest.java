package api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.payload.request.LoginRequest;
import core.payload.request.UpdateUserRequest;
import core.payload.response.LoginResponse;
import core.payload.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest()
class UserControllerTest {
  private RestTestClient restClient;
  private String jwt;
  private UserResponse user;

  @BeforeEach
  void setUp(WebApplicationContext context) {
    restClient = RestTestClient.bindToApplicationContext(context)
        .configureServer(mockMvc -> mockMvc.apply(SecurityMockMvcConfigurers.springSecurity()))
        .build();

    LoginRequest request = new LoginRequest("user controller tester :)", "password");

    LoginResponse response = restClient.post().uri("/auth/login").body(request).exchange()
        .returnResult(LoginResponse.class).getResponseBody();

    jwt = response.jwtToken();

    user = restClient.get().uri("/auth/me").header("Authorization", "Bearer " + jwt).exchange()
        .returnResult(UserResponse.class).getResponseBody();
  }

  @Test
  void getUserWithId() {
    UserResponse userResponse = restClient.get().uri("/api/users/" + user.id())
        .header("Authorization", "Bearer " + jwt).exchange().expectBody(UserResponse.class)
        .returnResult().getResponseBody();

    assertEquals(user.id(), userResponse.id());
    assertEquals(user.username(), userResponse.username());
  }

  @Test
  void testUpdateUser() {
    UpdateUserRequest request = new UpdateUserRequest("new username", "new display name",
        "new password");
    UserResponse updatedUser = restClient.put().uri("/api/users/" + user.id())
        .header("Authorization", "Bearer " + jwt).body(request).exchange().expectStatus().isOk()
        .expectBody(UserResponse.class).returnResult().getResponseBody();

    assertEquals(user.id(), updatedUser.id());
    assertEquals("new username", updatedUser.username());
    assertEquals("new display name", updatedUser.displayName());
  }
}
