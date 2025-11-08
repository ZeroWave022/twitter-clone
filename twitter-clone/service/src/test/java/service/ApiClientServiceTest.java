package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

class ApiClientServiceTest {

  private static final String jwtToken = "jwtToken";
  private static final String testEndpoint = "/test";
  private static final String stringResponse = "\"ok\"";
  private static final Integer intResponse = 123;

  private static MockWebServer mockWebServer;
  private ApiClientService apiClientService;

  private MockResponse jsonResponse(String body) {
    return new MockResponse().setBody(body).addHeader("Content-Type", "application/json");
  }

  private void verifyRequest(RecordedRequest request, String method, String path, boolean hasToken)
      throws Exception {
    assertEquals(method, request.getMethod());
    assertEquals(path, request.getPath());
    if (hasToken) {
      assertEquals("Bearer " + jwtToken, request.getHeader("Authorization"));
    } else {
      assertNull(request.getHeader("Authorization"));
    }
  }

  @BeforeAll
  static void setUpAll() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void tearDownAll() throws IOException {
    mockWebServer.shutdown();
  }

  @BeforeEach
  void setup() {
    String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
    apiClientService = new ApiClientService(baseUrl);
  }

  @Test
  void getWithToken() throws Exception {
    apiClientService.setJwtToken(jwtToken);
    mockWebServer.enqueue(jsonResponse(stringResponse));

    String result = apiClientService.get(testEndpoint, String.class);
    assertEquals(stringResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "GET", testEndpoint, true);
  }

  @Test
  void getWithoutToken() throws Exception {
    mockWebServer.enqueue(jsonResponse(stringResponse));

    String result = apiClientService.get(testEndpoint, String.class);
    assertEquals(stringResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "GET", testEndpoint, false);
  }

  @Test
  void postWithToken() throws Exception {
    apiClientService.setJwtToken(jwtToken);
    mockWebServer.enqueue(jsonResponse(stringResponse));

    String result = apiClientService.post(testEndpoint, "payload", String.class);
    assertEquals(stringResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "POST", testEndpoint, true);
  }

  @Test
  void postWithoutToken() throws Exception {
    mockWebServer.enqueue(jsonResponse(stringResponse));

    String result = apiClientService.post(testEndpoint, "payload", String.class);
    assertEquals(stringResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "POST", testEndpoint, false);
  }

  @Test
  void getWithParameterizedTypeWithToken() throws Exception {
    apiClientService.setJwtToken(jwtToken);
    mockWebServer.enqueue(jsonResponse(String.valueOf(intResponse)));

    Integer result = apiClientService.get(testEndpoint, new ParameterizedTypeReference<Integer>() {
    });
    assertEquals(intResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "GET", testEndpoint, true);
  }

  @Test
  void getWithParameterizedTypeWithoutToken() throws Exception {
    mockWebServer.enqueue(jsonResponse(String.valueOf(intResponse)));

    Integer result = apiClientService.post(testEndpoint, intResponse,
        new ParameterizedTypeReference<Integer>() {
        });
    assertEquals(intResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "POST", testEndpoint, false);
  }

  @Test
  void postWithParameterizedTypeWithToken() throws Exception {
    apiClientService.setJwtToken(jwtToken);
    mockWebServer.enqueue(jsonResponse(String.valueOf(intResponse)));

    Integer result = apiClientService.post(testEndpoint, intResponse,
        new ParameterizedTypeReference<Integer>() {
        });
    assertEquals(intResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "POST", testEndpoint, true);
  }

  @Test
  void postWithParameterizedTypeWithoutToken() throws Exception {
    mockWebServer.enqueue(jsonResponse(String.valueOf(intResponse)));

    Integer result = apiClientService.post(testEndpoint, intResponse,
        new ParameterizedTypeReference<Integer>() {
        });
    assertEquals(intResponse, result);

    verifyRequest(mockWebServer.takeRequest(), "POST", testEndpoint, false);
  }
}