package com.example.demo.Model;

import com.example.demo.Utility.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class UsersTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    public  static List<Users> users;

    private MvcResult result;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setup() {
        users = Arrays.asList(
                Users.builder().id_user(1L).username("asdaasdsad").password("asdasda123").first_name("asdsad1").last_name("Asdasdad").email("asdsa@yahoo.com").date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Users.builder().id_user(2L).username("asdaasdsad").password("asdasda123").first_name("asdsad1").last_name("Asdasdad").email("asdsa@yahoo.com").date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Users.builder().id_user(3L).username("asdaasdsad").password("asdasda123").first_name("asdsad1").last_name("Asdasdad").email("asdsa@yahoo.com").date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59")).build());
    }

    @Test
    public void testCreateUsers() throws Exception {
        mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_user").value(users.get(0).getId_user().intValue()))
                .andExpect(jsonPath("$.username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$.password").value(users.get(0).getPassword()))
                .andExpect(jsonPath("$.first_name").value(users.get(0).getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(users.get(0).getLast_name()))
                .andExpect(jsonPath("$.email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$.date_of_birth").value(users.get(0).getDate_of_birth().toString()));

        mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_user").value(users.get(1).getId_user().intValue()))
                .andExpect(jsonPath("$.username").value(users.get(1).getUsername()))
                .andExpect(jsonPath("$.password").value(users.get(1).getPassword()))
                .andExpect(jsonPath("$.first_name").value(users.get(1).getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(users.get(1).getLast_name()))
                .andExpect(jsonPath("$.email").value(users.get(1).getEmail()))
                .andExpect(jsonPath("$.date_of_birth").value(users.get(1).getDate_of_birth().toString()));

        mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_user").value(users.get(2).getId_user().intValue()))
                .andExpect(jsonPath("$.username").value(users.get(2).getUsername()))
                .andExpect(jsonPath("$.password").value(users.get(2).getPassword()))
                .andExpect(jsonPath("$.first_name").value(users.get(2).getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(users.get(2).getLast_name()))
                .andExpect(jsonPath("$.email").value(users.get(2).getEmail()))
                .andExpect(jsonPath("$.date_of_birth").value(users.get(2).getDate_of_birth().toString()));
    }

    @Test
    public void testDisplayAllUsers() throws Exception {
        mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(users)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testDisplayUsersById() throws Exception {
        mvc.perform(get("/api/users/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_user").value(users.get(0).getId_user().intValue()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[0].password").value(users.get(0).getPassword()))
                .andExpect(jsonPath("$[0].first_name").value(users.get(0).getFirst_name()))
                .andExpect(jsonPath("$[0].last_name").value(users.get(0).getLast_name()))
                .andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
                .andExpect(jsonPath("$[0].date_of_birth").value(users.get(0).getDate_of_birth().toString()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mvc.perform(delete("/api/users/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateUser() throws Exception {
        Long update = 2L;
        Users user = Users.builder().username("asdaasdsad12").password("asdasda12123").first_name("asd122sad1").last_name("Asda12sdad").email("asd12sa@yahoo.com").date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59")).build();
        mvc.perform(put("/api/users/{id}", update)
                        .content(JSON.asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("asdaasdsad12"))
                .andExpect(jsonPath("$.password").value("asdasda12123"))
                .andExpect(jsonPath("$.first_name").value("asd122sad1"))
                .andExpect(jsonPath("$.last_name").value("Asda12sdad"))
                .andExpect(jsonPath("$.email").value("asd12sa@yahoo.com"))
                .andExpect(jsonPath("$.date_of_birth").value("2024-12-31T23:59:59"));
    }

    @Test
    public void testUsersCreateUserNameNull() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .password("asdasda123")
                                .first_name("asdsad1")
                                .last_name("Asdasdad")
                                .email("asdsa@yahoo.com")
                                .date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("The username not be mandatory."));
    }

    @Test
    public void testUsersCreatePasswordNull() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .username("asdasda123")
                                .first_name("asdsad1")
                                .last_name("Asdasdad")
                                .email("asdsa@yahoo.com")
                                .date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The password not be mandatory."));
    }

    @Test
    public void testUsersCreateFirstNameNull() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .username("asdasda123")
                                .password("asdsad1")
                                .last_name("Asdasdad")
                                .email("asdsa@yahoo.com")
                                .date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The first name not be mandatory."));
    }

    @Test
    public void testUsersCreateLastNameNull() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .username("asdasda123")
                                .password("asdsad1")
                                .first_name("Asdasdad")
                                .email("asdsa@yahoo.com")
                                .date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The last name not be mandatory"));
    }

    @Test
    public void testUsersCreateEmailNull() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .username("asdasda123")
                                .password("asdsad1")
                                .first_name("Asdasdad")
                                .last_name("asdsa")
                                .date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The email not be mandatory"));
    }

    @Test
    public void testUsersCreateDateOfBirthNull() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .username("asdasda123")
                                .password("asdsad1")
                                .first_name("Asdasdad")
                                .last_name("asdsa")
                                .email("asdad@yahoo.com")
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The date of birth must not be null."));
    }

    @Test
    public void testUsersCreateAllDateNull() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder().build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("Validation failed: The email not be mandatory.; The password not be mandatory.; The last name not be mandatory.; The date of birth must not be null.; The fist name not be mandatory.; The username not be mandatory.;"));
    }

    @Test
    public void testUsersCreateInvalidEmail() throws Exception {
        result = mvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Users.builder()
                                .username("username")
                                .password("asdasda123")
                                .first_name("asdsad1")
                                .last_name("Asdasdad")
                                .email("invalid_email")
                                .date_of_birth(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("must be a well-formed email address"));
    }
}
