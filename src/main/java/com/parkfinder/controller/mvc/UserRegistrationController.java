package com.parkfinder.controller.mvc;

import com.parkfinder.entity.User;
import com.parkfinder.exception.UserExistsException;
import com.parkfinder.model.UserDTO;
import com.parkfinder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserRegistrationController {
    private final UserService userService;

    private static final String REGISTRATION_FORM = "registration-form";

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView(REGISTRATION_FORM).addObject("user", new User());
    }

    @PostMapping("/process_register")
    public String processRegister(UserDTO userDTO) throws UserExistsException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        userDTO.setPassword(encodedPassword);
        userDTO.setRoles(userDTO.getRoles());
        userService.saveUser(userDTO);

        return "register-success-page";
    }
}
