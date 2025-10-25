package api.config;

import api.service.JwtUtilsService;
import api.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Security filter that reads a jwt token from the Authorization header, and
 * adds the validated user to the security context.
 */
@Service
public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtilsService jwtUtilsService;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private RequestMatcher ignoredPaths = PathPatternRequestMatcher.pathPattern("/auth/login");

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (ignoredPaths.matches(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String jwt = parseJwt(request);

      if (jwt == null || !jwtUtilsService.validateJwtToken(jwt)) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().flush();
        return;
      }

      String username = jwtUtilsService.getUsernameFromJwtToken(jwt);

      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (UsernameNotFoundException e) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getWriter().flush();
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }

    return null;
  }
}
