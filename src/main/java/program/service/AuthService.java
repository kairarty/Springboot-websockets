package program.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import program.config.JWTTokenUtil;
import program.dto.UserDTO;
import program.entity.User;
import program.model.LoginCredentials;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class AuthService {
    @Autowired
    UserService userService;
    @Autowired
    JWTTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<?> getLoginUser(LoginCredentials credentials) {
        final String email = credentials.getEmail();
        UsernamePasswordAuthenticationToken authInputToken
                = new UsernamePasswordAuthenticationToken(email, credentials.getPassword());
        try {
            authManager.authenticate(authInputToken);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Email or password are incorrect!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        String token = jwtTokenUtil.generateToken(email);
        User user = userService.findByEmail(email);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setToken("Bearer " + token);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }
}
