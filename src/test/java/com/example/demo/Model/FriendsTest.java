package com.example.demo.Model;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
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
public class FriendsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Friends> friends;

    public static List<Users> users;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setup(){
        UsersTest.setup();
        users = UsersTest.users;

        friends = Arrays.asList(
                Friends.builder().id_friend(1L).id_user_1(users.get(0)).id_user_2(users.get(1)).status("asdsadsaddas").build(),
                Friends.builder().id_friend(2L).id_user_1(users.get(1)).id_user_2(users.get(2)).status("asdsadsaddas").build(),
                Friends.builder().id_friend(3L).id_user_1(users.get(2)).id_user_2(users.get(0)).status("asdsadsaddas").build());
    }

    @Test
    public void testCreateFriends() throws Exception {
        mvc.perform(post("/api/friens/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(friends.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_friend").value(friends.get(0).getId_friend().longValue()))
                .andExpect(jsonPath("$.id_user_1.id_user").value(friends.get(0).getId_user_1().getId_user().longValue()))
                .andExpect(jsonPath("$.id_user_2.id_user").value(friends.get(0).getId_user_2().getId_user().longValue()))
                .andExpect(jsonPath("$.status").value(friends.get(0).getStatus()));

        mvc.perform(post("/api/friens/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(friends.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_friend").value(friends.get(1).getId_friend().longValue()))
                .andExpect(jsonPath("$.id_user_1.id_user").value(friends.get(1).getId_user_1().getId_user().longValue()))
                .andExpect(jsonPath("$.id_user_2.id_user").value(friends.get(1).getId_user_2().getId_user().longValue()))
                .andExpect(jsonPath("$.status").value(friends.get(1).getStatus()));

        mvc.perform(post("/api/friens/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(friends.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_friend").value(friends.get(2).getId_friend().longValue()))
                .andExpect(jsonPath("$.id_user_1.id_user").value(friends.get(2).getId_user_1().getId_user().longValue()))
                .andExpect(jsonPath("$.id_user_2.id_user").value(friends.get(2).getId_user_2().getId_user().longValue()))
                .andExpect(jsonPath("$.status").value(friends.get(2).getStatus()));
    }

    @Test
    public void testDisplayAllFriends() throws Exception {
        mvc.perform(get("/api/friens")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testDisplayFriendsById() throws Exception {
        mvc.perform(get("/api/friens/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_friend").value(friends.get(0).getId_friend().longValue()))
                .andExpect(jsonPath("$[0].id_user_1.id_user").value(friends.get(0).getId_user_1().getId_user().longValue()))
                .andExpect(jsonPath("$[0].id_user_2.id_user").value(friends.get(0).getId_user_2().getId_user().longValue()))
                .andExpect(jsonPath("$[0].status").value(friends.get(0).getStatus()));
    }

    @Test
    public void testDeleteFriends() throws Exception {
        mvc.perform(delete("/api/friens/{id}", 4))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateFriens() throws Exception {
        Long update = 2L;
        Friends friend = Friends.builder().id_user_1(users.get(2)).id_user_2(users.get(2)).status("123112").build();

        mvc.perform(put("/api/friens/{id}", update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(friend))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_user_1.id_user").value(friend.getId_user_1().getId_user().longValue()))
                .andExpect(jsonPath("$.id_user_2.id_user").value(friend.getId_user_2().getId_user().longValue()))
                .andExpect(jsonPath("$.status").value(friend.getStatus()));
    }

    @Test
    public void testFriendsStatusNull() throws Exception {
        result = mvc.perform(post("/api/friens/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Friends.builder()
                                .id_user_1(users.get(0))
                                .id_user_2(users.get(2))
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The status not be mandatory."));
    }
}
