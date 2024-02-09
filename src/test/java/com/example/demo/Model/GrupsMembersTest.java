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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class GrupsMembersTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static List<Groups> groups;

    public static List<Users> users;

    public static List<GroupsMembers> groupsMembers;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setup() {
        GroupsTest.setup();
        groups = GroupsTest.groups;
        UsersTest.setup();
        users = UsersTest.users;

        groupsMembers = Arrays.asList(
                GroupsMembers.builder().id_group_member(1L).id_group(groups.get(0)).id_user(users.get(0)).build(),
                GroupsMembers.builder().id_group_member(2L).id_group(groups.get(1)).id_user(users.get(1)).build(),
                GroupsMembers.builder().id_group_member(3L).id_group(groups.get(2)).id_user(users.get(2)).build());
    }

    @Test
    public void testCreateGrupoMembers() throws Exception {
        mvc.perform(post("/api/groups/members/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupsMembers.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_group_member").value(groupsMembers.get(0).getId_group_member().longValue()))
                .andExpect(jsonPath("$.id_group.id_group").value(groupsMembers.get(0).getId_group().getId_group().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(groupsMembers.get(0).getId_user().getId_user().longValue()));

        mvc.perform(post("/api/groups/members/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupsMembers.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_group_member").value(groupsMembers.get(1).getId_group_member().longValue()))
                .andExpect(jsonPath("$.id_group.id_group").value(groupsMembers.get(1).getId_group().getId_group().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(groupsMembers.get(1).getId_user().getId_user().longValue()));

        mvc.perform(post("/api/groups/members/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupsMembers.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_group_member").value(groupsMembers.get(2).getId_group_member().longValue()))
                .andExpect(jsonPath("$.id_group.id_group").value(groupsMembers.get(2).getId_group().getId_group().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(groupsMembers.get(2).getId_user().getId_user().longValue()));
    }

    @Test
    public void testDisplayAllGrupoMembers() throws Exception {
        mvc.perform(get("/api/groups/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testDisplayGrupoMembersById() throws Exception {
        mvc.perform(get("/api/groups/members/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_group_member").value(groupsMembers.get(0).getId_group_member().longValue()))
                .andExpect(jsonPath("$[0].id_group.id_group").value(groupsMembers.get(0).getId_group().getId_group().longValue()))
                .andExpect(jsonPath("$[0].id_user.id_user").value(groupsMembers.get(0).getId_user().getId_user().longValue()));}

    @Test
    public void testDeleteGrupoMembers() throws Exception {
        mvc.perform(delete("/api/groups/members/{id}", 4))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateGrupoMembers() throws Exception {
        Long update = 2L;
        GroupsMembers groupsMember =  GroupsMembers.builder().id_group(groups.get(1)).id_user(users.get(1)).build();

        mvc.perform(put("/api/groups/members/{id}", update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupsMember))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_group.id_group").value(groupsMember.getId_group().getId_group().longValue()))
                .andExpect(jsonPath("$.id_user.id_user").value(groupsMember.getId_user().getId_user().longValue()));
    }

}
