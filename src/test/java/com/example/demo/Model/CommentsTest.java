package com.example.demo.Model;

import com.example.demo.Utility.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@AutoConfigureMockMvc
@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class CommentsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Posts> posts;

    public static List<Users> users;

    public static List<Comments> comments;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setup() {
        PostsTest.setup();
        posts = PostsTest.posts;
        users = PostsTest.users;

        comments = Arrays.asList(
                Comments.builder().id_comment(1L).id_post(posts.get(0)).id_user(users.get(0)).content("asdadad").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Comments.builder().id_comment(2L).id_post(posts.get(1)).id_user(users.get(1)).content("asdadad").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build(),
                Comments.builder().id_comment(3L).id_post(posts.get(2)).id_user(users.get(2)).content("asdadad").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build());
    }

    @Test
    public void testCreateComments() throws Exception {
        mvc.perform(post("/api/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_comment").value(comments.get(0).getId_comment().longValue()))
                .andExpect(jsonPath("$.id_post.id_post").value(comments.get(0).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(comments.get(0).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(comments.get(0).getContent()))
                .andExpect(jsonPath("$.time_stamp").value(comments.get(0).getTime_stamp().toString()));

        mvc.perform(post("/api/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_comment").value(comments.get(1).getId_comment().longValue()))
                .andExpect(jsonPath("$.id_post.id_post").value(comments.get(1).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(comments.get(1).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(comments.get(1).getContent()))
                .andExpect(jsonPath("$.time_stamp").value(comments.get(1).getTime_stamp().toString()));

        mvc.perform(post("/api/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comments.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_comment").value(comments.get(2).getId_comment().longValue()))
                .andExpect(jsonPath("$.id_post.id_post").value(comments.get(2).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(comments.get(2).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(comments.get(2).getContent()))
                .andExpect(jsonPath("$.time_stamp").value(comments.get(2).getTime_stamp().toString()));
    }

    @Test
    public void testDisplayAllComments() throws Exception{
        mvc.perform(get("/api/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testCommentsById() throws Exception{
        mvc.perform(get("/api/comments/id?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_comment").value(comments.get(0).getId_comment().longValue()))
                .andExpect(jsonPath("$[0].id_post.id_post").value(comments.get(0).getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$[0].id_user.id_user").value(comments.get(0).getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$[0].content").value(comments.get(0).getContent()))
                .andExpect(jsonPath("$[0].time_stamp").value(comments.get(0).getTime_stamp().toString()));
    }

    @Test
    public void testDeleteComments() throws Exception{
        mvc.perform(delete("/api/comments/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateComments() throws Exception {
        Long update = 2L;
        Comments comment = Comments.builder().id_post(posts.get(0)).id_user(users.get(0)).content("asdadad").time_stamp(LocalDateTime.parse("2024-12-31T23:59:59")).build();

        mvc.perform(put("/api/comments/{id}", update)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.asJsonString(comment))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id_post.id_post").value(comment.getId_post().getId_post().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(comment.getId_user().getId_user().longValue()))
                .andExpect(jsonPath("$.content").value(comment.getContent()))
                .andExpect(jsonPath("$.time_stamp").value(comment.getTime_stamp().toString()));

    }

    @Test
    public void testCommentsContentNull() throws Exception{
        result = mvc.perform(post("/api/comments/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Comments.builder()
                                .id_post(posts.get(0))
                                .id_user(users.get(0))
                                .time_stamp(LocalDateTime.parse("2024-12-31T23:59:59"))
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The content not be mandatory."));
    }

    @Test
    public void testCommentsTimeStampNull() throws Exception {
        result = mvc.perform(post("/api/comments/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Comments.builder()
                        .id_post(posts.get(0))
                        .id_user(users.get(0))
                        .content("asdasda")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("the time stamp not be mandatory."));
    }
}
