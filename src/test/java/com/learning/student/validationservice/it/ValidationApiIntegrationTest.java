package com.learning.student.validationservice.it;

import com.learning.student.validationservice.ValidationServiceApplication;
import com.learning.student.validationservice.facade.ValidationFacade;
import com.learning.student.validationservice.util.ValidationTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yaml")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ValidationServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ValidationApiIntegrationTest extends ContainersEnvironment {

    @Autowired
    private ValidationFacade validationFacade;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mvc;

    @Test
    void getAndPostReturn200OKAndEmptyListForValidStudent() throws Exception {
        // Validate student JSON and expect status code 200 OK
        int rowsBefore = JdbcTestUtils.countRowsInTable(jdbcTemplate, "validation");

        UUID uuid = UUID.randomUUID();
        MockHttpServletResponse postResponse = mvc.perform(post("/validation/student/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ValidationTestData.getStudentJson(10.0, 10.0)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        int rowsAfter = JdbcTestUtils.countRowsInTable(jdbcTemplate, "validation");

        // Get validations and expect status code 200 OK
        MockHttpServletResponse getResponse = mvc.perform(get("/validation/student/" + uuid))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Both requests return empty lists and no entry was stored in DB
        assertNotEquals(rowsAfter, rowsBefore + 1);
        assertEquals("[]", postResponse.getContentAsString());
        assertEquals("[]", getResponse.getContentAsString());
    }

    @Test
    void getAndPostReturn200OKAndValidListForInvalidStudent() throws Exception {
        // Validate student JSON and expect status code 200 OK
        int rowsBefore = JdbcTestUtils.countRowsInTable(jdbcTemplate, "validation");

        UUID uuid = UUID.randomUUID();
        MockHttpServletResponse postResponse = mvc.perform(post("/validation/student/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ValidationTestData.getStudentJson(4.0, 4.0)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        int rowsAfter = JdbcTestUtils.countRowsInTable(jdbcTemplate, "validation");

        // Get validations and expect status code 200 OK
        MockHttpServletResponse getResponse = mvc.perform(get("/validation/student/" + uuid))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Both requests return the expected error for the given JSON and the error was stored in DB
        assertEquals(rowsAfter, rowsBefore + 1);
        assertEquals(ValidationTestData.getErrorForStudentJson(uuid.toString()), postResponse.getContentAsString());
        assertEquals(ValidationTestData.getErrorForStudentJson(uuid.toString()), getResponse.getContentAsString());
    }
}
