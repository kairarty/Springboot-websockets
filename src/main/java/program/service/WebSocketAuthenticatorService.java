package program.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import program.config.JWTTokenUtil;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class WebSocketAuthenticatorService {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    JWTTokenUtil jwtTokenUtil;

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String token) throws AuthenticationException {
        if (token == null && token.isBlank() && !token.startsWith("Bearer ")) {
            throw new AuthenticationCredentialsNotFoundException("Token is incorrect");
        }
        String jwt = token.substring(7);
        String email = jwtTokenUtil.validateTokenAndRetrieveSubject(jwt);
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
                userDetails.getAuthorities());
    }
}
