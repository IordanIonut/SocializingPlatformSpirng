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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class LikesTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Users> users;

    public static List<Posts> posts;

    public static List<Likes> likes;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setup() {
        PostsTest.setup();
        posts = PostsTest.posts;
        users = PostsTest.users;

        likes = Arrays.asList(
                Likes.builder().id_like(1L).id_post(posts.get(0)).id_user(users.get(0)).number(121312L).build(),
                Likes.builder().id_like(2L).id_post(posts.get(1)).id_user(users.get(1)).number(121312L).build(),
                Likes.builder().id_like(3L).id_post(posts.get(2)).id_user(users.get(2)).number(121312L).build()
        );
    }

    @Test
    public void testCreateLikes() throws Exception {
        mvc.perform(post("/api/likes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(likes.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_like").value(likes.get(0).getId_like().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(likes.get(0).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.id_post.id_post").value(likes.get(0).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.number").value(likes.get(0).getNumber()));

        mvc.perform(post("/api/likes/add")
                        .content(objectMapper.writeValueAsString(likes.get(1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_like").value(likes.get(1).getId_like().longValue()))
                .andExpect(jsonPath("$.id_post.id_post").value(likes.get(1).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(likes.get(1).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.number").value(likes.get(1).getNumber()));


        mvc.perform(post("/api/likes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(likes.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_like").value(likes.get(2).getId_like().longValue()))
                .andExpect(jsonPath("$.id_post.id_post").value(likes.get(2).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(likes.get(2).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.number").value(likes.get(2).getNumber()));
    }

    @Test
    public void testDisplayAllLikes() throws Exception {
        mvc.perform(get("/api/likes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testDisplayByIdLikes() throws Exception {
        mvc.perform(get("/api/likes/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_like").value(likes.get(0).getId_like().longValue()))
                .andExpect(jsonPath("$[0].id_post.id_post").value(likes.get(0).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$[0].id_user.id_user").value(likes.get(0).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$[0].number").value(likes.get(0).getNumber()));
    }

    @Test
    public void testDeleteLikes() throws Exception {
        mvc.perform(delete("/api/likes/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateLikes() throws Exception {
        Long update = 2L;
        Likes like = Likes.builder().id_post(posts.get(2)).id_user(users.get(2)).number(11111L).build();

        mvc.perform(put("/api/likes/{id}", update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JSON.asJsonString(like)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_post.id_post").value(like.getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(like.getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.number").value(like.getNumber()));
    }

    @Test
    public void testLikesNumberNull() throws Exception {
        result = mvc.perform(post("/api/likes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Likes.builder()
                                .id_post(posts.get(0))
                                .id_user(users.get(0))
                                .build())))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The number not be mandatory."));
    }
}
