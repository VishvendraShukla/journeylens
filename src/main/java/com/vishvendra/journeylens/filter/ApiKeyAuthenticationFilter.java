package com.vishvendra.journeylens.filter;

import com.vishvendra.journeylens.service.apikey.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

  private final ApiKeyService apiKeyService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String apiKey = request.getHeader("X-API-KEY");
    if (apiKey == null || !apiKeyService.validateApiKey(apiKey)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid API Key");
      return;
    }
    filterChain.doFilter(request, response);

  }


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return !request.getRequestURI().matches("/api/v1/(events|sessions)/*");
  }
}
