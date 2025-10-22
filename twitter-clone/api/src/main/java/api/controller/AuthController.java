package api.controller;

import api.service.JwtUtilsService;
import core.User;
import core.payload.request.LoginRequest;
import core.payload.response.LoginResponse;
import core.payload.response.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.UserRepository;

/**
 * Controller for authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtilsService jwtUtilsService;

  /**
   * Authenticates a User. Creates a new user if the given username is not in use.
   *
   * @param loginRequest the user credentials
   * @return jwt auth token
   */
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    if (userRepository.findByUsername(loginRequest.getUsername()).isEmpty()) {
      User user = new User(null, loginRequest.getUsername(), loginRequest.getUsername(),
          passwordEncoder.encode(loginRequest.getPassword()));

      userRepository.save(user);
    }

    Authentication authenticaton = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authenticaton);
    String jwt = jwtUtilsService.generateJwtToken(authenticaton);

    return ResponseEntity.ok(new LoginResponse(jwt));
  }

  @GetMapping("/me")
  public ResponseEntity<?> getAuthenticatedUser() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    User user = userRepository.findByUsername(userDetails.getUsername()).get();

    return ResponseEntity.ok(new UserResponse(user.getId(), user.getUsername(), user.getDisplayName()));
  }
}
