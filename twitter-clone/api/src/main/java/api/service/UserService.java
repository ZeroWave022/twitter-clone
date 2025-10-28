package api.service;

import core.User;
import core.payload.response.UserResponse;
import org.springframework.stereotype.Service;

/**
 * Uility service for converting {@link User} objects to DTOs.
 */
@Service
public class UserService {
  /**
   * Constructs a User DTO.
   *
   * @param user the user
   * @return the user DTO
   */
  public UserResponse toDto(User user) {
    return new UserResponse(user.getId(), user.getUsername(), user.getDisplayName());
  }
}
