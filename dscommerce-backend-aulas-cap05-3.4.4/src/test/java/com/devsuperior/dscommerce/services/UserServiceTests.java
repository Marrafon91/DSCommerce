package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.devsuperior.dscommerce.tests.UserDetailsFactory;
import com.devsuperior.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private String existingUsername, nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";
        user = UserFactory.createCustomClientUser(1L, existingUsername);

        userDetails = UserDetailsFactory.createCustomAdminUser(existingUsername);

        Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(List.of());
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUsernameExists() {
        UserDetails result = service.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingUsername, result.getUsername());
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
     Assertions.assertThrows(UsernameNotFoundException.class,
             () -> service.loadUserByUsername(nonExistingUsername));
    }
}
