package com.parkfinder.controller.mvc;

import com.parkfinder.model.dto.UserDTO;
import com.parkfinder.service.UserService;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserRegistrationControllerTest {

    @InjectMocks
    private UserRegistrationController userRegistrationController;
    @Mock
    private UserService userService;
    @Mock
    private Validator validator;
    private BindingResult bindingResult;
    private UserDTO userDTO;
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bindingResult = new BeanPropertyBindingResult(userDTO, "userDTO");

        userDTO = new UserDTO();
        userDTO.setId(123L);
        userDTO.setEmail("john.doe@example.com");
        userDTO.setUsername("John Doe");
        userDTO.setPassword("12345678");
        userDTO.setRoles("ROLE_USER");

        model = new org.springframework.ui.ConcurrentModel();
    }

    @Test
    void testRegister() {
        ModelAndView mav = userRegistrationController.register();
        assertEquals("registration-form", mav.getViewName());
        assertTrue(mav.getModel().get("user") instanceof UserDTO);
    }

    @Test
    void testProcessRegisterWithValidInput() {
        when(userService.isExistingUser(userDTO)).thenReturn(false);
        when(validator.validate(any(UserDTO.class))).thenReturn(null);

        String viewName = userRegistrationController.processRegister(userDTO, bindingResult, model);

        assertEquals("register-success-page", viewName);
    }

    @Test
    void testProcessRegisterWithInvalidInput() {
        // Set binding result error
        bindingResult.addError(new ObjectError("userDTO", "Invalid email"));

        // Get view name
        String viewName = userRegistrationController.processRegister(userDTO, bindingResult, model);

        // assert that the view name is correct and that the model contains the expected objects
        assertEquals("registration-form", viewName);
    }


    @Test
    void testProcessRegisterWithExistingUser() {
        when(userService.isExistingUser(userDTO)).thenReturn(true);
        when(validator.validate(any(UserDTO.class))).thenReturn(null);

        String viewName = userRegistrationController.processRegister(userDTO, bindingResult, model);

        assertEquals("registration-form", viewName);
        assertEquals(userDTO, model.getAttribute("user"));
        assertEquals("An account with this email already exists", model.getAttribute("userExistsMessage"));
    }

    @Test
    void testProcessRegisterWithEmptyRole() {
        userDTO.setRoles(null);

        when(validator.validate(any(UserDTO.class))).thenReturn(null);

        String viewName = userRegistrationController.processRegister(userDTO, bindingResult, model);

        assertEquals("registration-form", viewName);
        assertEquals(userDTO, model.getAttribute("user"));
        assertEquals("Select role", model.getAttribute("emptyRole"));
    }
}
