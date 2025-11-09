package api;

import core.payload.request.CreatePostRequest;
import core.payload.request.LoginRequest;
import core.payload.response.LoginResponse;
import core.payload.response.PostResponse;
import core.payload.response.UserResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest()
class PostControllerTest {
  private RestTestClient restClient;
  private String jwt;
  private UserResponse user;

  @BeforeEach
  void setUp(WebApplicationContext context) {
    restClient = RestTestClient.bindToApplicationContext(context)
        .configureServer(mockMvc -> mockMvc.apply(SecurityMockMvcConfigurers.springSecurity())).build();

    LoginRequest request = new LoginRequest("test user for api", "password");

    LoginResponse response = restClient.post().uri("/auth/login").body(request).exchange()
        .returnResult(LoginResponse.class).getResponseBody();

    jwt = response.jwtToken();

    user = restClient.get().uri("/auth/me").header("Authorization", "Bearer " + jwt).exchange()
        .returnResult(UserResponse.class).getResponseBody();
  }

  @Test
  void testPostCreation() {
    CreatePostRequest request = new CreatePostRequest("my post", null);
    PostResponse post = restClient.post().uri("/api/posts").header("Authorization", "Bearer " + jwt).body(request)
        .exchange()
        .expectStatus().isOk()
        .expectBody(PostResponse.class)
        .returnResult()
        .getResponseBody();

    assertEquals("my post", post.content());
    assertNotNull(post.id());
    assertEquals(user.id(), post.author().id());

    CreatePostRequest retweetRequest = new CreatePostRequest("my retweet", post.id());
    PostResponse retweet = restClient.post().uri("/api/posts").header("Authorization", "Bearer " + jwt)
        .body(retweetRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(PostResponse.class)
        .returnResult()
        .getResponseBody();

    assertEquals(post.id(), retweet.originalPostId());

    PostResponse updatedPost = restClient.get().uri("/api/posts/" + post.id()).header("Authorization", "Bearer " + jwt)
        .exchange()
        .returnResult(PostResponse.class).getResponseBody();

    assertEquals(1, updatedPost.reTweets());
  }

  @Test
  void testLikePost() {
    CreatePostRequest request = new CreatePostRequest("my post", null);
    PostResponse post = restClient.post().uri("/api/posts").header("Authorization", "Bearer " + jwt).body(request)
        .exchange()
        .returnResult(PostResponse.class)
        .getResponseBody();

    PostResponse likedPost = restClient.post().uri("/api/posts/" + post.id() + "/likes")
        .header("Authorization", "Bearer " + jwt).exchange().expectStatus().isOk().expectBody(PostResponse.class)
        .returnResult().getResponseBody();

    assertEquals(1, likedPost.likes());
    assertTrue(likedPost.likedByUser(user.id()));
  }
}
