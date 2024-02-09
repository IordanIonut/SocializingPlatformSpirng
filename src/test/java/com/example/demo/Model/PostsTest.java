package com.example.demo.Model;

import com.example.demo.Utility.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.catalina.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class PostsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectManager;

    public static List<Posts> posts;

    public static List<Users> users;

    private MvcResult result;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectManager = objectMapper;
    }

    @BeforeAll
    public static void setup() {
        UsersTest.setup();
        users = UsersTest.users;

        posts = Arrays.asList(
                Posts.builder().id_post(1L).id_user(users.get(0)).content("asdsasdas1").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Posts.builder().id_post(2L).id_user(users.get(1)).content("asdsasdas2").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Posts.builder().id_post(3L).id_user(users.get(2)).content("asdsasdas3").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build());
    }

    @Test
    public void testCreatePosts() throws Exception {
        mvc.perform(post("/api/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectManager.writeValueAsString(posts.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_post").value(posts.get(0).getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(posts.get(0).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(posts.get(0).getContent()))
                .andExpect(jsonPath("$.time_stamp").value(posts.get(0).getTime_stamp().toString()));

        mvc.perform(post("/api/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectManager.writeValueAsString(posts.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_post").value(posts.get(1).getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(posts.get(1).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(posts.get(1).getContent()))
                .andExpect(jsonPath("$.time_stamp").value(posts.get(1).getTime_stamp().toString()));

        mvc.perform(post("/api/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectManager.writeValueAsString(posts.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_post").value(posts.get(2).getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(posts.get(2).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(posts.get(2).getContent()))
                .andExpect(jsonPath("$.time_stamp").value(posts.get(2).getTime_stamp().toString()));
    }

    @Test
    public void testDisplayAllPosts() throws Exception {
        mvc.perform(get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectManager.writeValueAsString(posts)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testDisplayPostsById() throws Exception {
        mvc.perform(get("/api/posts/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_post").value(posts.get(0).getId_post().longValue()))
                .andExpect(jsonPath("$[0].id_user.id_user").value(posts.get(0).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$[0].content").value(posts.get(0).getContent()))
                .andExpect(jsonPath("$[0].time_stamp").value(posts.get(0).getTime_stamp().toString()));
    }

    @Test
    public void testDeletePosts() throws Exception {
        mvc.perform(delete("/api/posts/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdatePosts() throws Exception {
        Long update = 2L;
        Posts post = Posts.builder().id_user(users.get(1)).content("asdsasdas2").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build();

        mvc.perform(put("/api/posts/{id}", update)
                        .content(JSON.asJsonString(post))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_user.id_user").value(post.getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andExpect(jsonPath("$.time_stamp").value(post.getTime_stamp().toString()));
    }

    @Test
    public void testPostsContentNull() throws Exception {
        result = mvc.perform(post("/api/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectManager.writeValueAsString(Posts.builder().
                                id_post(1L)
                                .id_user(users.get(0))
                                .time_stamp(LocalDateTime.parse("2024-12-31T23:59:59"))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The content not be mandatory."));
    }

    @Test
    public void testPostsTimeStampNull() throws Exception {
        result = mvc.perform(post("/api/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectManager.writeValueAsString(Posts.builder().
                                id_post(1L)
                                .id_user(users.get(0))
                                .content("asdassadasd")
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The time stamp not be mandatory."));
    }
}
