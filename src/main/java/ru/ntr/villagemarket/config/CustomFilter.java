package ru.ntr.villagemarket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ntr.villagemarket.model.service.SecurityService;
import ru.ntr.villagemarket.model.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {

    private static final String AUTH_PREFIX = "Bearer ";
    private final SecurityService securityService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(AUTH_PREFIX)) {
            final String token = header.replaceFirst(AUTH_PREFIX, "");

            String username = "";

            try {
                username = securityService.verifyJwt(token);


                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(username,
                                null,
                                userService.loadUserByUsername(username).getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (ExpiredJwtException e) {
                 response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token has expired.");
                 response.setHeader("message", "Token has expired.");
                return;
              //  responseError(response, "Token has expired");
            } catch (JwtException e) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token.");
                response.setHeader("message", "Invalid token.");
               // responseError(response, "Invalid token");
                return;
            }




        }

            filterChain.doFilter(request, response);



    }


    //TO
    private void responseError(HttpServletResponse response, String errorMessage) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", errorMessage);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            mapper.writeValue(response.getWriter(), errorDetails);
        } catch (IOException e) {
            //TODO handle error
        }

    }


}
