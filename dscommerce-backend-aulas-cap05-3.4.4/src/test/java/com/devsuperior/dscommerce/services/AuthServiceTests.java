package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService service;

    @Mock
    private UserService userService;

    private User admin, selfCilent, otherCilent;

    @BeforeEach
    void setUp() {
        admin = UserFactory.createAdminUser();
        selfCilent = UserFactory.createCustomClientUser(1L, "Bob");
        otherCilent = UserFactory.createCustomClientUser(2L, "Ana");
    }

    @Test
    void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {
        Mockito.when(userService.authenticated()).thenReturn(admin);
        Long userId = admin.getId();
        Assertions.assertDoesNotThrow(() -> service.validateSelfOrAdmin(userId));
    }

    @Test
    void validateSelfOrAdminShouldDoNothingWhenSelfCilentLogged() {
        Mockito.when(userService.authenticated()).thenReturn(selfCilent);
        Long userId = selfCilent.getId();
        Assertions.assertDoesNotThrow(() -> service.validateSelfOrAdmin(userId));
    }

    @Test
    void validateSelfOrAdminThrowsForbiddenExceptionWhenOtherCilentLogged() {
        Mockito.when(userService.authenticated()).thenReturn(selfCilent);
        Long userId = otherCilent.getId();
        Assertions.assertThrows(ForbiddenException.class, () -> service.validateSelfOrAdmin(userId));
    }
}
