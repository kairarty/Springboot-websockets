package program.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import program.config.JWTTokenUtil;
import program.dto.UserDTO;
import program.entity.User;
import program.repository.UserRepository;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JWTTokenUtil jwtTokenUtil;

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }

    public ResponseEntity<?> register(User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        String token = jwtTokenUtil.generateToken(user.getEmail());
        user.setPassword(encodedPass);
        try {
            user = repository.save(user);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>("Duplicate email!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setToken("Bearer " + token);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }
}
