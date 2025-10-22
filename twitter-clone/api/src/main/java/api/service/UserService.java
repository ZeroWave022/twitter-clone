package api.service;

import core.User;
import core.payload.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  public UserResponse toDTO(User user) {
    return new UserResponse(user.getId(), user.getUsername(), user.getDisplayName());
  }
}
