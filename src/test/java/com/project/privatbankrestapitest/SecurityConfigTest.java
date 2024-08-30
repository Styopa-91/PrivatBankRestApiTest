package com.project.privatbankrestapitest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAccessDeniedForUnauthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/some-endpoint"))
                .andExpect(status().isUnauthorized()); // Expect 401 Unauthorized
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAccessGrantedForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk()); // Expect 200 OK
    }
}
