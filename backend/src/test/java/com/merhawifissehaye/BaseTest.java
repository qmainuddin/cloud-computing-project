package com.merhawifissehaye;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
//@DataJpaTest
public class BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    protected void setup(WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    protected ObjectMapper objectMapper;

    protected Faker faker = new Faker(Locale.US);

    User signIn() {
        return signIn("test1@example.com", "password");
    }

    User signIn(String email, String password) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = User.builder().id(1L).email(email).password(password).build();
        Authentication auth = new UsernamePasswordAuthenticationToken(email, user.getPassword(), user.getAuthorities());
        securityContext.setAuthentication(auth);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        return user;
    }
}
