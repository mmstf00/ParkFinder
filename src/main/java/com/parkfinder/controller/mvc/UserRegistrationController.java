package com.parkfinder.controller.mvc;

import com.parkfinder.model.dto.UserDTO;
import com.parkfinder.service.ExtendedUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserRegistrationController {
    private final ExtendedUserService userService;

    private static final String REGISTRATION_FORM = "registration-form";
    private static final String REGISTER_SUCCESS = "register-success-page";

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView(REGISTRATION_FORM).addObject("user", new UserDTO());
    }

    @PostMapping("/process_register")
    public String processRegister(@Valid UserDTO userDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return REGISTRATION_FORM;
        }

        if (userService.isExistingUser(userDTO)) {
            model.addAttribute("user", userDTO);
            model.addAttribute("userExistsMessage", "An account with this email already exists");
            return REGISTRATION_FORM;
        }

        if (userDTO.getRoles() == null) {
            model.addAttribute("user", userDTO);
            model.addAttribute("emptyRole", "Select role");
            return REGISTRATION_FORM;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        userDTO.setPassword(encodedPassword);
        userDTO.setRoles(userDTO.getRoles());
        userService.saveUser(userDTO);

        return REGISTER_SUCCESS;
    }
}
