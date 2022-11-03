package program.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import program.config.util.SecurityUtils;
import program.dto.UserDTO;
import program.entity.User;
import program.model.UserDetails;
import program.service.UserService;


@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController     // http://localhost:8080
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    ModelMapper mapper;

    @GetMapping("/current")
    public UserDTO getCurrentUser() {
        UserDetails userDetails = SecurityUtils.getCurrentAuthenticatedUser();
        User user = service.findByEmail(userDetails.getEmail());
        return mapper.map(user, UserDTO.class);
    }
}
