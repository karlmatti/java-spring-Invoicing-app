package com.invoicing.invoicing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoicing.invoicing.InvoicingApplication;
import com.invoicing.invoicing.model.Project;
import com.invoicing.invoicing.repository.ProjectRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {
    @Autowired
    private MockMvc mvc;


    @Test
    void getProjectsListThenCheckMediaTypeAndStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/projects")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createProjectThenCheckLabel() throws Exception {
        Project project = new Project("Unit testing",
                "UNIT TESTING is a level of software testing where individual units/ components of a software are tested.");
        System.out.println("Sent: "+ asJsonString(project));
        RequestBuilder request = post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(project))
                .characterEncoding("utf-8");
        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();
        System.out.println("/--------------------------------/");
        System.out.println("Received: "+ result.getResponse().getContentAsString());
        System.out.println("/--------------------------------/");
        Project resultProject = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Project.class);
        assertEquals(project.getLabel(), resultProject.getLabel());
    }

    @Test
    void createAndUpdateThenCheckProjectLabelAndDescription() throws Exception {
        Project createProject = new Project("Integration testing",
                "INTEGRATION TESTING is a level of software testing where individual units are combined and tested as a group.");
        System.out.println("CREATING PROJECT");
        System.out.println("Sent: "+ asJsonString(createProject));
        RequestBuilder postRequest = post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createProject))
                .characterEncoding("utf-8");
        MvcResult postResult = mvc.perform(postRequest).andExpect(status().isOk()).andReturn();
        System.out.println("/--------------------------------/");
        System.out.println("Received: "+ postResult.getResponse().getContentAsString());
        System.out.println("/--------------------------------/");
        Project postResultProject = new ObjectMapper().readValue(postResult.getResponse().getContentAsString(), Project.class);
        assertEquals(createProject.getLabel(), postResultProject.getLabel());
        System.out.println("/--------------------------------/");

        System.out.println("UPDATING PROJECT");
        Project updateProject = new Project(postResultProject.getId(), "Int test",
                "Integration testing is the phase in software testing in which individual software modules are combined and tested as a group.");

        System.out.println("Sent: "+ asJsonString(updateProject));

        RequestBuilder putRequest = put("/projects/"+postResultProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateProject))
                .characterEncoding("utf-8");
        MvcResult updateResult = mvc.perform(putRequest).andExpect(status().isOk()).andReturn();
        System.out.println("/--------------------------------/");
        System.out.println("Received: "+ updateResult.getResponse().getContentAsString());
        System.out.println("/--------------------------------/");
        Project updateResultProject = new ObjectMapper().readValue(updateResult.getResponse().getContentAsString(), Project.class);
        assertEquals(updateProject.getLabel(), updateResultProject.getLabel());
        assertEquals(updateProject.getDescription(), updateResultProject.getDescription());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}