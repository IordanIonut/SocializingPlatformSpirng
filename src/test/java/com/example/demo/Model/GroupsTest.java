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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class GroupsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    public static List<Groups> groups;

    public void setMvc(MockMvc mvc, ObjectMapper objectMapper){
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    public static void setup() {
        groups = Arrays.asList(
                Groups.builder().id_group(1L).name("asdasda").description("asdsadasdsadasdssaasd").build(),
                Groups.builder().id_group(2L).name("asdasda").description("asdsadasdsadasdssaasd").build(),
                Groups.builder().id_group(3L).name("asdasda").description("asdsadasdsadasdssaasd").build());
    }

    @Test
    public void testCreateGroups() throws Exception {
        mvc.perform(post("/api/groups/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groups.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_group").value(groups.get(0).getId_group()))
                .andExpect(jsonPath("$.name").value(groups.get(0).getName()))
                .andExpect(jsonPath("$.description").value(groups.get(0).getDescription()));

        mvc.perform(post("/api/groups/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groups.get(1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_group").value(groups.get(1).getId_group()))
                .andExpect(jsonPath("$.name").value(groups.get(1).getName()))
                .andExpect(jsonPath("$.description").value(groups.get(1).getDescription()));

        mvc.perform(post("/api/groups/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groups.get(2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_group").value(groups.get(2).getId_group()))
                .andExpect(jsonPath("$.name").value(groups.get(2).getName()))
                .andExpect(jsonPath("$.description").value(groups.get(2).getDescription()));
    }

    @Test
    public void testDisplayAllGroups() throws Exception {
        mvc.perform(get("/api/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testDisplayGroupsById() throws Exception {
        mvc.perform(get("/api/groups/id?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_group").value(groups.get(0).getId_group()))
                .andExpect(jsonPath("$[0].name").value(groups.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(groups.get(0).getDescription()));

    }

    @Test
    public void testDeleteGroups() throws Exception {
        mvc.perform(delete("/api/friens/{id}", 4))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateGroups() throws Exception{
        Long update = 2L;
        Groups group = Groups.builder().name("12313121").description("1232212323132132132").build();

        mvc.perform(put("/api/groups/{id}", update)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.asJsonString(group))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(group.getName()))
                .andExpect(jsonPath("$.description").value(group.getDescription()));
    }

    @Test
    public void testGroupsNameNull() throws Exception{
        result = mvc.perform(post("/api/groups/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Groups.builder()
                                .description("asdsadasadassa")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The name not be mandatory"));
    }

    @Test
    public void testGroupsDescriptionNull() throws Exception{
        result = mvc.perform(post("/api/groups/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Groups.builder()
                                .name("asdsadasadassa")
                                .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response.contains("The description not be mandatory"));
    }
}
