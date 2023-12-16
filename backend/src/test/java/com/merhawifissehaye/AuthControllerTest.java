package com.merhawifissehaye;

import com.merhawifissehaye.controller.AuthController;
import com.merhawifissehaye.model.User;
import com.merhawifissehaye.service.UserService;
import com.merhawifissehaye.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {
    private MockMvc mvc;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;

    @BeforeAll
    public void setup() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenUtil = mock(JwtTokenUtil.class);
        userService = mock(UserService.class);
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(authenticationManager, jwtTokenUtil, userService)).build();
    }

    @Test
    public void testLogin() throws Exception {
//        User user = new User();
//        user.setId(1L);
//        user.setEmail("merhawifissehaye@gmail.com");
//        user.setName("Merhawi Fissehaye");
//        user.setPassword("password");
//        user.setBalance(1000);
//
//        when(userService.save(user)).thenReturn(user);
//        mvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("email", "merhawifissehaye@gmail.com")
//                        .param("password", user.getPassword()))
//                .andExpect(status().isOk());
    }
}
