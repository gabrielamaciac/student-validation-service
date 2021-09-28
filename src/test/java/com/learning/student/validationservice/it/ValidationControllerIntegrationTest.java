package com.learning.student.validationservice.it;

import com.learning.student.validationservice.controller.api.ValidationApi;
import com.learning.student.validationservice.controller.api.ValidationController;
import com.learning.student.validationservice.facade.ValidationFacade;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidationControllerIntegrationTest {

    @Autowired
    private ValidationFacade validationFacade;

    @Autowired
    private ModelMapper modelMapper;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        ValidationApi validationController = new ValidationController(validationFacade, modelMapper);
        mvc = MockMvcBuilders.standaloneSetup(validationController).build();
    }

    @Test
    void getAndPostReturn200OKAndEmptyListForValidStudent() throws Exception {
        // Validate student JSON and expect status code 200 OK
        UUID uuid = UUID.randomUUID();
        MockHttpServletResponse postResponse = mvc.perform(post("/validation/student/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ValidationTestData.getStudentJson(10.0, 10.0)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Get validations and expect status code 200 OK
        MockHttpServletResponse getResponse = mvc.perform(get("/validation/student/" + uuid))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Both requests return empty lists
        assertEquals("[]", postResponse.getContentAsString());
        assertEquals("[]", getResponse.getContentAsString());
    }

    @Test
    void getAndPostReturn200OKAndValidListForInvalidStudent() throws Exception {
        // Validate student JSON and expect status code 200 OK
        UUID uuid = UUID.randomUUID();
        MockHttpServletResponse postResponse = mvc.perform(post("/validation/student/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ValidationTestData.getStudentJson(4.0, 4.0)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Get validations and expect status code 200 OK
        MockHttpServletResponse getResponse = mvc.perform(get("/validation/student/" + uuid))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Both requests return the expected error for the given JSON
        assertEquals(ValidationTestData.getErrorForStudentJson(uuid.toString()), postResponse.getContentAsString());
        assertEquals(ValidationTestData.getErrorForStudentJson(uuid.toString()), getResponse.getContentAsString());
    }
}
